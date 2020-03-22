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
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author usager
 */
class MainFrame extends JFrame implements ActionListener { 
    
    ListeDesEtudiants liste;
    JPanel panneau = new JPanel();
    int nombreEtudiants;
    JFileChooser choix;
    
    public MainFrame() throws  FileNotFoundException, IOException, Exception{
        choix = new JFileChooser();
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int resultat = JFileChooser.CANCEL_OPTION;
        
        while(resultat != JFileChooser.APPROVE_OPTION){
            resultat = choix.showOpenDialog(this);
            
            if(resultat == JFileChooser.CANCEL_OPTION)
                JOptionPane.showMessageDialog(null, "Veuillez choisir un fichier");
            if(resultat == JFileChooser.ERROR_OPTION)
                JOptionPane.showMessageDialog(null, "Une erreur est survenu lors du choix de fichier.");
            
            if(resultat != JFileChooser.APPROVE_OPTION){
                String[] options = {"Oui", "Non"};
                if(JOptionPane.showOptionDialog(null, "Voulez-vous recommencez?", "FERMETURE", JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.INFORMATION_MESSAGE, null, options, options[0]) == 1)
                    System.exit(0);
            }
            else{
                try{
                    liste = new ListeDesEtudiants(choix.getSelectedFile());
                } catch(FileNotFoundException fnfe){
                    JOptionPane.showMessageDialog(null, fnfe.getMessage());
                    resultat = JFileChooser.CANCEL_OPTION;
                } catch(IOException ioe){
                    JOptionPane.showMessageDialog(null, ioe.getMessage());
                    resultat = JFileChooser.CANCEL_OPTION;
                } catch(Exception e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    resultat = JFileChooser.CANCEL_OPTION;
                }
            }
            
        }
        
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
            Etudiant currEtudiant = liste.getEtudiant(i);
            
            constraints.gridy++;
            nomEtudiants[i] = new JLabel(currEtudiant.getName());
            nomEtudiants[i].addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    FrameEtudiant frameClique = new FrameEtudiant(currEtudiant); // Comment recuperer l'etudiant???
                    frameClique.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameClique.setVisible(true);
                }
            });
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
	JButton [] Bmoins = new JButton[nombreEtudiants];
	for (int i=0; i<nombreEtudiants;i++){
            Bplus[i] = new JButton("+");
            Bplus[i].setMargin(new Insets(0,0,0,0));
            Bplus[i].setPreferredSize(new Dimension(20,20));
            Bplus[i].setActionCommand("inc pv "+i);
            Bplus[i].addActionListener(this);
            
            Bmoins[i] = new JButton("-");
            Bmoins[i].setMargin(new Insets(0,0,0,0));
            Bmoins[i].setPreferredSize(new Dimension(20,20));
            Bmoins[i].setActionCommand("dec pv "+i);
            Bmoins[i].addActionListener(this);
        }
        
	constraints.gridx=4;// Moi: j'ai mis 4 parce qu'il y a classe, liste et avatar avant pv et les bouttons
	constraints.gridy=0;
        JLabel pv = new JLabel("Points de Vie");
	panneau.add(pv,constraints);
	
	for(int i=0; i<nombreEtudiants;i++){
            constraints.gridy++;
            panneau.add(new JLabel(""+liste.getEtudiant(i).getPv()),constraints);
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
        JProgressBar[] progressBar = new JProgressBar[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            progressBar[i] = new JProgressBar();
            progressBar[i].setMaximum(ExpMax);
            progressBar[i].setValue(0);
            progressBar[i].setStringPainted(true);
            
            constraints.gridy++;
            progressBar[i].setString("LV"+(liste.getEtudiant(i).getNiveau()+((liste.getEtudiant(i).getExp() == 1) ? 0.5 : 0))+"                            ");
            progressBar[i].setValue(liste.getEtudiant(i).getExp());	
        }
        
        JButton[] BplusExp = new JButton[nombreEtudiants];
	JButton[] BmoinsExp = new JButton[nombreEtudiants];
	for (int i=0; i<nombreEtudiants;i++){
            BplusExp[i] = new JButton("+");
            BplusExp[i].setMargin(new Insets(0,0,0,0));
            BplusExp[i].setPreferredSize(new Dimension(20,20));
            BplusExp[i].setActionCommand("inc ex "+i);
            BplusExp[i].addActionListener(this);
            
            BmoinsExp[i] = new JButton("-");
            BmoinsExp[i].setMargin(new Insets(0,0,0,0));
            BmoinsExp[i].setPreferredSize(new Dimension(20,20));
            BmoinsExp[i].setActionCommand("dec ex "+i);
            BmoinsExp[i].addActionListener(this);
	}
        
        constraints.gridx=5;
        constraints.gridy=0;
        for(int i=0; i<nombreEtudiants;i++){
            constraints.gridy++;
            panneau.add(progressBar[i],constraints);
            constraints.anchor=GridBagConstraints.EAST;
            panneau.add(BplusExp[i],constraints);
            constraints.anchor=GridBagConstraints.WEST;
            panneau.add(BmoinsExp[i],constraints);
            constraints.anchor=GridBagConstraints.CENTER;
	}
        
        constraints.gridx=6;
        constraints.gridy=0;
        constraints.gridwidth=6;
        constraints.weightx=1;
        
        JLabel pouvoir = new JLabel("Pouvoirs");
        panneau.add(pouvoir,constraints);
        
        constraints.gridwidth=1;
        int lv = 0; // niveau ou on gagne un pouvoir
        constraints.gridy++;
        JButton ListePouvoirs[][] = new JButton[nombreEtudiants][ListeDesEtudiants.NBR_POUVOIRS];
        for (int i=0; i<ListePouvoirs.length;i++){ 
            for (int j=0; j<ListeDesEtudiants.NBR_POUVOIRS;j++){ 
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
            constraints.gridx-=ListeDesEtudiants.NBR_POUVOIRS;
            lv=0;
        }
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                String[] options = {"Oui", "Non"};
                if(JOptionPane.showOptionDialog(null, "Etes-vous sure de vouloir fermer l'application?", "FERMETURE", JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.INFORMATION_MESSAGE, null, options, options[0]) == 0){
                    String fichierImg = "";

                    while((fichierImg == null) || (fichierImg.isEmpty())){
                        try{
                        //L'ecriture des images prends beaucoups de temps

                            fichierImg = JOptionPane.showInputDialog("Veuillez selectionnez l'emplacement du fichier excel de sauvegarde. \n(L'operation pourrait prendre plus de temps si les images sont a ecrire)");
                            if((fichierImg == null) || (fichierImg.isEmpty())){
                                if(fichierImg == null)
                                    if(JOptionPane.showOptionDialog(null, "Vous avez changez d'idee?", "FERMETURE", JOptionPane.YES_NO_OPTION,
                                                                    JOptionPane.INFORMATION_MESSAGE, null, options, options[0]) == 0)
                                        return;
                                
                                JOptionPane.showMessageDialog(null, "Veuillez entrez un non de fichier non-vide.");
                                continue;
                            }

                            if(!fichierImg.endsWith(".xlsx"))
                                fichierImg += ".xlsx";   

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
        
        try{
            switch (cmd.substring(0, 6)) {
                case "inc pv":
                    //Faire des verifications quant au max des PVs
                    currEtudiant.setPv(currEtudiant.getPv()+1);
                    break;
                case "dec pv":
                    currEtudiant.setPv(currEtudiant.getPv()-1);
                    break;
                case "inc ex":
                    if(currEtudiant.getExp()+1 == 2){
                        currEtudiant.setExp(0);
                        currEtudiant.setNiveau(currEtudiant.getNiveau()+1);
                    }
                    else currEtudiant.setExp(currEtudiant.getExp()+1);
                    break;
                case "dec ex":
                    currEtudiant.setExp(currEtudiant.getPv()-1);
                    break;
                case "pouvoi":
                    indexPouvoir = Integer.parseInt(cmd.substring(10,11));
                default:
                    break;
            }
        } catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(null, iae.getMessage());
        }
	
	liste.setEtudiant(indexEtudiant, currEtudiant);
        repaint();
    }
}