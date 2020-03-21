/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 *
 * @author usager
 */
class MainFrame extends JFrame implements ActionListener { 
    
    ListeDesEtudiants liste;
    JPanel panneau = new JPanel();
    int nombreEtudiants;
    
    public MainFrame() throws  FileNotFoundException, IOException, Exception{
        liste = new ListeDesEtudiants("Classeur1.xlsx");
        nombreEtudiants = liste.getEtudiantsSize();
        
        setSize(nombreEtudiants*35, nombreEtudiants*30);
        
        // GridBagLayout
        GridBagLayout gbl = new GridBagLayout();
	panneau.setLayout(gbl);
	GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx=0;
	constraints.gridy=0;
        constraints.weightx=1;
        constraints.weighty=0.01;
        
	constraints.gridwidth=1;
	constraints.gridheight=1;
	constraints.anchor=GridBagConstraints.CENTER;
        
        
        JLabel nom = new JLabel("Nom");
        panneau.add(nom, constraints);
        
        JLabel[] nomEtudiants = new JLabel[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            constraints.gridy++;
            nomEtudiants[i] = new JLabel(liste.getEtudiant(i).getName());
            panneau.add(nomEtudiants[i], constraints);
        }
        
        constraints.gridx++;
        constraints.gridy=0;
        JLabel classe = new JLabel("classe");
        panneau.add(classe, constraints);
        
        JLabel[] role = new JLabel[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            constraints.gridy++;
            role[i] = new JLabel(liste.getEtudiant(i).getRole());
            panneau.add(role[i],constraints);
        }
        
        
        constraints.gridx++;
        constraints.gridy=0;
        JLabel pseudo = new JLabel("Pseudo");
        panneau.add(pseudo, constraints);
        
        JTextField[] pseudoEtudiant = new JTextField[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            constraints.gridy++;
            pseudoEtudiant[i] = new JTextField(liste.getEtudiant(i).getPseudo());
            panneau.add(pseudoEtudiant[i], constraints);
        }
        
	JButton [] Bplus = new JButton[nombreEtudiants];
	for (int i=0; i<nombreEtudiants;i++){
            Bplus[i] = new JButton("+");
            Bplus[i].setMargin(new Insets(0,0,0,0));
            Bplus[i].setPreferredSize(new Dimension(20,20));
            Bplus[i].setActionCommand("inc pv "+i);
            Bplus[i].addActionListener(this);
	}
        
	JButton [] Bmoins = new JButton[nombreEtudiants];
	for (int i=0; i<nombreEtudiants;i++){
            Bmoins[i] = new JButton("-");
            Bmoins[i].setMargin(new Insets(0,0,0,0));
            Bmoins[i].setPreferredSize(new Dimension(20,20));
            Bmoins[i].setActionCommand("dec pv "+i);
            Bmoins[i].addActionListener(this);
	}
        
	// Moi: vies c'est le tableau des points de vie des étudiants. j'aivais déja utiliser pv
	int [] Vies = new int[nombreEtudiants];
	for (int i=0; i<Vies.length;i++){
		// ici on va devoir appeler le tableau des etudiants pour chaque vies
		Vies[i] = liste.getEtudiant(i).getPv();
	}
        

	constraints.gridx=4;// Moi: j'ai mis 4 parce qu'il y a classe, liste et avatar avant pv et les bouttons
	constraints.gridy=0;
        JLabel pv = new JLabel("Points de Vie");
	panneau.add(pv,constraints);
	
	for(int i=0; i<nombreEtudiants;i++){
            constraints.gridy++;
            panneau.add(new JLabel(""+Vies[i]),constraints);
            constraints.anchor=GridBagConstraints.EAST;
            panneau.add(Bplus[i],constraints);
            constraints.anchor=GridBagConstraints.WEST;
            panneau.add(Bmoins[i],constraints);
            constraints.anchor=GridBagConstraints.CENTER;
	}
        
        constraints.gridx=5;
        constraints.gridy=0;
        JLabel exp = new JLabel("Experience");
        panneau.add(exp,constraints);
        
        int ExpMax=2;
        int Exp=0;
        JProgressBar[] progressBar = new JProgressBar[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            progressBar[i] = new JProgressBar();
            progressBar[i].setMaximum(ExpMax);
            progressBar[i].setValue(0);
            progressBar[i].setStringPainted(true);
        }
        
        
        
        for(int i=0; i<nombreEtudiants; i++){
            constraints.gridy++;
            progressBar[i].setString("LV"+liste.getEtudiant(i).getNiveau()+"                            ");
            progressBar[i].setValue(liste.getEtudiant(i).getExp());
            panneau.add(progressBar[i],constraints);	
        }
                
        constraints.gridx=6;
        constraints.gridy=0;
        constraints.gridwidth=6;
        constraints.weightx=1;
        
        JLabel pouvoir = new JLabel("Pouvoirs");
        panneau.add(pouvoir,constraints);
        
         // liste de pouvoirs pour un eleve
        int POUVOIRS_MAX = 6;
        constraints.gridwidth=1;
        int lv = 0; // niveau ou on gagne un pouvoir
        constraints.gridy++;
        JButton ListePouvoirs[][] = new JButton[nombreEtudiants][POUVOIRS_MAX];
        for (int i=0; i<ListePouvoirs.length;i++){ 
            for (int j=0; j<POUVOIRS_MAX;j++){ 
                lv+=5;
                ListePouvoirs[i][j] = new JButton(""+lv);
                ListePouvoirs[i][j].setMargin(new Insets(0,0,0,0));
                ListePouvoirs[i][j].setPreferredSize(new Dimension(35,20));
                ListePouvoirs[i][j].setActionCommand("pouvoir "+i+" "+j);
                ListePouvoirs[i][j].addActionListener(this);
                panneau.add(ListePouvoirs[i][j],constraints);
                constraints.gridx++;
            }
            constraints.gridy++;
            constraints.gridx-=POUVOIRS_MAX;
            lv=0;
            
        }
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                String[] options = {"Oui", "Non"};
                if(JOptionPane.showOptionDialog(null, "Etes-vous sure de vouloir fermer l'application?", "FERMETURE", JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.INFORMATION_MESSAGE, null, options, options[0]) == 0){
                    try{
                        //L'ecriture des images prends beaucoups de temps
                        String fichierImg = JOptionPane.showInputDialog("Veuillez selectionnez l'emplacement du fichier excel de sauvegarde. \n(L'operation pourrait prendre plus de temps si les images sont a ecrire)");
                        //if(liste.isModif()){
                            liste.writeToutEtudiantsEtImages(fichierImg, true);
                        //else
                        //    liste.writeToutEtudiantsEtImages(JOptionPane.showInputDialog("Veuillez selectionnez l'emplacement du fichier excel de sauvegarde."), false);
                            
                    }catch(FileNotFoundException fnfe){
                        JOptionPane.showMessageDialog(null, fnfe.getMessage());
                    } catch(IOException ioe){
                        JOptionPane.showMessageDialog(null, ioe.getMessage());
                    } catch(Exception e){
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    
                    System.exit(0);
                }
            }  
        });
        
        add(panneau);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        int indexEtudiant;
        try{
            indexEtudiant = Integer.parseInt(cmd.substring(7)); //Soit c'est inc/dec pv, donc l'index est en position 7
        } catch(NumberFormatException nfe){
            indexEtudiant = Integer.parseInt(cmd.substring(8,9)); //Soit c'est pouvoir, donc l'index est 8-9
        }
        int indexPouvoir = 0;
        
        Etudiant currEtudiant = liste.getEtudiant(indexEtudiant);
        
        switch (cmd.substring(0, 6)) {
            case "inc pv":
                currEtudiant.setPv(currEtudiant.getPv()+1);
                break;
            case "dec pv":
                currEtudiant.setPv(currEtudiant.getPv()-1);
                break;
            case "inc ex":
                currEtudiant.setExp(currEtudiant.getExp()+1);
                break;
            case "dec ex":
                currEtudiant.setExp(currEtudiant.getPv()-1);
                break;
            case "pouvoi":
                indexPouvoir = Integer.parseInt(cmd.substring(10,11));
            default:
                break;
        }
	
	liste.setEtudiant(indexEtudiant, currEtudiant);
        repaint();
    }
}
