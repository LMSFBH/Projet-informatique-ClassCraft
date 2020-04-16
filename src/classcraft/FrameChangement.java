/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Pierre
 */
public class FrameChangement extends JFrame{
    JPanel changementInfo;
    JButton confirmer, annuler;
    Etudiant unEtudiant;
    String fileName;
    JTextArea nomChangement, pseudoChangement;
    JComboBox roleChangement;
    
    public FrameChangement(Etudiant unEtudiant, String fileName){
        changementInfo = new JPanel();
        setSize(500,500);
        JLabel nom = new JLabel("Nom: ");
        nomChangement = new JTextArea(unEtudiant.getName());
        JLabel pseudo = new JLabel("Pseudo: ");
        pseudoChangement = new JTextArea(unEtudiant.getPseudo());
        JLabel role = new JLabel("Rôle: ");
        roleChangement = new JComboBox();
        
        confirmer = new JButton("Enregistrer");
        confirmer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                unEtudiant.setName(nomChangement.getText());
                FrameEtudiant.nomEtPrenom.setText("Nom et prénom: "+unEtudiant.getName());

                unEtudiant.setPseudo(pseudoChangement.getText());
                FrameEtudiant.pseudo.setText("Pseudo: "+unEtudiant.getPseudo());
                //rajouter le changement du role quand la classe sera faite//rajouter le changement du role quand la classe sera faite
                JOptionPane.showMessageDialog(null, "Modification effectuée");
            }
        });
        
        annuler = new JButton("Annuler");
        annuler.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "Modification annulée");
            }
        });
        
        GridBagLayout gbl = new GridBagLayout();
	changementInfo.setLayout(gbl);
	GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx=0;
	constraints.gridy=0;
        
        changementInfo.add(nom, constraints);
        constraints.gridx++;
        changementInfo.add(nomChangement, constraints);
        constraints.gridx=0;
        constraints.gridy++;
        changementInfo.add(pseudo, constraints);
        constraints.gridx++;
        changementInfo.add(pseudoChangement, constraints);
        constraints.gridx=0;
        constraints.gridy++;
        changementInfo.add(role, constraints);
        constraints.gridx++;
        changementInfo.add(roleChangement, constraints);
        constraints.gridx=0;
        constraints.gridy++;
        changementInfo.add(confirmer, constraints);
        constraints.gridx++;
        changementInfo.add(annuler, constraints);
        add(changementInfo);
    }
    
}
