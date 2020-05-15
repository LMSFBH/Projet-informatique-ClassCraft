/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseAventure;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Brecht
 */

public class FrameNomRole extends JFrame{

    JPanel panneauClique = new JPanel();
    JButton confirmer = new JButton("Confirmer");
    JLabel classeActuelle ;
    JLabel nouvelleClasse ;
    JTextField nomClasse ;

    
    public FrameNomRole(ListeDesEtudiants liste, int indexEtudiant){

        setTitle("Changer le nom de la classe");    
        setSize(403,250);
        setVisible(false);

        classeActuelle = new JLabel("Nom de la classe actuelle:     "+ liste.getEtudiant(indexEtudiant).getRole().getRole());
        nouvelleClasse = new JLabel("Nouveau nom de la classe: ");
        nomClasse = new JTextField(10);
        nomClasse.setText(""+ liste.getEtudiant(indexEtudiant).getRole().getRole());

        GridBagLayout gbl = new GridBagLayout();
        panneauClique.setLayout(gbl);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth=2;

        panneauClique.add(classeActuelle, constraints);
        constraints.gridy++;
        panneauClique.add(nouvelleClasse,constraints);
        constraints.gridx+=2;
        panneauClique.add(nomClasse,constraints);
        constraints.gridy++;
        int placeRole =0;
        confirmer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //classeActuelle.setText(MainFrame.liste.getEtudiant(indexEtudiant).getRole().getRole());
                int verif = 0;
                for(int i=0; i<Etudiant.roles.length; i++){
                    if(Etudiant.roles[i].getRole().equalsIgnoreCase(nomClasse.getText()) ){
                         JOptionPane.showMessageDialog(null,"La classe "+nomClasse.getText()+" existe deja.\nVeuiller changer la classe de l'eleve au lieu du nom de la classe.");
                         i=Etudiant.roles.length;
                         verif=1;
                    } 
                }
                if( verif ==0){
                    classeActuelle.setText(MainFrame.role[indexEtudiant].getText());
                    for(int i=0; i<Etudiant.roles.length; i++){
                        if(Etudiant.roles[i].getRole().equalsIgnoreCase(liste.getEtudiant(indexEtudiant).getRole().getRole())){
                            //changer role dans tableau role
                            
                        }
                    }
                    for(int k=0; k<MainFrame.role.length; k++){  
                        Etudiant.roles[liste.getEtudiant(indexEtudiant).getRoleIndex()].setRole(nomClasse.getText());       
                        if(MainFrame.role[k].getText().equalsIgnoreCase(classeActuelle.getText()) ){
                             MainFrame.role[k].setText(nomClasse.getText());
                        }    
                        //JOptionPane.showMessageDialog(null, "nom du label: "+MainFrame.rol[k].getText()+"\n new name:      "+nomClasse.getText());
                    }
                    classeActuelle.setText("Nom de la classe actuelle:     "+ nomClasse.getText());
                }
            }});

        panneauClique.add(confirmer,constraints);
        add(panneauClique);
        }     
}
