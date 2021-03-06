
package ClasseEtDragons;

import static ClasseEtDragons.MainFrame.ouiOuNon;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Frame modifiant les informations de l'étudiant
 * @author Pierre Moyne-Bressand, Kegoum Brecht
 */
public class FrameChangement extends JFrame{
    JPanel changementInfo;
    JButton confirmer, annuler, imageChangement;
    Etudiant unEtudiant;
    String fileName;
    JTextField nomChangement, pseudoChangement;
    JComboBox<String> roleChangement;
    
    /**
     * Constructeur à 3 paramètres de FrameChangement
     * 
     * @param unEtudiant Étudiant actuelle
     * @param liste      Liste actuelle
     * @param fileName   fichier
     */
    public FrameChangement(Etudiant unEtudiant, ListeDesEtudiants liste, String fileName){
        changementInfo = new JPanel();
        setSize(500,500);
        Image icone = Toolkit.getDefaultToolkit().getImage("image/dragon.jpg");
        setIconImage(icone);
        JLabel nom = new JLabel("Nom: ");
        nomChangement = new JTextField(10);
        nomChangement.setText(unEtudiant.getName());
        JLabel pseudo = new JLabel("Pseudo: ");
        pseudoChangement = new JTextField(10);
        pseudoChangement.setText(unEtudiant.getPseudo());
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
                
                JFileChooser choix = new JFileChooser("Choix d'une image");
                LookAndFeel lf = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    choix = new JFileChooser(new File(System.getProperty("user.home"), "desktop"));
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("images", ImageIO.getReaderFileSuffixes());
                    choix.setFileFilter(filter);
                    UIManager.setLookAndFeel(lf);
                } catch (Exception exc){
                    JOptionPane.showMessageDialog(null, "Erreur lors du paramétrage du look and feel.");
                }

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
                try{
                    unEtudiant.setName(nomChangement.getText());
                    unEtudiant.setPseudo(pseudoChangement.getText());
                } catch(IllegalArgumentException iae){
                    JOptionPane.showMessageDialog(null, iae.getMessage());
                    return;
                }
                
                FrameEtudiant.nomEtPrenom.setText("Nom et prénom: "+unEtudiant.getName());
                MainFrame.nomEtudiants[liste.getIndex(unEtudiant)].setText(unEtudiant.getName());

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
                
                //Écrire et redémarrez
                try{
                    liste.writeToutEtudiantsEtImages(fileName);
                }catch(FileNotFoundException fnfe){
                    JOptionPane.showMessageDialog(null, fnfe.getMessage());
                    return;
                } catch(IOException ioe){
                    JOptionPane.showMessageDialog(null, ioe.getMessage());
                    return;
                } catch(Exception exc){
                    JOptionPane.showMessageDialog(null, exc.getMessage());
                    return;
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
