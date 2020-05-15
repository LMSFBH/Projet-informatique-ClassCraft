/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseAventure;

import static ClasseAventure.MainFrame.boutonUtilisable;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Pierre
 */
public class FramePouvoir extends JFrame {
    JLabel descriptionPouvoir = new JLabel();
    JPanel skill = new JPanel();
    JScrollPane rouleau = new JScrollPane(skill);
    
    JPanel panneau;
    JButton activer, annuler;
    Pouvoir action= new Pouvoir();
    int faire;
    
    public FramePouvoir(Etudiant currEtudiant, ListeDesEtudiants liste,int indexPouvoir){
        setSize(900,900);
        panneau = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
	panneau.setLayout(gbl);
	GridBagConstraints constraints = new GridBagConstraints();
        int indexEtudiant = liste.getIndex(currEtudiant);
        
        constraints.gridx=0;
	constraints.gridy=0;
	constraints.gridwidth=2;
        
        if(indexPouvoir == 0){
            faire=currEtudiant.getRole().getIndexNv5();
        }
        
        if(indexPouvoir == 1){
            faire=currEtudiant.getRole().getIndexNv10();
        }
        
        if(indexPouvoir == 2){
            faire=currEtudiant.getRole().getIndexNv15();
        }
        
        if(indexPouvoir == 3){
            faire=currEtudiant.getRole().getIndexNv20();
        }
        
        if(indexPouvoir == 4){
            faire=currEtudiant.getRole().getIndexNv25();
        }
        
        if(indexPouvoir == 5){
            faire=currEtudiant.getRole().getIndexNv30();
        }
        
        
        JLabel description = new JLabel(action.action(faire,currEtudiant,liste,true));
        panneau.add(description, constraints);
        
        constraints.gridy++;
        constraints.gridwidth=1;
        activer = new JButton("Activer");
        if(!MainFrame.listePouvoirs[liste.getIndex(currEtudiant)][indexPouvoir].getBackground().equals(Color.green)){
            activer.setEnabled(false);
        }
        
        
        activer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                if(liste.getEtudiant(indexEtudiant).getPv()== 0){   // Moi: on fait ça? il ne peux pas accceder a la fenetre pouvoir si l'eleve est mort
                    JOptionPane.showMessageDialog(null,"Pouvoir innacessible! L'éleve est mort");
                }else if(MainFrame.listePouvoirs[indexEtudiant][indexPouvoir].getBackground().equals(MainFrame.couleur3)) {
                    if(MainFrame.ouiOuNon("Voulez vous vraiment utiliser le pouvoir?", "Activer Pouvoir")){
                        
                        JOptionPane.showMessageDialog(null,action.action(faire,currEtudiant,liste,false) );
                        MainFrame.listePouvoirs[liste.getIndex(currEtudiant)][indexPouvoir].setBackground(Color.yellow);
                        MainFrame.listePouvoirs[indexEtudiant][indexPouvoir].setBackground(MainFrame.couleur5);
                        MainFrame.boutonUtilisable[liste.getIndex(currEtudiant)][indexPouvoir]=false;
                    }
                }else if(MainFrame.listePouvoirs[indexEtudiant][indexPouvoir].getBackground().equals(MainFrame.couleur5)) {
                    JOptionPane.showMessageDialog(null,"Le pouvoir est déja actif");
                }else{
                    JOptionPane.showMessageDialog(null,"Le niveau de l'etudiant est insuffisant pour utiliser le pouvoir");
                }   
                //MainFrame.setCouleurPouvoirs(indexEtudiant);
                dispose();
            }
        });
        panneau.add(activer, constraints);
        
        constraints.gridx++;
        annuler = new JButton("Annuler");
        annuler.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
            
        });
        
        panneau.add(annuler, constraints);
        
        add(panneau);
    } 
}
