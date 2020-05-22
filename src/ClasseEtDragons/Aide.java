/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClasseEtDragons;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author erwin
 */
public class Aide extends JFrame {
    JPanel panneauAide= new JPanel();
    JTextArea aide = new JTextArea() ;
    JScrollPane barreDefilante = new JScrollPane();
    String ligne;
    
    public Aide() throws IOException{
        
        setTitle("Aide");
        setSize(1400,500);
        Image icone = Toolkit.getDefaultToolkit().getImage("image/dragon.jpg");
        setIconImage(icone);
        aide.setEditable(false);
        try{
        BufferedReader entree = new BufferedReader(new FileReader("documents textes/aide.txt"));
        
        while((ligne=entree.readLine())!=null){
            ligne+="\n";
            aide.append(ligne);
        }
            
        panneauAide.add(aide);
        entree.close();
        } catch (FileNotFoundException e)    { // Exception déclenchée si le fichier n'existe pas 
            JOptionPane.showMessageDialog(null, "Le fichier n'existe pas");
        } catch (EOFException e)    { // Exception déclenchée si la fin du fichier est atteinte 
            JOptionPane.showMessageDialog(null, "La lecture du fichier est atteint");
        } catch (IOException e)    { // Exception déclenchée si un autre problème de fichier 
            JOptionPane.showMessageDialog(null, "Le fichier a eu un problème lors de sa fermeture");
        }

        add(panneauAide);
    }

}
