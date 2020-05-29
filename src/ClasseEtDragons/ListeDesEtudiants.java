package ClasseEtDragons;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *   Liste des étudiants, gérant l'écriture et l'array list d'élèves, voir le site poi.apache.org
 *   @author  Nour Asmani
 */
public class ListeDesEtudiants{
    public static final int PARAM_ETUDIANTS = 8;
    public static final int NBR_POUVOIRS = 6;
    public static final int MAX_CELLS = PARAM_ETUDIANTS + NBR_POUVOIRS;
    
    private ArrayList<Etudiant> etudiants = new ArrayList<>();
    
    /**
     * Constructeur à 1 paramètres pour ListeDesEtudiants
     * 
     * @param fichierAvatars Chemin canonique du fichier excel contenant la liste des étudiants
     * @see   #setToutEtudiants(java.lang.String)
     */
    public ListeDesEtudiants(String fichierAvatars) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        setToutEtudiants(fichierAvatars);
    }
    
    /**
     * Constructeur à 1 paramètres pour ListeDesEtudiants
     * 
     * @param fichierAvatars Fichier excel contenant la liste des étudiants
     * @see   #setToutEtudiants(java.lang.String)
     */
    public ListeDesEtudiants(File fichierAvatars) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        this(fichierAvatars.getCanonicalPath());
    }
    
    /**
     * Obtient l'étudiant situer a index dans la liste.
     * 
     * @param index Index de l'étudiant à obtenir
     */
    public Etudiant getEtudiant(int index){
        return etudiants.get(index);
    }

    /**
     * Obtient la taille de la liste des étudiants
     */
    public int getEtudiantsSize(){
        return etudiants.size();
    }
    
    /**
     * Obtient l'index d'un étudiant
     * 
     * @param unEtudiant Étudiant dont on a besoin de l'index
     */
    public int getIndex(Etudiant unEtudiant){
        return etudiants.indexOf(unEtudiant);
    }
    
    /**
     * Set l'étudiant à un index
     * 
     * @param index      Index de l'étudiant
     * @param unEtudiant Etudiant remplacant l'étudiant à la place "index"
     */
    public void setEtudiant(int index, Etudiant unEtudiant){
        etudiants.set(index, unEtudiant);
    }
    
    /**
     * Ajoute un etudiant et reorganise la liste
     * 
     * @param unEtudiant Etudiant à ajouter
     * 
     * @throws Exception Si l'étudiant n'a pas été ajouter
     */
    public void addEtudiant(Etudiant unEtudiant) throws Exception{
        if(!etudiants.add(unEtudiant))
            throw new Exception("L'etudiant "+unEtudiant.getName()+" de numero de DA "+unEtudiant.getNAdmission()+" n'a pas pu etre ajouter.");
        
        organisezAlphabet();
    }
      
    /**
     * Supprime un etudiant et reorganise la liste
     * 
     * @param index L'index de l'étudiant a supprimer
     * 
     * @throws Exception Si l'étudiant n'a pas été ajouter
     */
    public void rmEtudiant(int index) throws Exception{
        if((index < 0) || (index > etudiants.size()))
            throw new Exception("L'etudiant n'a pas pu etre supprimer.");
        
        etudiants.remove(index);
    }
    
    /**
     * Organise la liste alphabétiquement
     * 
     * @see Comparator
     */
    public void organisezAlphabet(){
        etudiants.sort(new Comparator<Etudiant>() {
            @Override
            public int compare(Etudiant etudiant1, Etudiant etudiant2) {
                return etudiant1.getName().compareToIgnoreCase(etudiant2.getName());
            }
        });
    }
    
    /**
     * Organise la liste selon le niveau et l'expérience
     * 
     * @param isCroissant Si l'ordre doit être croissant ou pas
     * @see Comparator
     */
    public void organisezClassement(boolean isCroissant){
        etudiants.sort(new Comparator<Etudiant>() {
            @Override
            public int compare(Etudiant etudiant1, Etudiant etudiant2) {
                int niveauDiff = Integer.compare((isCroissant) ? etudiant1.getNiveau() : etudiant2.getNiveau(), (isCroissant) ? etudiant2.getNiveau() : etudiant1.getNiveau());
                int expDiff = Integer.compare((isCroissant) ? etudiant1.getExp() : etudiant2.getExp(), (isCroissant) ? etudiant2.getExp() : etudiant1.getExp());
                
                if(niveauDiff > 0)
                    return 1;
                else if(niveauDiff < 0)
                    return -1;
                else if(expDiff > 0)
                    return 1;
                else if(expDiff < 0)
                    return -1;
                
                return 0;
            }
        });
    }
    
    /**
     * Ecrit tout les etudiants et les images liee de cet objet dans un fichier excel de nom fileName
     * 
     * @param fileName                       Nom du fichier a écrire
     * 
     * @throws IllegalArgumentException      Si le nom de fichier est vide ou null
     * @throws java.io.IOException           Si une erreur d'I/O arrive durant l'écriture
     * @throws java.io.FileNotFoundException Si le fichier n'est pas trouver
     * @throws Exception                     Si une erreur quelconque arrive durant l'écriture
     */
    public void writeToutEtudiantsEtImages(String fileName) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        if((fileName == null) || (fileName.isEmpty()))
            throw new IllegalArgumentException("Un fichier d'entree est vide.");
        
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        
        Row ligne;
        Cell cellule;
        
        // Chaque ligne et cellule doivent être crée manuellement
        ligne = sheet.createRow(0);
        cellule = ligne.createCell(0);
        cellule.setCellValue("Les 4 champs nécessaires de bases sont:");
        cellule = ligne.createCell(1);
        cellule.setCellValue("Numéros d'admission");
        cellule = ligne.createCell(2);
        cellule.setCellValue("Noms et prénoms");
        cellule = ligne.createCell(3);
        cellule.setCellValue("Numéro du rôle (déterminez par la ligne du role dans docs/role.txt)");
        cellule = ligne.createCell(4);
        cellule.setCellValue("Pseudo de l'élève");
        
        for (int i=0;i < etudiants.size();i++){
            ligne = sheet.createRow(i+1);
            Etudiant currEtudiant = etudiants.get(i);
            String img = currEtudiant.getCheminImage();
            
            
            cellule = ligne.createCell(0);
            cellule.setCellValue(currEtudiant.getNAdmission());
            cellule = ligne.createCell(1);
            cellule.setCellValue(currEtudiant.getName());
            cellule = ligne.createCell(2);
            cellule.setCellValue(currEtudiant.getRoleIndex());
            cellule = ligne.createCell(3);
            cellule.setCellValue(currEtudiant.getPseudo());
            cellule = ligne.createCell(4);
            cellule.setCellValue(img);
            cellule = ligne.createCell(5);
            cellule.setCellValue(currEtudiant.getExp());
            cellule = ligne.createCell(6);
            cellule.setCellValue(currEtudiant.getNiveau());
            cellule = ligne.createCell(7);
            cellule.setCellValue(currEtudiant.getPv());
            cellule = ligne.createCell(8);
            
            //On écrit si les pouvoirs sont utilisablent ou non (boolean) en string
            cellule.setCellValue(""+currEtudiant.getPouvoir(0));
            cellule = ligne.createCell(9);
            cellule.setCellValue(""+currEtudiant.getPouvoir(1));
            cellule = ligne.createCell(10);
            cellule.setCellValue(""+currEtudiant.getPouvoir(2));
            cellule = ligne.createCell(11);
            cellule.setCellValue(""+currEtudiant.getPouvoir(3));
            cellule = ligne.createCell(12);
            cellule.setCellValue(""+currEtudiant.getPouvoir(4));
            cellule = ligne.createCell(13);
            cellule.setCellValue(""+currEtudiant.getPouvoir(5));
        }
        
        closeWorkBook(fileName, wb, true);
    }
    
    /**
     * Met en place l'array list etudiants
     * Appeler lors de l'initialisation de cet objet, ne devrait pas etre appeler autrement
     * 
     * @param fileName                  Fichier à lire
     * 
     * @throws IllegalArgumentException      Si le nom de fichier est vide ou null
     * @throws java.io.IOException           Si une erreur d'I/O arrive durant la lecture
     * @throws java.io.FileNotFoundException Si le fichier n'est pas trouver
     * @throws Exception                     Si une erreur quelconque arrive durant la lecture
     */
    public void setToutEtudiants(String fileName) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        if((fileName == null) || (fileName.isEmpty()))
            throw new IllegalArgumentException("Un fichier d'entree est vide.");
        
        XSSFWorkbook wb = null;
        try{
            wb = new XSSFWorkbook(new FileInputStream(fileName));
        } catch(FileNotFoundException fnfe){
            throw new FileNotFoundException("La liste d'etudiant "+fileName+" n'existe pas.");
        } catch(IOException ioe){
            throw new IOException("Erreur d'I/O lors de la lecture de la liste d'etudiant "+fileName+".");
        } catch(Exception e){
            throw new Exception("Erreur lors de la lecture de la liste d'etudiant "+fileName+".");
        }
        
        wb.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
        Sheet sheet = wb.getSheetAt(0);
        
        Row ligne = null;
        String img;
        Etudiant unEtudiant;
        DataFormatter formatter = new DataFormatter();
        for (int i=1;i <= sheet.getLastRowNum();i++){
            ligne = sheet.getRow(i);
            
            if(ligne == null)
                continue;
            
            try{
                switch (ligne.getLastCellNum()) {
                    //4 lignes pour les paramètres de bases (nbr admission, nom, role, pseudo)
                    case 4:
                        unEtudiant = new Etudiant(formatter.formatCellValue(ligne.getCell(0)), ligne.getCell(1).getStringCellValue(), (int)ligne.getCell(2).getNumericCellValue(), ligne.getCell(3).getStringCellValue());
                        
                        //for(int j=0;j<etudiants.size();i++)
                        //    if(!unEtudiant.equals(etudiants.get(j)) && (!unEtudiant.getName().equals(etudiants.get(j).getName()) || !unEtudiant.getNAdmission().equals(etudiants.get(j).getNAdmission())))
                        //        throw new Exception("2 etudiants ne peuvent pas avoir le même numéro de DA ou le même nom. (nDa dupliquer: "+etudiants.get(j).getNAdmission()+", nom dupliquer: "+etudiants.get(j).getName()+")");
                        
                        etudiants.add(unEtudiant);
                        break;
                    case MAX_CELLS: // 13 parametres [(nbr admission, nom, pseudos, role, chemin de l'image, exp, pv, lvl) + 5 pouvoirs]
                        if(ligne.getCell(4) == null)
                            img = null;
                        else
                            img = ligne.getCell(4).getStringCellValue();
                        
                        String[] pouvoirsUtilisableSS = {ligne.getCell(8).getStringCellValue(), ligne.getCell(9).getStringCellValue(), ligne.getCell(10).getStringCellValue(), ligne.getCell(11).getStringCellValue(),
                                                       ligne.getCell(12).getStringCellValue(), ligne.getCell(13).getStringCellValue()};
                        boolean[] pouvoirsUtilisable = {pouvoirsUtilisableSS[0].equalsIgnoreCase("true"), pouvoirsUtilisableSS[1].equalsIgnoreCase("true"), pouvoirsUtilisableSS[2].equalsIgnoreCase("true"), pouvoirsUtilisableSS[3].equalsIgnoreCase("true"),
                                                       pouvoirsUtilisableSS[4].equalsIgnoreCase("true"), pouvoirsUtilisableSS[5].equalsIgnoreCase("true")};
                        
                        unEtudiant = new Etudiant(formatter.formatCellValue(ligne.getCell(0)), ligne.getCell(1).getStringCellValue(), (int)ligne.getCell(2).getNumericCellValue(), ligne.getCell(3).getStringCellValue(), img,
                                                   (int)ligne.getCell(5).getNumericCellValue(), (int)ligne.getCell(6).getNumericCellValue(), (int)ligne.getCell(7).getNumericCellValue(), pouvoirsUtilisable);
                        
                        //Vérifier si la liste contient un autre étudiant avec le même numéro d'admission ou le même nom
                        //for(int j=0;j<etudiants.size();i++)
                        //    if(unEtudiant.getName().equals(etudiants.get(j).getName()) || unEtudiant.getNAdmission().equals(etudiants.get(j).getNAdmission()))
                        //        throw new Exception("2 etudiants ne peuvent pas avoir le même numéro de DA ou le même nom. (nDa dupliquer: "+etudiants.get(j).getNAdmission()+", nom dupliquer: "+etudiants.get(j).getName()+")");
                        
                        etudiants.add(unEtudiant);
                        break;
                    default:
                        throw new Exception("Format du fichier excel "+fileName+" invalide (nombre de colonnes n'est pas egale a 4 ou "+MAX_CELLS/* ou "+(MAX_CELLS-NBR_POUVOIRS)+"*/);
                }
            } catch(NullPointerException npe){
                throw new Exception("Format du fichier excel "+fileName+" invalide (une case autre que le chemin de l'image (colonne 5) est vide).");
            }
        }
        
        organisezAlphabet(); //On assume que l'ordre n'est pas alphabetique
        closeWorkBook(fileName, wb, false);
    }
    
    /**
     * Vérifie si le fichier fileName éxiste
     */
    public static boolean doesFileExist(String fileName){
        return (new File(fileName)).exists();
    }
    
    // Ne devrait pas etre appeler par quoi que ce soit
    public static void closeWorkBook(String fileName, XSSFWorkbook wb, boolean isWrite) throws IllegalArgumentException, FileNotFoundException, IOException, EOFException, Exception{
        if((fileName == null) || (fileName.isEmpty()))
            throw new IllegalArgumentException("Un fichier d'entree est vide.");
        
        if(isWrite){
            try{
                (new File(fileName)).createNewFile();
            } catch(IOException ioe){
                throw new IOException("Erreur d'I/O lors de la creation du fichier excel "+fileName+".");
            } catch(Exception e){
                throw new Exception("Erreur lors de la creation du fichier excel "+fileName+".");
            }

            try{
                wb.write(new FileOutputStream(fileName));
                wb.close();
            } catch(EOFException eofe){
                throw new EOFException("Atteinte de fin de fichier inattendis lors de l'ecriture du fichier excel "+fileName+".");
            } catch(IOException ioe){
                throw new IOException("Erreur d'I/O lors de l'ecriture du fichier excel "+fileName+".");
            } catch(Exception e){
                throw new Exception("Erreur lors de l'ecriture du fichier excel "+fileName+".");
            }
        }
        else{
           try{
                wb.close();
            } catch(IOException ioe){
                throw new IOException("Erreur d'I/O lors de la fermeture du fichier excel "+fileName+".");
            } catch(Exception e){
                throw new Exception("Erreur lors de la fermeture du fichier excel "+fileName+".");
            }
        }
    }
}