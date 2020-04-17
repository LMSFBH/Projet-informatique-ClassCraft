/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author usager
 */
class MainFrame extends JFrame{ 
    
    ListeDesEtudiants liste;
    JPanel panneau = new JPanel();
    int nombreEtudiants, nouveauPV;
    JFileChooser choix;
    
    JProgressBar[] progressBar;
    static JLabel[] pv, nomEtudiants, teteDeMort;
    static JTextField[] pseudoEtudiant;
    String fichierPrincipale;
    boolean boutonUtilisable;
    JScrollPane miseEnPage;
    JButton[][] listePouvoirs;
    Etudiant currEtudiant;
    
    Image tete =getToolkit().getImage("teteMort.png");
    Image crane=tete.getScaledInstance(20,20,0);
    ImageIcon cerveau = new ImageIcon(crane);
    
    public MainFrame() throws  FileNotFoundException, IOException, Exception{
        choix = new JFileChooser(".");
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choix.setDialogTitle("Sauvegarde");

        int resultat = JFileChooser.CANCEL_OPTION;

        while(resultat != JFileChooser.APPROVE_OPTION){
            resultat = choix.showOpenDialog(this);

            switch (resultat) {
                case JFileChooser.ERROR_OPTION:
                    JOptionPane.showMessageDialog(null, "Une erreur est survenu lors du choix de fichier.");

                    if(!ouiOuNon("Voulez-vous recommencez?", "FERMETURE"))
                        System.exit(0);
                    break;
                case JFileChooser.CANCEL_OPTION:
                    System.exit(0);
                default:
                    try{
                        liste = new ListeDesEtudiants(choix.getSelectedFile());
                        fichierPrincipale = choix.getSelectedFile().getCanonicalPath();
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
                    break;
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
        
        nomEtudiants = new JLabel[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            Etudiant currEtudiant = liste.getEtudiant(i);
            
            constraints.gridy++;
            nomEtudiants[i] = new JLabel(currEtudiant.getName());
            nomEtudiants[i].addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    FrameEtudiant frameClique = new FrameEtudiant(currEtudiant, liste, fichierPrincipale);
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
        
        pseudoEtudiant = new JTextField[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            constraints.gridy++;
            pseudoEtudiant[i] = new JTextField(liste.getEtudiant(i).getPseudo());
            //Trouvez un moyen de faire l'action (probablement simple, me disait seulement si on pourrait utiliser autre chose qu'un keylistener)
            pseudoEtudiant[i].addActionListener(new GestAction());
            pseudoEtudiant[i].setActionCommand("pseudo "+i);
            panneau.add(pseudoEtudiant[i], constraints);
        }
        
	JButton [] Bplus = new JButton[nombreEtudiants];
	JButton [] Bmoins = new JButton[nombreEtudiants];
        pv = new JLabel[nombreEtudiants];
        teteDeMort = new JLabel[nombreEtudiants];
        
	for (int i=0; i<nombreEtudiants;i++){
            Bplus[i] = new JButton("+");
            Bplus[i].setMargin(new Insets(0,0,0,0));
            Bplus[i].setPreferredSize(new Dimension(20,20));
            Bplus[i].setActionCommand("pv inc "+i);
            Bplus[i].addActionListener(new GestAction());
            
            Bmoins[i] = new JButton("-");
            Bmoins[i].setMargin(new Insets(0,0,0,0));
            Bmoins[i].setPreferredSize(new Dimension(20,20));
            Bmoins[i].setActionCommand("pv dec "+i);
            Bmoins[i].addActionListener(new GestAction());
            
            pv[i] = new JLabel(""+liste.getEtudiant(i).getPv());
            teteDeMort[i] = new JLabel(cerveau);
        }
        
	constraints.gridx=4;// Moi: j'ai mis 4 parce qu'il y a classe, liste et avatar avant pv et les bouttons
	constraints.gridy=0;
        JLabel textePV = new JLabel("Points de Vie");
	panneau.add(textePV,constraints);
	
	for(int i=0; i<nombreEtudiants;i++){
            constraints.gridy++;
            panneau.add(pv[i],constraints);
            panneau.add(teteDeMort[i],constraints);
            if(Integer.parseInt(pv[i].getText())==0){
                teteDeMort[i].setVisible(true);
                pv[i].setVisible(false);
            }else{
                teteDeMort[i].setVisible(false);
                pv[i].setVisible(true);
            }
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
        progressBar = new JProgressBar[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            progressBar[i] = new JProgressBar();
            progressBar[i].setMaximum(ExpMax);
            progressBar[i].setValue(0);
            progressBar[i].setStringPainted(true);
            
            constraints.gridy++;
            progressBar[i].setString("Niv "+(liste.getEtudiant(i).getNiveau()+((liste.getEtudiant(i).getExp() == 1) ? 0.5 : 0))+"                            ");
            progressBar[i].setValue(liste.getEtudiant(i).getExp());	
        }
        
        JButton[] BplusExp = new JButton[nombreEtudiants];
	JButton[] BmoinsExp = new JButton[nombreEtudiants];
	for (int i=0; i<nombreEtudiants;i++){
            BplusExp[i] = new JButton("+");
            BplusExp[i].setMargin(new Insets(0,0,0,0));
            BplusExp[i].setPreferredSize(new Dimension(20,20));
            BplusExp[i].setActionCommand("exp inc "+i);
            BplusExp[i].addActionListener(new GestAction());
            
            BmoinsExp[i] = new JButton("-");
            BmoinsExp[i].setMargin(new Insets(0,0,0,0));
            BmoinsExp[i].setPreferredSize(new Dimension(20,20));
            BmoinsExp[i].setActionCommand("exp dec "+i);
            BmoinsExp[i].addActionListener(new GestAction());
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
        
        listePouvoirs = new JButton[nombreEtudiants][ListeDesEtudiants.NBR_POUVOIRS];
        for (int i=0; i<listePouvoirs.length;i++){ 
            for (int j=0; j<ListeDesEtudiants.NBR_POUVOIRS;j++){ 
                lv+=5;
                listePouvoirs[i][j] = new JButton(""+lv);
                listePouvoirs[i][j].setMargin(new Insets(0,0,0,0));
                listePouvoirs[i][j].setPreferredSize(new Dimension(35,20));
                boutonUtilisable = true;
                if(liste.getEtudiant(i).getNiveau()<5 & j==0){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisable=false;
                }
                if(liste.getEtudiant(i).getNiveau()<10 & j==1){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisable=false;
                }
                if(liste.getEtudiant(i).getNiveau()<15 & j==2){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisable=false;
                }
                if(liste.getEtudiant(i).getNiveau()<20 & j==3){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisable=false;
                }
                if(liste.getEtudiant(i).getNiveau()<25 & j==4){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisable=false;
                }
                if(liste.getEtudiant(i).getNiveau()<30 & j==5){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisable=false;
                }
                if(boutonUtilisable){
                    listePouvoirs[i][j].setBackground(Color.green);
                }
                if(liste.getEtudiant(i).getPv()==0){
                    listePouvoirs[i][j].setBackground(Color.red);
                }
                listePouvoirs[i][j].setActionCommand("pouvoir "+i+" "+j);
                listePouvoirs[i][j].addActionListener(new GestAction());
                panneau.add(listePouvoirs[i][j],constraints);
                constraints.gridx++;
            }
            
            constraints.gridy++;
            constraints.gridx-=ListeDesEtudiants.NBR_POUVOIRS;
            lv=0;
        }
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                if(ouiOuNon("Etes-vous sure de vouloir fermer l'application?", "FERMETURE")){
                    String fichierImg;
                    int resultat = JFileChooser.CANCEL_OPTION;
                    boolean utiliserFichierDemarrage;
                    
                    while(resultat != JFileChooser.APPROVE_OPTION){
                        utiliserFichierDemarrage = ouiOuNon("Voulez-vous utilisez le meme fichier selectionnez au lancement du programme?", "FERMETURE");
                        
                        try{
                            JFileChooser choix = null;
                            if(!utiliserFichierDemarrage){
                                choix = new JFileChooser(".");
                                
                                /*FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Excel Workbook (.xlsx)", ".xlsx");
                                choix.addChoosableFileFilter(extensionFilter);
                                choix.setAcceptAllFileFilterUsed(false);
                                choix.setFileFilter(extensionFilter);*/
                                choix.setDialogTitle("Sauvegarde");
                                
                                resultat = choix.showSaveDialog(null);
                            } else
                                resultat = JFileChooser.APPROVE_OPTION;
                            
                            switch(resultat){
                                case JFileChooser.ERROR_OPTION:
                                    JOptionPane.showMessageDialog(null, "Une erreur est survenu lors du choix de fichier.");

                                    if(!ouiOuNon("Voulez-vous recommencez?", "FERMETURE"))
                                        System.exit(0);
                                    break;
                                case JFileChooser.CANCEL_OPTION:
                                    return;
                                case JFileChooser.APPROVE_OPTION:
                                    //L'ecriture des images prends beaucoups de temps, trouver un moyen de ne pas les ecrire chaques fois
                                    //if(liste.isModif()){
                                    fichierImg = utiliserFichierDemarrage ? fichierPrincipale : choix.getSelectedFile().getCanonicalPath();
                                    if(!fichierImg.endsWith(".xlsx"))
                                        fichierImg += ".xlsx";
                                    
                                    if((new File(fichierImg)).exists())
                                        if(!ouiOuNon("Le fichier existe deja. Voulez-vous le re-ecrire? (le programme vous redemandera si vous selectionnez non)", "FERMETURE")){
                                            resultat = JFileChooser.CANCEL_OPTION;
                                            continue;
                                        }
                                    
                                    liste.writeToutEtudiantsEtImages(fichierImg, true);
                                    System.exit(0);
                                    //else
                                    //  liste.writeToutEtudiantsEtImages(JOptionPane.showInputDialog("Veuillez selectionnez l'emplacement du fichier excel de sauvegarde."), false);
                            }

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
        
        miseEnPage = new JScrollPane(panneau);
        
        add(miseEnPage);
    }
    
    public static boolean ouiOuNon(String msg, String titre){
        String[] options = {"Oui", "Non"};
        return (JOptionPane.showOptionDialog(null, msg, titre, JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]) == 0);
    }
    
    private class GestAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            int indexEtudiant;
            String[] cmds = cmd.split(" ");
            int indexPouvoir = 0;

            if(cmd.startsWith("pv inc") || cmd.startsWith("pv dec") || cmd.startsWith("exp inc") || cmd.startsWith("exp dec"))
                indexEtudiant = Integer.parseInt(cmds[2]); //Soit c'est pv/exp inc/dec, donc l'index est apres le 2e espace
            else if(cmd.startsWith("pseudo"))
                indexEtudiant = Integer.parseInt(cmds[1]);
            else{
                indexEtudiant = Integer.parseInt(cmds[1]); //Soit c'est pouvoir, donc l'index est apres le 1e espace
                indexPouvoir = Integer.parseInt(cmds[2]);
            }

            currEtudiant = liste.getEtudiant(indexEtudiant);

            try{
                switch (cmds[0]) {
                    case "pv":
                        //Faire des verifications quant au max des PVs
                        if(cmds[1].equals("inc")){
                            nouveauPV = Integer.parseInt(pv[indexEtudiant].getText())+1;
                            currEtudiant.setPv(currEtudiant.getPv()+1);
                        }
                        else{
                            nouveauPV = Integer.parseInt(pv[indexEtudiant].getText())-1;
                            currEtudiant.setPv(currEtudiant.getPv()-1);
                            if(nouveauPV<0){
                                nouveauPV =0;
                                currEtudiant.setPv(0);
                            }
                        }
                        pv[indexEtudiant].setText(""+nouveauPV);
                        if(Integer.parseInt(pv[indexEtudiant].getText())==0){                                                
                            teteDeMort[indexEtudiant].setVisible(true);
                            pv[indexEtudiant].setVisible(false);
                        }else{
                            teteDeMort[indexEtudiant].setVisible(false);
                            pv[indexEtudiant].setVisible(true);
                        }
                        break;
                    case "exp":
                        if(cmds[1].equals("inc")){
                            if(currEtudiant.getExp()+1 == 2){
                                progressBar[indexEtudiant].setValue(0);
                                currEtudiant.setExp(0);
                                currEtudiant.setNiveau(currEtudiant.getNiveau()+1);
                            }
                            else {
                                progressBar[indexEtudiant].setValue(1);
                                currEtudiant.setExp(currEtudiant.getExp()+1);
                            }
                        }
                        //Probleme: que fait on si le prof veut baisser le niveau de l'eleve (je me disais qu'on fairait une fenetre en edition de toute les infos d'un etudiant)
                        else{ 
                            if(currEtudiant.getExp()-1 == -1){
                                currEtudiant.setNiveau(currEtudiant.getNiveau()-1);
                                if(currEtudiant.getNiveau()==-1){
                                    progressBar[indexEtudiant].setValue(0);
                                    currEtudiant.setExp(0);
                                }else{
                                   progressBar[indexEtudiant].setValue(1);
                                   currEtudiant.setExp(1); 
                                }
                            }
                            else{
                                progressBar[indexEtudiant].setValue(0);
                                currEtudiant.setExp(currEtudiant.getExp()-1);
                            }
                        }

                        progressBar[indexEtudiant].setString("Niv "+(liste.getEtudiant(indexEtudiant).getNiveau()+((liste.getEtudiant(indexEtudiant).getExp() == 1) ? 0.5 : 0))+"                            ");
                        for (int j=0; j<ListeDesEtudiants.NBR_POUVOIRS;j++){ 
                                boutonUtilisable = true;
                                if(liste.getEtudiant(indexEtudiant).getNiveau()<5 & j==0){
                                    listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                                    listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                                    boutonUtilisable=false;
                                }

                                if(liste.getEtudiant(indexEtudiant).getNiveau()<10 & j==1){
                                    listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                                    listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                                    boutonUtilisable=false;
                                }

                                if(liste.getEtudiant(indexEtudiant).getNiveau()<15 & j==2){
                                    listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                                    listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                                    boutonUtilisable=false;
                                }

                                if(liste.getEtudiant(indexEtudiant).getNiveau()<20 & j==3){
                                    listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                                    listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                                    boutonUtilisable=false;
                                }

                                if(liste.getEtudiant(indexEtudiant).getNiveau()<25 & j==4){
                                    listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                                    listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                                    boutonUtilisable=false;
                                }

                                if(liste.getEtudiant(indexEtudiant).getNiveau()<30 & j==5){
                                    listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                                    listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                                    boutonUtilisable=false;
                                }

                                if(boutonUtilisable){
                                    listePouvoirs[indexEtudiant][j].setBackground(Color.green);
                                    listePouvoirs[indexEtudiant][j].setForeground(Color.black);
                                }
                                
                                if(liste.getEtudiant(indexEtudiant).getPv()==0){
                                    listePouvoirs[indexEtudiant][j].setBackground(Color.red);
                                    listePouvoirs[indexEtudiant][j].setForeground(Color.white);
                                }
                            }
                        break;
                    case "pouvoir":

                        break;
                    default:
                        break;
                }
            
            } catch(IllegalArgumentException iae){
                JOptionPane.showMessageDialog(null, iae.getMessage());
            }

            liste.setEtudiant(indexEtudiant, currEtudiant);

            revalidate();
            repaint();
        }
    }
}