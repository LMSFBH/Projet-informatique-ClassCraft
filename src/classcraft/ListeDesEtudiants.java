package classcraft;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 *   @version 2.4
 *   Gestion pas completement faites des images
     Il faut s'entendre sur l'entrer des images
     Definir une taille maximale
 *   @author  Nour Asmani
 */

public class ListeDesEtudiants{
    public static final int MAX_CELLS = 14;
    public static final int NBR_POUVOIRS = 6;
    public static final int IMG_POS = 15;
    public static final String DEFAULT_IMAGE = "default.png"; 
    
    private ArrayList<Etudiant> etudiants = new ArrayList<>();
    
    //Initialise la liste d'etudiant avec un fichier de chemin fichierAvatar
    public ListeDesEtudiants(String fichierAvatars) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        setToutEtudiants(fichierAvatars);
    }
    
    public ListeDesEtudiants(File fichierAvatars) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        this(fichierAvatars.getCanonicalPath());
    }

    public Etudiant getEtudiant(int index){
        return etudiants.get(index);
    }

    public int getEtudiantsSize(){
        return etudiants.size();
    }
    
    //Adapte de https://stackoverflow.com/a/52875928
    public static void convertCsvToXlsx(String xlsLocation, String csvLocation) throws FileNotFoundException, IOException, Exception {
        if((csvLocation == null) || (csvLocation.isEmpty()))
            throw new IllegalArgumentException("Un fichier d'entree est vide.");
        
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet();
        AtomicReference<Integer> row = new AtomicReference<>(0);
        
        if(!doesFileExist(csvLocation))
            throw new FileNotFoundException("Fichier csv "+csvLocation+" introuvable pour la conversion.");
        
        try{
            (new File(xlsLocation)).createNewFile();
        } catch(IOException ioe){
            throw new IOException("Erreur d'I/O lors de la creation du fichier excel "+xlsLocation+" lors de la conversions.");
        } catch(Exception e){
            throw new Exception("Erreur lors de la creation du fichier excel "+xlsLocation+" lors de la conversions.");
        }
        
        try{
        Files.readAllLines(Paths.get(csvLocation)).forEach(line -> {
            Row currentRow = sheet.createRow(row.getAndSet(row.get() + 1));
            String[] nextLine = line.split(",");
            
            Stream.iterate(0, i -> i + 1).limit(nextLine.length).forEach(i -> {
                currentRow.createCell(i).setCellValue(nextLine[i]);
            });
        });
        
        } catch(IOException ioe){
            throw new IOException("Erreur d'I/O lors de la conversion.");
        } catch(Exception e){
            throw new Exception("Erreur lors de la conversion.");
        }
        
        try (FileOutputStream fos = new FileOutputStream(new File(xlsLocation))) {
            workbook.write(fos);
        } catch (IOException ioe) {
            throw new IOException("Erreur d'I/O lors de l'ecriture apres la conversion de "+csvLocation+".");
            //Logger.getLogger(ListeDesEtudiants.class.getName()).log(Level.SEVERE, null, ioe);
        }
    }
    
    public void setEtudiant(int index, Etudiant unEtudiant){
        etudiants.set(index, unEtudiant);
    }
    
    //Ajoute un etudiant et reorganise la liste
    public void addEtudiant(Etudiant unEtudiant) throws Exception{
        if(!etudiants.add(unEtudiant))
            throw new Exception("L'etudiant "+unEtudiant.getName()+" de numero de DA "+unEtudiant.getNAdmission()+" n'a pas pu etre ajouter.");
        
        organisezAlphabet();
    }
    
    //Supprime un etudiant et reorganise la liste
    public void rmEtudiant(Etudiant unEtudiant) throws Exception{
        if(!etudiants.remove(unEtudiant))
            throw new Exception("L'etudiant "+unEtudiant.getName()+" de numero de DA "+unEtudiant.getNAdmission()+" n'a pas pu etre supprimer.");
        
        organisezAlphabet();
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
    
    public static ArrayList<XSSFPictureData> getAllImages(String fileName) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        if((fileName == null) || (fileName.isEmpty()))
            throw new IllegalArgumentException("Un fichier d'entree est vide.");
        
        XSSFWorkbook wb = null;
        
        try{
            wb = new XSSFWorkbook(new FileInputStream(fileName));
        } catch(FileNotFoundException fnfe){
            throw new FileNotFoundException("La liste d'etudiant "+fileName+" est introuvable lors de la lecture d'image.");
        } catch(IOException ioe){
            throw new IOException("Erreur d'I/O lors de la lecture de la liste d'etudiant "+fileName+" lors de la lecture d'image.");
        } catch(Exception e){
            throw new Exception("Erreur lors de la lecture de la liste d'etudiant "+fileName+" lors de la lecture d'image.");
        }
        
        ArrayList<XSSFPictureData> ret = (ArrayList<XSSFPictureData>) wb.getAllPictures();
        closeWorkBook(fileName, wb, false);
        
        return ret;
    }
    
    //Obtient une image du fichier fileName
    public XSSFPictureData getImage(Etudiant etudiant, String fileName) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        ArrayList<XSSFPictureData> ret = getAllImages(fileName);
        
        if(etudiants.indexOf(etudiant) == -1)
            throw new Exception("L'etudiant "+etudiant.getName()+" de numero de DA "+etudiant.getNAdmission()+" n'est pas dans la liste.");
        
        return ret.get(etudiants.indexOf(etudiant));
    }
    
    public void writeToutEtudiantsEtImages(File file, boolean writeImage) throws IllegalArgumentException, FileNotFoundException, IOException, EOFException, Exception{
        writeToutEtudiantsEtImages(file.getCanonicalPath(), writeImage);
    }
    
    //Ecrit tout les etudiants et les images liee de cet objet dans un fichier excel de nom fileName
    //ecrit ou pas les images selon writeImage, a faire que si un etudiant a ete ajouter/retirer ou la premiere ecriture du fichier
    public void writeToutEtudiantsEtImages(String fileName, boolean writeImage) throws IllegalArgumentException, FileNotFoundException, IOException, EOFException, Exception{
        if((fileName == null) || (fileName.isEmpty()))
            throw new IllegalArgumentException("Un fichier d'entree est vide.");
        
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        
        Row ligne = null;
        Cell cellule = null;
        
        for (int i=0;i < etudiants.size();i++){
            ligne = sheet.createRow(i);
            Etudiant currEtudiant = etudiants.get(i);
            String img = currEtudiant.getCheminImage();
            
            // Ecriture etudiant
            cellule = ligne.createCell(0);
            cellule.setCellValue(currEtudiant.getNAdmission());
            cellule = ligne.createCell(1);
            cellule.setCellValue(currEtudiant.getName());
            cellule = ligne.createCell(2);
            cellule.setCellValue(currEtudiant.getRole());
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
            
            // Images
            if(writeImage){
                File imgFile = null;
                if(!((img == null) || img.isEmpty())){
                    imgFile = new File(img);
                    
                    if(!imgFile.exists())
                        throw new FileNotFoundException("Le fichier image "+img+" n'existe pas, veuillez selectionnez une image valide.");
                }

                int pictureIdx;
                try{
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    BufferedImage bImage = null;
                    
                    //Convertis l'image
                    if((img == null) || img.isEmpty())
                        bImage = ImageIO.read(new File(DEFAULT_IMAGE));
                    else
                        bImage = ImageIO.read(imgFile);
                    
                    ImageIO.write(bImage, "png", os);
                    pictureIdx = wb.addPicture(os.toByteArray(), Workbook.PICTURE_TYPE_PNG);
                } catch (IOException ioe){
                    throw new IOException("Erreur d'acces a l'image "+((img == null) ? DEFAULT_IMAGE : img)+".");
                }

                CreationHelper helper = wb.getCreationHelper();

                // Toute les formes excels sont dessiner a partir du Partriarch
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = helper.createClientAnchor();

                //repositionnez l'image, pour pict.resize()
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                anchor.setCol1(IMG_POS);
                anchor.setRow1(i);

                Picture pict = drawing.createPicture(anchor, pictureIdx);
                pict.resize();
            }
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
        String numAdmission;
        DataFormatter formatter = new DataFormatter();
        for (int i=0;((ligne = sheet.getRow(i)) != null);i++){
            try{
                switch (ligne.getLastCellNum()) {
                    case 2:
                        numAdmission = formatter.formatCellValue(ligne.getCell(0));
                        unEtudiant = new Etudiant(String.valueOf(ligne.getCell(0).getNumericCellValue()), ligne.getCell(1).getStringCellValue());
                        
                        etudiants.add(unEtudiant);
                        break;
                    case MAX_CELLS - NBR_POUVOIRS: // 8 parametres (nbr d'admission, nom, pseudos, role, chemin de l'image, exp, pv, lvl)
                        if(ligne.getCell(4) == null)
                            img = null;
                        else
                            img = ligne.getCell(4).getStringCellValue();
                        
                        numAdmission = formatter.formatCellValue(ligne.getCell(0));
                        unEtudiant = new Etudiant(/*String.valueOf((int)ligne.getCell(0).getNumericCellValue())*/numAdmission, ligne.getCell(1).getStringCellValue(), ligne.getCell(2).getStringCellValue(), ligne.getCell(3).getStringCellValue(), img,
                                                   (int)ligne.getCell(5).getNumericCellValue(), (int)ligne.getCell(6).getNumericCellValue(), (int)ligne.getCell(7).getNumericCellValue());
                        if(etudiants.contains(unEtudiant))
                            throw new Exception("2 etudiants ne peuvent pas etre pareil.");
                        
                        etudiants.add(unEtudiant);
                        break;
                    case MAX_CELLS: // 13 parametres [(nbr admission, nom, pseudos, role, chemin de l'image, exp, pv, lvl) + 5 pouvoirs]
                        if(ligne.getCell(4) == null)
                            img = null;
                        else
                            img = ligne.getCell(4).getStringCellValue();
                        
                        numAdmission = formatter.formatCellValue(ligne.getCell(0));
                        unEtudiant = new Etudiant(numAdmission, ligne.getCell(1).getStringCellValue(), ligne.getCell(2).getStringCellValue(), ligne.getCell(3).getStringCellValue(), img,
                                                   (int)ligne.getCell(5).getNumericCellValue(), (int)ligne.getCell(6).getNumericCellValue(), (int)ligne.getCell(7).getNumericCellValue());
                        if(etudiants.contains(unEtudiant))
                            throw new Exception("2 etudiants ne peuvent pas etre pareil.");
                        
                        etudiants.add(unEtudiant);
                        //Pouvoirs
                        break;
                    default:
                        throw new Exception("Format du fichier excel "+fileName+" invalide (nombre de colonnes n'est pas egale a 2, "+MAX_CELLS+" ou "+(MAX_CELLS-NBR_POUVOIRS)+".");
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