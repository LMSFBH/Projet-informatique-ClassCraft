/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;

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
    JPanel panneau;
    JButton activer, annuler;
    
    public FramePouvoir(Etudiant currEtudiant, ListeDesEtudiants liste,int indexPouvoir){
        setSize(900,900);
        panneau = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
	panneau.setLayout(gbl);
	GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx=0;
	constraints.gridy=0;
	constraints.gridwidth=2;
        
        JLabel description = new JLabel("description");
        panneau.add(description, constraints);
        
        constraints.gridy++;
        constraints.gridwidth=1;
        activer = new JButton("Activer");
        if(MainFrame.listePouvoirs[liste.getIndex(currEtudiant)][indexPouvoir].getBackground()!=Color.green){
            activer.setEnabled(false);
        }
        activer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "Pouvoir activé");
                MainFrame.listePouvoirs[liste.getIndex(currEtudiant)][indexPouvoir].setBackground(Color.yellow);
                dispose();
            }
        });
        panneau.add(activer, constraints);
        
        constraints.gridx++;
        annuler = new JButton("Annuler");
        panneau.add(annuler, constraints);
        
        add(panneau);
    } 
}