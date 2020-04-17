
package classcraft;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.apache.poi.xssf.usermodel.XSSFPictureData;

/**
 *
 * @author user
 */
public class FrameEtudiant extends JFrame {
    JPanel panneau;
    Etudiant unEtudiant;
    ListeDesEtudiants liste;
    String fileName;
    
    public FrameEtudiant(Etudiant unEtudiant, ListeDesEtudiants liste, String fileName){
        this.unEtudiant = unEtudiant;
        this.liste = liste;
        this.fileName = fileName;
        
        setSize(500,500);
        panneau = new JPanel();
        JLabel nAdmission = new JLabel("Numero d'admission: "+unEtudiant.getNAdmission());
        JLabel nomEtPrenom = new JLabel("Nom et prenom: "+unEtudiant.getName());
        JLabel role = new JLabel("Role: "+unEtudiant.getRole());
        JLabel pseudo = new JLabel("Pseudo: "+unEtudiant.getPseudo());
        JLabel niveau = new JLabel("Niveau: "+(unEtudiant.getNiveau()+((unEtudiant.getExp() == 1) ? 0.5 : 0)));
        JLabel pv = new JLabel("PV: "+unEtudiant.getPv());
        
        panneau.add(nAdmission);
        panneau.add(nomEtPrenom);
        panneau.add(role);
        panneau.add(pseudo);
        panneau.add(niveau);
        panneau.add(pv);
        add(panneau);
    }
    
    @Override
    public void paint(Graphics g){
        super.paintComponents(g);
        
        panneau = new JPanel();
        GridLayout layout = new GridLayout();
        Graphics2D g2d = (Graphics2D)g;
        
        XSSFPictureData dataImage = null;
        try {
            dataImage = liste.getImage(unEtudiant, fileName);
        } catch(FileNotFoundException fnfe){
            JOptionPane.showMessageDialog(null, fnfe.getMessage());
            dispose();
        } catch(IOException ioe){
            JOptionPane.showMessageDialog(null, ioe.getMessage());
            dispose();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            dispose();
        }
        
        if(dataImage == null){
            JOptionPane.showMessageDialog(null, "Les images ne sont pas dans le fichier "+fileName);
            dispose();
        }
        
        try {
            InputStream dataInputStream = dataImage.getPackagePart().getInputStream();
            BufferedImage bImage = ImageIO.read(dataInputStream);
            g2d.drawImage(bImage, 250, 250, null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erreur imprevus lors de l'obtention de l'image.");
            dispose();
        }
    }
}
