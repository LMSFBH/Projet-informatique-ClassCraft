/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseEtDragons;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;

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
    File ancienneImage, nouvelleImage;
    BufferedReader entree;
    BufferedWriter sortie;
    String ancienNom;
    
    public FrameNomRole(ListeDesEtudiants liste, Etudiant unEtudiant)throws FileNotFoundException {
        int indexEtudiant = liste.getIndex(unEtudiant);
        
        setTitle("Changer le nom de la classe");    
        setSize(403,250);
        Image icone = Toolkit.getDefaultToolkit().getImage("image/dragon.jpg");
        setIconImage(icone);
        setVisible(false);

        classeActuelle = new JLabel("Nom de la classe actuelle:     "+ liste.getEtudiant(indexEtudiant).getRole().getNomRole());
        nouvelleClasse = new JLabel("Nouveau nom de la classe: ");
        nomClasse = new JTextField(10);
        nomClasse.setText(""+ liste.getEtudiant(indexEtudiant).getRole().getNomRole());

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
        
        confirmer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int verif = 0;
                ancienNom = liste.getEtudiant(indexEtudiant).getRole().getNomRole();
                ancienneImage = new File("image/"+ancienNom+".png");
                for(int i=0; i<Etudiant.roles.length; i++){
                    if(Etudiant.roles[i].getNomRole().equalsIgnoreCase(nomClasse.getText()) ){
                         JOptionPane.showMessageDialog(null,"La classe "+nomClasse.getText()+" existe deja.\nVeuiller changer la classe de l'eleve au lieu du nom de la classe.");
                         i=Etudiant.roles.length;
                         verif=1;
                    } 
                }
                
                if( verif ==0){
                    classeActuelle.setText(MainFrame.role[indexEtudiant].getText());
                    
                    for(int k=0; k<MainFrame.role.length; k++){  
                        Etudiant.roles[liste.getEtudiant(indexEtudiant).getRoleIndex()].setNomRole(nomClasse.getText());       
                        if(MainFrame.role[k].getText().equalsIgnoreCase(classeActuelle.getText()) ){
                             MainFrame.role[k].setText(nomClasse.getText());
                        }
                    }
                    classeActuelle.setText("Nom de la classe actuelle:     "+ nomClasse.getText());
                    nouvelleImage = new File ("image/"+nomClasse.getText()+".png");
                    ancienneImage.renameTo(nouvelleImage);
                    
                    try{
                        entree= new BufferedReader(new FileReader("docs/roles.txt"));
                        sortie = new BufferedWriter(new FileWriter("docs/test.txt"));
                        String ligne, nomRole=null;
                        int nombreLigne = 0, vie=0, i=0;
                        while((ligne=entree .readLine() ) != null){
                            if(ligne.equals(ancienNom)){
                                sortie.write(nomClasse.getText()); 
                                sortie.newLine();
                            }else{
                                sortie.write(ligne); 
                                sortie.newLine();
                            }
                        }
                    } catch (FileNotFoundException erreur)    { // Exception déclenchée si le fichier n'existe pas 
                        JOptionPane.showMessageDialog(null,"Le fichier role n'existe pas");
                        return;
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,"Erreur d'I/O lors de la lecture du fichier role");
                        return;
                    }
                    finally {
                        try {
                            entree.close();
                            sortie.close();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null,"Il y a un problème lors de la fermeture du fichier");
                            return;
                        }
                    }
                    
                    new File("docs/roles.txt").delete();
                    new File("docs/test.txt").renameTo(new File("docs/roles.txt"));

                }
            }
        });

        panneauClique.add(confirmer,constraints);
        add(panneauClique);
    }
}
