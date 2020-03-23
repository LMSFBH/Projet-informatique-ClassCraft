
package classcraft;


import java.awt.*;
import javax.swing.*;

/**
 *
 * @author user
 */
public class FrameEtudiant extends JFrame {
    JPanel panneau;
    
    public FrameEtudiant(Etudiant unEtudiant){
        panneau = new JPanel();
        GridLayout layout = new GridLayout();
        
        JLabel nAdmisssion = new JLabel(unEtudiant.getNAdmission());
        JLabel nomEtPrenom = new JLabel(unEtudiant.getName());
        JLabel role = new JLabel(unEtudiant.getRole());
        JLabel pseudo = new JLabel(unEtudiant.getPseudo());
        JLabel niveau = new JLabel(""+(unEtudiant.getNiveau()+((unEtudiant.getExp() == 1) ? 0.5 : 0)));
    }
}