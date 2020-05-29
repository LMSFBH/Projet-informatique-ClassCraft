package ClasseEtDragons;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 *   @version 2.4
 *   Gestion pas completement faites des images
     Il faut s'entendre sur l'entrer des images
     Definir une taille maximale
 *   @author  Nour Asmani
 */

public class ListeDesEtudiants{
    public static final int PARAM_ETUDIANTS = 8;
    public static final int NBR_POUVOIRS = 6;
    public static final int MAX_CELLS = PARAM_ETUDIANTS + NBR_POUVOIRS;
    public static final int IMG_POS = 15;
    
    private ArrayList<Etudiant> etudiants = new ArrayList<>();
    
    /**
     * Constructeur à 1 paramètres pour ListeDesEtudiants
     * 
     * @param fichierAvatars Chemin canonique du fichier excel contenant la liste des étudiants
     */
    public ListeDesEtudiants(String fichierAvatars) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        setToutEtudiants(fichierAvatars);
    }
    
    /**
     * Constructeur à 1 paramètres pour ListeDesEtudiants
     * 
     * @param fichierAvatars Fichier excel contenant la liste des étudiants
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
     * @param unEtudiant Index de l'étudiant à obtenir
     */
    public void setEtudiant(int index, Etudiant unEtudiant){
        etudiants.set(index, unEtudiant);
    }
    
    //Ajoute un etudiant et reorganise la liste
    public void addEtudiant(Etudiant unEtudiant) throws Exception{
        if(!etudiants.add(unEtudiant))
            throw new Exception("L'etudiant "+unEtudiant.getName()+" de numero de DA "+unEtudiant.getNAdmission()+" n'a pas pu etre ajouter.");
        
        organisezAlphabet();
    }
        
    public void rmEtudiant(int index) throws Exception{
        if((index < 0) || (index > etudiants.size()))
            throw new Exception("L'etudiant n'a pas pu etre supprimer.");
        
        etudiants.remove(index);
    }
    
    //Organise la liste
    //Est appeler par addEtudiant/rmEtudiant
    public void organisezAlphabet(){
        etudiants.sort((Etudiant etudiant1, Etudiant etudiant2) -> etudiant1.getName().compareToIgnoreCase(etudiant2.getName()));
    }
    
    public void organisezClassement(boolean isCroissant){
        etudiants.sort((Etudiant etudiant1, Etudiant etudiant2) -> {
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
        });
    }
    
    //Ecrit tout les etudiants et les images liee de cet objet dans un fichier excel de nom fileName
    //ecrit ou pas les images selon writeImage, a faire que si un etudiant a ete ajouter/retirer ou la premiere ecriture du fichier
    public void writeToutEtudiantsEtImages(String fileName) throws IllegalArgumentException, FileNotFoundException, IOException, EOFException, Exception{
        if((fileName == null) || (fileName.isEmpty()))
            throw new IllegalArgumentException("Un fichier d'entree est vide.");
        
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        
        Row ligne;
        Cell cellule;
        
        ligne = sheet.createRow(0);
        cellule = ligne.createCell(0);
        cellule.setCellValue("Numéros d'admission");
        cellule = ligne.createCell(1);
        cellule.setCellValue("Noms et prénoms");
        cellule = ligne.createCell(2);
        cellule.setCellValue("Numéro du rôle");
        cellule = ligne.createCell(3);
        cellule.setCellValue("Pseudo de l'élève");
        
        for (int i=0;i < etudiants.size();i++){
            ligne = sheet.createRow(i+1);
            Etudiant currEtudiant = etudiants.get(i);
            String img = currEtudiant.getCheminImage();
            
            // Ecriture etudiant
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
    
    //Appeler lors de l'initialisation de cet objet, ne devrait pas etre appeler autrement
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
            
            if((ligne = sheet.getRow(i)) == null)
                continue;
            
            try{
                switch (ligne.getLastCellNum()) {
                    case 4:
                        unEtudiant = new Etudiant(formatter.formatCellValue(ligne.getCell(0)), ligne.getCell(1).getStringCellValue(), (int)ligne.getCell(2).getNumericCellValue(), ligne.getCell(3).getStringCellValue());
                        
                        if(etudiants.contains(unEtudiant))
                            throw new Exception("2 etudiants ne peuvent pas etre pareil.");
                        
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
                        
                        if(etudiants.contains(unEtudiant))
                            throw new Exception("2 etudiants ne peuvent pas etre pareil.");
                        
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
    
    public static boolean doesFileExist(String fileName){
        return (new File(fileName)).exists();
    }
    
    //Ne devrait pas etre appeler par quoi que ce soit
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