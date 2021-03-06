
package ClasseEtDragons;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import javax.swing.*;

/**
 *  Fenêtre d'aide, dont le texte est dans docs/aide.txt
 *  @author Kegoum Brecht
 */
public class Aide extends JFrame {
    JPanel panneauAide;
    JTextArea aide;
    
    String ligne;
    
    public Aide(){
        setTitle("Aide");
        setSize(1400,500);
        setResizable(false);
        Image icone = Toolkit.getDefaultToolkit().getImage("image/dragon.jpg");
        setIconImage(icone);
        
        panneauAide = new JPanel();
        aide = new JTextArea();
        
        GridBagLayout layout = new GridBagLayout();
        panneauAide.setLayout(layout);
        
        aide.setEditable(false);
        try{
            BufferedReader entree = new BufferedReader(new FileReader("docs/aide.txt"));

            while((ligne=entree.readLine())!=null){
                ligne+="\n";
                aide.append(ligne);
            }
            
            panneauAide.add(aide);
            entree.close();
        } catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Le fichier d'aide n'existe pas. L'aide ne fonctionnera pas.");
        } catch (EOFException e){
            JOptionPane.showMessageDialog(null, "La fin du fichier aide est atteinte de manière étrange. L'aide ne fonctionnera pas.");
        } catch (IOException e){ 
            JOptionPane.showMessageDialog(null, "Erreur d'I/O lors de l'accès du fichier aide. L'aide ne fonctionnera pas.");
        }

        add(panneauAide);
    }

}
