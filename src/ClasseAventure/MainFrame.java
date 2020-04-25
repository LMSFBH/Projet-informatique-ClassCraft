/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseAventure;

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
    public static final int NOMBRE_ETUDIANT_CLASSEMENT = 10;
    
    static ListeDesEtudiants liste;
    JPanel panneau = new JPanel();
    int nombreEtudiants, nouveauPV, indexEtudiant;
    JFileChooser choix;
    
    JProgressBar[] progressBar;
    static JLabel[] pv, nomEtudiants, teteDeMort;
    static JLabel[] pseudoEtudiant;
    String fichierPrincipale;
    static boolean[][] boutonUtilisable;
    boolean boutonUtilisableSeul;
    JScrollPane miseEnPage;
    JButton[] changerImage;
    static JButton[][] listePouvoirs;
    Etudiant currEtudiant;
    
    Image tete =getToolkit().getImage("teteMort.png");
    Image crane=tete.getScaledInstance(20,20,0);
    ImageIcon cerveau = new ImageIcon(crane);
    
    static Color couleur1 = new Color(255,255,255); // couleur blanche
    static Color couleur2 = new Color(100,100,100); // couleur noir
    static Color couleur3 = new Color(0,255,0); // couleur verte
    static Color couleur4 = new Color(255,0,0); // couleur rouge
    static Color couleur5 = new Color(255,255,0); // couleur jaune
    
    public MainFrame() throws  FileNotFoundException, IOException, Exception{
        choix = new JFileChooser(".");
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choix.setDialogTitle("Selection");

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
        boutonUtilisable = new boolean[nombreEtudiants][ListeDesEtudiants.NBR_POUVOIRS];
        
        //setSize(500, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //setUndecorated(true);
        
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
        
        JButton changement = new JButton("Afficher le classement");
        changement.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameOrdre = new JFrame();
                JPanel panneau = new JPanel();
                
                GridBagLayout gbl = new GridBagLayout();
                panneau.setLayout(gbl);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;

                frameOrdre.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                liste.organisezClassement(false);
                
                for(int i=0;i<NOMBRE_ETUDIANT_CLASSEMENT;i++){
                    constraints.gridy = i;
                    
                    if(i > nombreEtudiants)
                        break;
                    
                    Etudiant currEtudiant = liste.getEtudiant(i);
                    JLabel etudiantLabel = new JLabel("Nom et prenom:"+currEtudiant.getName()+", Numero d'admission: "+currEtudiant.getNAdmission()+
                                                      ", Role: "+currEtudiant.getRole().getRole()+", Pseudo: "+currEtudiant.getPseudo()+", Niveau: "+
                                                      (currEtudiant.getNiveau()+((currEtudiant.getExp() == 1) ? 0.5 : 0))+", Points de vies: "+
                                                      currEtudiant.getPv());
                    panneau.add(etudiantLabel, constraints);
                }
                
                frameOrdre.add(panneau);
                frameOrdre.setVisible(true);
                
                liste.organisezAlphabet();
            }
            
        });
        panneau.add(changement, constraints);
        
        constraints.gridy=1;
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
                    frameClique.setTitle("Informations de l'étudiant");
                    frameClique.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameClique.setVisible(true);
                }
            });
            panneau.add(nomEtudiants[i], constraints);
        }
        
        constraints.gridx++;
        constraints.gridy=1;
        JLabel classe = new JLabel("classe");
        panneau.add(classe, constraints);
        
        JLabel[] role = new JLabel[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            constraints.gridy++;
            role[i] = new JLabel(liste.getEtudiant(i).getRole().getRole());
            panneau.add(role[i],constraints);
        }
        
        constraints.gridx++;
        constraints.gridy=1;
        JLabel pseudo = new JLabel("Pseudo");
        panneau.add(pseudo, constraints);
        
        pseudoEtudiant = new JLabel[nombreEtudiants];
        for(int i=0; i<nombreEtudiants; i++){
            constraints.gridy++;
            pseudoEtudiant[i] = new JLabel(liste.getEtudiant(i).getPseudo());
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
	constraints.gridy=1;
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
        constraints.gridy=1;
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
        constraints.gridy=1;
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
        constraints.gridy=1;
        constraints.gridwidth=6;
        constraints.weightx=1;
        
        JLabel pouvoir = new JLabel("Pouvoirs");
        panneau.add(pouvoir,constraints);
        
        constraints.gridwidth=1;
        int lv = 0; // niveau ou on gagne un pouvoir
        constraints.gridy++;
        
        listePouvoirs = new JButton[nombreEtudiants][ListeDesEtudiants.NBR_POUVOIRS];
        boutonUtilisable = new boolean[nombreEtudiants][ListeDesEtudiants.NBR_POUVOIRS];
        for (int i=0; i<listePouvoirs.length;i++){ 
            for (int j=0; j<ListeDesEtudiants.NBR_POUVOIRS;j++){ 
                lv+=5;
                listePouvoirs[i][j] = new JButton(""+lv);
                listePouvoirs[i][j].setMargin(new Insets(0,0,0,0));
                listePouvoirs[i][j].setPreferredSize(new Dimension(35,20));
                boutonUtilisableSeul = true;
                if(liste.getEtudiant(i).getNiveau()<5 & j==0){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(liste.getEtudiant(i).getNiveau()<10 & j==1){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(liste.getEtudiant(i).getNiveau()<15 & j==2){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(liste.getEtudiant(i).getNiveau()<20 & j==3){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(liste.getEtudiant(i).getNiveau()<25 & j==4){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(liste.getEtudiant(i).getNiveau()<30 & j==5){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(boutonUtilisableSeul){
                    listePouvoirs[i][j].setBackground(Color.green);
                }
                if(liste.getEtudiant(i).getPv()==0){
                    listePouvoirs[i][j].setBackground(Color.red);
                }
                listePouvoirs[i][j].setActionCommand("pouvoir "+i+" "+j);
                listePouvoirs[i][j].addActionListener(new GestAction());
                boutonUtilisable[i][j] = true;    
                panneau.add(listePouvoirs[i][j],constraints);
                constraints.gridx++;
            }
            
            constraints.gridy++;
            constraints.gridx-=ListeDesEtudiants.NBR_POUVOIRS;
            lv=0;
        }
        
        constraints.gridx=7+ListeDesEtudiants.NBR_POUVOIRS;
        constraints.gridy=2;
        changerImage = new JButton[nombreEtudiants];
        
        for(int i=0;i<nombreEtudiants;i++){
            changerImage[i] = new JButton("Image");
            Etudiant currEtudiant = liste.getEtudiant(i);
            
            changerImage[i].setActionCommand("image "+i);
            changerImage[i].addActionListener(new GestAction());
            
            panneau.add(changerImage[i], constraints);
            constraints.gridy++;
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
            int indexEtudiant = 0;
            String[] cmds = cmd.split(" ");
            int indexPouvoir = 0;

            if(cmd.startsWith("pv inc") || cmd.startsWith("pv dec") || cmd.startsWith("exp inc") || cmd.startsWith("exp dec"))
                indexEtudiant = Integer.parseInt(cmds[2]); //Soit c'est pv/exp inc/dec, donc l'index est apres le 2e espace
            else{
                if(cmd.startsWith("image")){
                   indexEtudiant = Integer.parseInt(cmds[1]); 
                }
                else{
                    indexEtudiant = Integer.parseInt(cmds[1]); //Soit c'est pouvoir, donc l'index est apres le 1e espace
                    indexPouvoir = Integer.parseInt(cmds[2]);
                }
            }

            Etudiant currEtudiant = liste.getEtudiant(indexEtudiant);

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
                        verificationPouvoir(indexEtudiant);
                        break;
                    case "exp":
                        if(liste.getEtudiant(indexEtudiant).getPv()>0){
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
                        }else
                            JOptionPane.showMessageDialog(null, "l'etudiant est mort! il ne peux plus gagner d'exp");
                        
                        progressBar[indexEtudiant].setString("Niv "+(liste.getEtudiant(indexEtudiant).getNiveau()+((liste.getEtudiant(indexEtudiant).getExp() == 1) ? 0.5 : 0))+"                            ");                       
                        verificationPouvoir(indexEtudiant);
                        break;
                    case "pouvoir":
                        FramePouvoir descriptionPouvoir = new FramePouvoir(currEtudiant, liste, indexPouvoir);
                        descriptionPouvoir.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        descriptionPouvoir.setVisible(true);
                        break;
                    case "image":
                        choix = new JFileChooser(".");
                        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        choix.setDialogTitle("Selectionnez l'image de l'etudiant "+currEtudiant.getName());

                        int resultat = JFileChooser.CANCEL_OPTION;

                        while(resultat != JFileChooser.APPROVE_OPTION){
                            resultat = choix.showOpenDialog(null);

                            switch (resultat) {
                                case JFileChooser.ERROR_OPTION:
                                    JOptionPane.showMessageDialog(null, "Une erreur est survenu lors du choix de fichier.");

                                    if(!ouiOuNon("Voulez-vous recommencez?", "FERMETURE"))
                                        return;
                                    break;
                                case JFileChooser.CANCEL_OPTION:
                                    return;
                                default:
                                    try{
                                        currEtudiant.setCheminImage(choix.getSelectedFile().getCanonicalPath());
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
                        break;
                    default:
                        break;
                }
            setCouleurPouvoirs(indexEtudiant);
            } catch(IllegalArgumentException iae){
                JOptionPane.showMessageDialog(null, iae.getMessage());
            }

            liste.setEtudiant(indexEtudiant, currEtudiant);

            revalidate();
            repaint();
        }
    }
    
    public void verificationPouvoir(int indexEtudiant){
        for (int j=0; j<ListeDesEtudiants.NBR_POUVOIRS;j++){ 
            //rajouter bouton utilise pour les pouvoirs comme etant attribut de Etudiant
            boutonUtilisableSeul = true;
            if(liste.getEtudiant(indexEtudiant).getNiveau()<5 & j==0){
                listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                boutonUtilisableSeul=false;
            }

            if(liste.getEtudiant(indexEtudiant).getNiveau()<10 & j==1){
                listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                boutonUtilisableSeul=false;
            }
            
            if(liste.getEtudiant(indexEtudiant).getNiveau()<15 & j==2){
                listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                boutonUtilisableSeul=false;
            }

            if(liste.getEtudiant(indexEtudiant).getNiveau()<20 & j==3){
                listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                boutonUtilisableSeul=false;
            }

            if(liste.getEtudiant(indexEtudiant).getNiveau()<25 & j==4){
                listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                boutonUtilisableSeul=false;
            }

            if(liste.getEtudiant(indexEtudiant).getNiveau()<30 & j==5){
                listePouvoirs[indexEtudiant][j].setBackground(new Color(96,96,96));
                listePouvoirs[indexEtudiant][j].setForeground(new Color(255,255,255));
                boutonUtilisableSeul=false;
            }

            if(boutonUtilisableSeul){
                listePouvoirs[indexEtudiant][j].setBackground(Color.green);
                listePouvoirs[indexEtudiant][j].setForeground(Color.black);
            }
                                
            if(liste.getEtudiant(indexEtudiant).getPv()==0){
                listePouvoirs[indexEtudiant][j].setBackground(Color.red);
                listePouvoirs[indexEtudiant][j].setForeground(Color.white);
            }
        }
    }
    
    protected static void setCouleurPouvoirs(int indEtudiant){
        int indPouvoir;
        indPouvoir = 0;
        if(liste.getEtudiant(indEtudiant).getPv()== 0){ // bouton inutilisable couleur
            for(int j=0; j<6; j++){
                listePouvoirs[indEtudiant][j].setBackground(couleur4); 
                listePouvoirs[indEtudiant][j].setForeground(couleur1);
            }
        }else{      // couleur pour un bouton normal  
            for(int j=0; j<6; j++){
                listePouvoirs[indEtudiant][j].setBackground(couleur2); 
                listePouvoirs[indEtudiant][j].setForeground(couleur1);
            } 
            
            
                

            if(liste.getEtudiant(indEtudiant).getNiveau()>=5){ //couleur pour un pouvoir actif
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
            if(boutonUtilisable[indEtudiant][indPouvoir]== false){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
            }
                indPouvoir++;        
            }            
                if(liste.getEtudiant(indEtudiant).getNiveau()>=10){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                //Moi: j'ai fait des copier coller pour bouton utlisable. j'ai l'impression qu'on peut le faire une fois pour tout mais ... la prochaine fois peu etre
                if(boutonUtilisable[indEtudiant][indPouvoir]== false){
                    listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                    listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                }
                indPouvoir++;
            } if(liste.getEtudiant(indEtudiant).getNiveau()>=15){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                if(boutonUtilisable[indEtudiant][indPouvoir]== false){
                    listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                    listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                }
                indPouvoir++;
            } if(liste.getEtudiant(indEtudiant).getNiveau()>=20){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                if(boutonUtilisable[indEtudiant][indPouvoir]== false){
                   listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                   listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                }
                indPouvoir++;
            } if(liste.getEtudiant(indEtudiant).getNiveau()>=25){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                if(boutonUtilisable[indEtudiant][indPouvoir]== false){
                    listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                    listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                }
                indPouvoir++;
            } if(liste.getEtudiant(indEtudiant).getNiveau()>=30){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                if(boutonUtilisable[indEtudiant][indPouvoir]== false){
                    listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                    listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                }
            }
        }
    }
}

