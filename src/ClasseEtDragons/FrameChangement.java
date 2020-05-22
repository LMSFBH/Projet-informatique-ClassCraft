/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseEtDragons;

import static ClasseEtDragons.MainFrame.ouiOuNon;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Pierre
 */
public class FrameChangement extends JFrame{
    JPanel changementInfo;
    JButton confirmer, annuler, imageChangement;
    Etudiant unEtudiant;
    String fileName;
    JTextArea nomChangement, pseudoChangement;
    JComboBox<String> roleChangement;
    
    public FrameChangement(Etudiant unEtudiant, ListeDesEtudiants liste, String fileName){
        changementInfo = new JPanel();
        setSize(500,500);
        JLabel nom = new JLabel("Nom: ");
        nomChangement = new JTextArea(unEtudiant.getName());
        JLabel pseudo = new JLabel("Pseudo: ");
        pseudoChangement = new JTextArea(unEtudiant.getPseudo());
        JLabel role = new JLabel("Rôle: ");
        roleChangement = new JComboBox<String>();
        for(int i=0; i<unEtudiant.roles.length; i++){
            roleChangement.addItem(unEtudiant.roles[i].getNomRole());
        }
        roleChangement.setSelectedIndex(unEtudiant.getRoleIndex());
        
        imageChangement = new JButton("Modifier l'image");
        imageChangement.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser choix = new JFileChooser(".");
                choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
                choix.setDialogTitle("Sélectionnez l'image de l'étudiant "+unEtudiant.getName());

                int resultat = JFileChooser.CANCEL_OPTION;

                while(resultat != JFileChooser.APPROVE_OPTION){
                    resultat = choix.showOpenDialog(null);

                    switch (resultat) {
                        case JFileChooser.ERROR_OPTION:
                            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du choix de fichier.");

                            if(!ouiOuNon("Voulez-vous recommencer ?", "FERMETURE"))
                                return;
                            break;
                        case JFileChooser.CANCEL_OPTION:
                            return;
                        default:
                            try{
                                unEtudiant.setCheminImage(choix.getSelectedFile().getCanonicalPath());
                                ImageIO.write(ImageIO.read(new File(unEtudiant.getCheminImage())), "png", new File("image/"+unEtudiant.getNAdmission()+".png"));
                            } catch(FileNotFoundException fnfe){
                                JOptionPane.showMessageDialog(null, fnfe.getMessage());
                                resultat = JFileChooser.CANCEL_OPTION;
                            } catch(IOException ioe){
                                JOptionPane.showMessageDialog(null, ioe.getMessage());
                                resultat = JFileChooser.CANCEL_OPTION;
                            } catch(Exception exc){
                                JOptionPane.showMessageDialog(null, exc.getMessage());
                                resultat = JFileChooser.CANCEL_OPTION;
                            }
                            break;
                    }
                }
            }
        });
        
        confirmer = new JButton("Enregistrer");
        confirmer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                unEtudiant.setName(nomChangement.getText());
                FrameEtudiant.nomEtPrenom.setText("Nom et prénom: "+unEtudiant.getName());
                MainFrame.nomEtudiants[liste.getIndex(unEtudiant)].setText(unEtudiant.getName());

                unEtudiant.setPseudo(pseudoChangement.getText());
                FrameEtudiant.pseudo.setText("Pseudo: "+unEtudiant.getPseudo());
                MainFrame.pseudoEtudiant[liste.getIndex(unEtudiant)].setText(unEtudiant.getPseudo());
                
                for(int i=0; i<unEtudiant.roles.length; i++){
                    if(roleChangement.getSelectedItem().equals(unEtudiant.roles[i].getNomRole())){
                        unEtudiant.setRole(i);
                        FrameEtudiant.role.setText("Rôle: "+unEtudiant.getRole().getNomRole());
                        MainFrame.role[liste.getIndex(unEtudiant)].setText(unEtudiant.getRole().getNomRole());
                    }
                }
                
                JOptionPane.showMessageDialog(null, "Modification effectuée");
                
                
                try {
                    liste.writeToutEtudiantsEtImages(fileName);
                }catch(FileNotFoundException fnfe){
                    JOptionPane.showMessageDialog(null, fnfe.getMessage());
                } catch(IOException ioe){
                    JOptionPane.showMessageDialog(null, ioe.getMessage());
                } catch(Exception exc){
                    JOptionPane.showMessageDialog(null, exc.getMessage());
                }
                
                MainFrame.restart();
            }
        });
        
        annuler = new JButton("Annuler");
        annuler.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "Modification annulée");
                dispose();
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
        constraints.gridwidth=2;
        constraints.gridx=0;
        constraints.gridy++;
        changementInfo.add(imageChangement, constraints);
        constraints.gridwidth=1;
        constraints.gridy++;
        changementInfo.add(confirmer, constraints);
        constraints.gridx++;
        changementInfo.add(annuler, constraints);
        add(changementInfo);
    }
    
}
