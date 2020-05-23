
package ClasseEtDragons;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Frame affichant les informations de l'étudiant
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
        Image icone = Toolkit.getDefaultToolkit().getImage("image/dragon.jpg");
        setIconImage(icone);
        panneau = new JPanel();
        JLabel nAdmission = new JLabel("Numéro d'admission: "+unEtudiant.getNAdmission());
        nomEtPrenom = new JLabel("Nom et prénom: "+unEtudiant.getName());
        role = new JLabel("Rôle: "+unEtudiant.getRole().getNomRole());
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
        Graphics2D g2d = (Graphics2D)g;
        
        Image image = null;
        
        if(ListeDesEtudiants.doesFileExist("image/"+unEtudiant.getNAdmission()+".png"))
            image = getToolkit().getImage("image/"+unEtudiant.getNAdmission()+".png");
        else
            image = getToolkit().getImage("image/"+unEtudiant.getRole().getNomRole()+".png");
        
        g2d.drawImage(image, 175, 100, 100, 100, this);
    }
    
    private class GestAction implements ActionListener{
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

