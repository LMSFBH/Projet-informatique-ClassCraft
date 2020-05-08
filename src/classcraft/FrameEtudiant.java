
package ClasseAventure;


import java.awt.*;
import java.awt.event.*;
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
    JButton info;
    static JLabel nomEtPrenom, pseudo, role;
    
    public FrameEtudiant(Etudiant unEtudiant, ListeDesEtudiants liste, String fileName){
        this.unEtudiant = unEtudiant;
        this.liste = liste;
        this.fileName = fileName;
        
        setSize(425,275);
        panneau = new JPanel();
        JLabel nAdmission = new JLabel("Numéro d'admission: "+unEtudiant.getNAdmission());
        nomEtPrenom = new JLabel("Nom et prénom: "+unEtudiant.getName());
        role = new JLabel("Rôle: "+unEtudiant.getRole().getRole());
        pseudo = new JLabel("Pseudo: "+unEtudiant.getPseudo());
        JLabel niveau = new JLabel("Niveau: "+(unEtudiant.getNiveau()+((unEtudiant.getExp() == 1) ? 0.5 : 0)));
        JLabel pv = new JLabel("PV: "+unEtudiant.getPv());
        info = new JButton("Modifier les informations");
        info.addActionListener(new GestAction());
        
        GridBagLayout gbl = new GridBagLayout();
	panneau.setLayout(gbl);
	GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.weightx=5;
        constraints.gridx=0;
	constraints.gridy=0;
	constraints.gridwidth=1;
        
        panneau.add(nAdmission, constraints);
        
        constraints.gridx=1;
        panneau.add(nomEtPrenom, constraints);
        
        constraints.weightx=0;
        constraints.gridwidth=2;
        constraints.gridx=0;
        constraints.gridy=1;
        panneau.add(pseudo, constraints);
        
        constraints.gridy++;
        panneau.add(role, constraints);
        
        constraints.weighty=25;
        constraints.anchor=GridBagConstraints.SOUTH;
        constraints.gridwidth=1;
        constraints.gridy++;
        panneau.add(niveau, constraints);
        
        constraints.gridx++;
        panneau.add(pv, constraints);
        
        constraints.gridwidth=2;
        constraints.weighty=0;
        constraints.anchor=GridBagConstraints.CENTER;
        constraints.gridx=0;
        constraints.gridy++;
        panneau.add(info, constraints);
        add(panneau);
    }
    
    @Override
    public void paint(Graphics g){
        super.paintComponents(g);
        
        panneau = new JPanel();
        GridLayout layout = new GridLayout();
        Graphics2D g2d = (Graphics2D)g;
        String classe = unEtudiant.getRole().getRole();
        
        if(classe.equals("Guerrier")){
            Image guerrier =getToolkit().getImage("guerrier.jpg");
            g2d.drawImage(guerrier, 175, 100, 100, 100, this);
        }
        
        if(classe.equals("Magicien")){
            Image guerrier =getToolkit().getImage("magicien.png");
            g2d.drawImage(guerrier, 175, 100, 100, 100, this);
        }
        
        if(classe.equals("Guerrisseur")){
            Image guerrier =getToolkit().getImage("guerrisseur.png");
            g2d.drawImage(guerrier, 175, 100, 100, 100, this);
        }
        
        if(classe.equals("Voleur")){
            Image guerrier =getToolkit().getImage("voleur.png");
            g2d.drawImage(guerrier, 175, 100, 100, 100, this);
        }
        
        /*
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
            g2d.drawImage(bImage, 175, 100, 100, 100, this);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erreur imprevus lors de l'obtention de l'image.");
            dispose();
        }*/
    }
    
    protected class GestAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            FrameChangement changement = new FrameChangement(unEtudiant, liste, fileName);
            changement.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            changement.setTitle("Modifier les informations");
            changement.setVisible(true);
            changement.setLocationRelativeTo(null);
        }
    }
}

