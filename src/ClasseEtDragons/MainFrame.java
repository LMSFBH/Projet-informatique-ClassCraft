/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseEtDragons;

import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author usager
 */
class MainFrame extends JFrame{ 
    public final int NOMBRE_ETUDIANT_CLASSEMENT = 10;
    
    JButton aide = new JButton("Aide");
    Aide lAide = new Aide();
    JButton aPropos  = new JButton("?");
    Aide lAPropos = new Aide("À propos du Programme");
    
    ListeDesEtudiants liste;
    JPanel panneau = new JPanel();
    int nombreEtudiants, indexEtudiant;
    JFileChooser choix;
    FrameEtudiant frameClique;
    
    JProgressBar[] progressBar;
    static JLabel[] pv, nomEtudiants, teteDeMort, role, pseudoEtudiant;
    static String fichierPrincipale;
    static boolean[][] boutonUtilisable;
    boolean boutonUtilisableSeul;
    JScrollPane miseEnPage;
    JButton[] changerImage;
    static JButton[][] listePouvoirs;
    
    Image tete =getToolkit().getImage("teteMort.png");
    Image crane=tete.getScaledInstance(20,20,0);
    ImageIcon cerveau = new ImageIcon(crane);
    
    static Color couleur1 = new Color(255,255,255); // couleur blanche
    static Color couleur2 = new Color(100,100,100); // couleur grise
    static Color couleur3 = new Color(0,255,0); // couleur verte
    static Color couleur4 = new Color(255,0,0); // couleur rouge
    static Color couleur5 = new Color(255,255,0); // couleur jaune
    Color couleur6 = new Color(0,0,255); // couleur Bleu
    Color couleur7 = new Color(100,100,0); // couleur jaune pale
    
    public MainFrame(String fichierXlsx){
        Image icone = Toolkit.getDefaultToolkit().getImage("image/dragon.jpg");
        setIconImage(icone);
        
        LookAndFeel lf = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors du paramétrage du look and feel.");
        }
        choix = new JFileChooser(new File(System.getProperty("user.home"), "desktop"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("excel", "xlsx");
        
        if(fichierXlsx == null){
            JOptionPane.showMessageDialog(null,"Veuillez sélectionner votre classe d'étudiant (ici Classeur1)");
            
            choix.setFileFilter(filter);
            choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
            choix.setDialogTitle("Sélection de votre classe");
            
            int resultat = JFileChooser.CANCEL_OPTION;

            while(resultat != JFileChooser.APPROVE_OPTION){
                resultat = choix.showOpenDialog(this);

                switch (resultat) {
                    case JFileChooser.ERROR_OPTION:
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du choix de fichier.");

                        if(!ouiOuNon("Voulez-vous recommencer ?", "FERMETURE"))
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
        }
        else{
            try{
                liste = new ListeDesEtudiants(fichierXlsx);
                fichierPrincipale = choix.getSelectedFile().getCanonicalPath();
            } catch(FileNotFoundException fnfe){
                JOptionPane.showMessageDialog(null, fnfe.getMessage());
            } catch(IOException ioe){
                JOptionPane.showMessageDialog(null, ioe.getMessage());
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            fichierPrincipale = fichierXlsx;
        }
           
        
        try {
            UIManager.setLookAndFeel(lf);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors du paramétrage du look and feel.");
        }
        nombreEtudiants = liste.getEtudiantsSize();
        
        //setSize(500, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("ClasseAventure");
        setBackground(couleur6);
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
                
                frameOrdre.setSize(900,500);
                frameOrdre.setTitle("Ordre chronologique par niveau");
                
                GridBagLayout gbl = new GridBagLayout();
                panneau.setLayout(gbl);
                GridBagConstraints constraints = new GridBagConstraints();
                

                frameOrdre.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                liste.organisezClassement(false);
                
                constraints.weightx = 1;
                constraints.weighty = 1;
                
                for(int i=0;i<NOMBRE_ETUDIANT_CLASSEMENT;i++){
                    
                    if(i > nombreEtudiants)
                        break;
                    
                    Etudiant currEtudiant = liste.getEtudiant(i);

                    constraints.gridy = i;
                    constraints.gridx=0;
                    JLabel etudiantNom = new JLabel("Nom et prénom: "+currEtudiant.getName());
                    JLabel etudiantDA = new JLabel(" Numéro d'admission: "+currEtudiant.getNAdmission());
                    JLabel etudiantRole = new JLabel("Rôle: "+currEtudiant.getRole().getNomRole());
                    JLabel etudiantPseudo= new JLabel("Pseudo: "+currEtudiant.getPseudo());
                    JLabel etudiantNiveau = new JLabel("Niveau: "+currEtudiant.getName());
                    JLabel etudiantPv = new JLabel("Points de vies: "+currEtudiant.getPv());

                    panneau.add(etudiantNom, constraints);
                    constraints.gridx++;
                    panneau.add(etudiantDA, constraints);
                    constraints.gridx++;
                    panneau.add(etudiantRole, constraints);
                    constraints.gridx++;
                    panneau.add(etudiantPseudo, constraints);
                    constraints.gridx++;
                    panneau.add(etudiantNiveau, constraints);
                    constraints.gridx++;
                    panneau.add(etudiantPv, constraints);
                    
                }
                
  
                
                frameOrdre.add(panneau);
                frameOrdre.setVisible(true);
                frameOrdre.setLocationRelativeTo(null);
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
                    frameClique = new FrameEtudiant(currEtudiant, liste, fichierPrincipale);
                    frameClique.setTitle("Informations de l'étudiant");
                    frameClique.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameClique.setVisible(true);
                    frameClique.setLocationRelativeTo(null);
                }
            });
            panneau.add(nomEtudiants[i], constraints);
        }
        
        constraints.gridx++;
        constraints.gridy=1;
        JLabel classe = new JLabel("classe");
        classe.setToolTipText("Classe des etudiants\nCliquez sur une classe pour changer son nom");
        classe.setForeground(couleur6);
        panneau.add(classe, constraints);
        
        role = new JLabel[nombreEtudiants];
        for(int i=0; i<role.length; i++){
            constraints.gridy++;
            FrameNomRole rl = new FrameNomRole(liste, i);
            rl.setVisible(false);     
            
            role[i] = new JLabel(liste.getEtudiant(i).getRole().getNomRole());
            role[i].addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    rl.setVisible(true);                 
                }});
            
            panneau.add(role[i],constraints);
        }
        
        constraints.gridx++;
        constraints.gridy=1;
        JLabel pseudo = new JLabel("Pseudo");
        pseudo.setToolTipText("Pseudonyme des étudiants\nCliquez le nom d'un étudiant pour changer son Pseudo");
        pseudo.setForeground(couleur6);
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
            
            pv[i] = new JLabel(liste.getEtudiant(i).getPv()+"/"+liste.getEtudiant(i).getRole().getMaxPv());
            teteDeMort[i] = new JLabel(cerveau);
        }
        
	constraints.gridx=4;
	constraints.gridy=1;
        JLabel textePV = new JLabel("Points de Vie");
        textePV.setToolTipText("liste des point de vie des étudiants");
        textePV.setForeground(couleur6);
	panneau.add(textePV,constraints);
	
	for(int i=0; i<nombreEtudiants;i++){
            constraints.gridy++;
            panneau.add(pv[i],constraints);
            panneau.add(teteDeMort[i],constraints);
            if(liste.getEtudiant(i).getPv()==0){
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
        exp.setToolTipText("Points d'experience des etudiants");
        exp.setForeground(couleur6);
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
        pouvoir.setToolTipText("Pouvoir des étudiants\n Il faut monter de niveau pour en débloquer");
        pouvoir.setForeground(couleur6);
        panneau.add(pouvoir,constraints);
        
        constraints.gridwidth=1;
        int lv = 0; // niveau ou on gagne un pouvoir
        constraints.gridy++;
        
        listePouvoirs = new JButton[nombreEtudiants][ListeDesEtudiants.NBR_POUVOIRS];
        boutonUtilisable = new boolean[nombreEtudiants][ListeDesEtudiants.NBR_POUVOIRS];
        for (int i=0; i<listePouvoirs.length;i++){ 
            for (int j=0; j<ListeDesEtudiants.NBR_POUVOIRS;j++){ 
                Etudiant currEtudiant = liste.getEtudiant(i);
                
                lv+=5;
                listePouvoirs[i][j] = new JButton(""+lv);
                listePouvoirs[i][j].setMargin(new Insets(0,0,0,0));
                listePouvoirs[i][j].setPreferredSize(new Dimension(35,20));
                boutonUtilisableSeul = true;
                if(currEtudiant.getNiveau()<5 & j==0){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(currEtudiant.getNiveau()<10 & j==1){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(currEtudiant.getNiveau()<15 & j==2){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(currEtudiant.getNiveau()<20 & j==3){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(currEtudiant.getNiveau()<25 & j==4){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(currEtudiant.getNiveau()<30 & j==5){
                    listePouvoirs[i][j].setBackground(new Color(96,96,96));
                    listePouvoirs[i][j].setForeground(new Color(255,255,255));
                    boutonUtilisableSeul=false;
                }
                if(boutonUtilisableSeul){
                    listePouvoirs[i][j].setBackground(Color.green);
                }
                if(currEtudiant.getPv()==0){
                    listePouvoirs[i][j].setBackground(Color.red);
                }
                
                listePouvoirs[i][j].setActionCommand("pouvoir "+i+" "+j);
                listePouvoirs[i][j].addActionListener(new GestAction());
                
                panneau.add(listePouvoirs[i][j],constraints);
                constraints.gridx++;
            }
            
            setCouleurPouvoirs(i);
            constraints.gridy++;
            constraints.gridx-=ListeDesEtudiants.NBR_POUVOIRS;
            lv=0;
        }
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                if(ouiOuNon("Êtes-vous sûr de vouloir fermer l'application?", "FERMETURE")){
                    String fichierXlsx;
                    int resultat = JFileChooser.CANCEL_OPTION;
                    boolean utiliserFichierDemarrage;
                    
                    while(resultat != JFileChooser.APPROVE_OPTION){
                        utiliserFichierDemarrage = ouiOuNon("Voulez-vous sauvegarder le fichier ?", "FERMETURE");
                        
                        try{
                            JFileChooser choix = null;
                            if(!utiliserFichierDemarrage){
                                System.exit(0);
                            } else
                                resultat = JFileChooser.APPROVE_OPTION;
                            
                            switch(resultat){
                                case JFileChooser.ERROR_OPTION:
                                    JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du choix de fichier.");

                                    if(!ouiOuNon("Voulez-vous recommencer ?", "FERMETURE"))
                                        System.exit(0);
                                    break;
                                case JFileChooser.CANCEL_OPTION:
                                    return;
                                case JFileChooser.APPROVE_OPTION:
                                    fichierXlsx = utiliserFichierDemarrage ? fichierPrincipale : choix.getSelectedFile().getCanonicalPath();
                                    if(!fichierXlsx.endsWith(".xlsx"))
                                        fichierXlsx += ".xlsx";

                                    liste.writeToutEtudiantsEtImages(fichierXlsx);
                                    System.exit(0);
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
        
        constraints.gridy = 0;
        constraints.gridx += 4;
        constraints.weightx = 0.1;
        constraints.weighty = 0.001;
        
	constraints.gridwidth = 1;
	constraints.gridheight = 1;
        aide.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
           
            lAide.setVisible(true);
        }});
        
        panneau.add(aide,constraints);
        constraints.gridx++;
        aPropos.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {          
            lAPropos.setVisible(true);
        }});
        panneau.add(aPropos,constraints);
        
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
            String[] cmds = cmd.split(" ");
            int indexEtudiant = 0;
            int indexPouvoir = 0;

            if(cmd.startsWith("pv inc") || cmd.startsWith("pv dec") || cmd.startsWith("exp inc") || cmd.startsWith("exp dec"))
                indexEtudiant = Integer.parseInt(cmds[2]); //Soit c'est pv/exp inc/dec, donc l'index est apres le 2e espace
            else{
                    indexEtudiant = Integer.parseInt(cmds[1]); //Soit c'est pouvoir, donc l'index est apres le 1e espace
                    indexPouvoir = Integer.parseInt(cmds[2]);
            }

            Etudiant currEtudiant = liste.getEtudiant(indexEtudiant);

            try{
                switch (cmds[0]) {
                    case "pv":
                        if(cmds[1].equals("inc")){
                            if(currEtudiant.getPv()>=currEtudiant.getRole().getMaxPv()){
                                if(ouiOuNon("L'élève a atteint ou dépassé le maximum de ses points de vie. Voulez-vous vraiment augmenter ses points de vie ?", "Augmenter les points de vie")){
                                    currEtudiant.setPv(currEtudiant.getPv()+1);
                                }
                            }else{
                                currEtudiant.setPv(currEtudiant.getPv()+1);
                            }
                        }
                        else{
                            currEtudiant.setPv(currEtudiant.getPv()-1);
                            if(currEtudiant.getPv()<0){
                                currEtudiant.setPv(0);
                            }
                        }
                        pv[indexEtudiant].setText(currEtudiant.getPv()+"/"+currEtudiant.getRole().getMaxPv());
                        if(currEtudiant.getPv()==0){                                                
                            teteDeMort[indexEtudiant].setVisible(true);
                            pv[indexEtudiant].setVisible(false);
                        }else{
                            teteDeMort[indexEtudiant].setVisible(false);
                            pv[indexEtudiant].setVisible(true);
                        }
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
                            JOptionPane.showMessageDialog(null, "L'étudiant est mort ! Il ne peux plus gagner d'expérience");
                        
                        progressBar[indexEtudiant].setString("Niv "+(liste.getEtudiant(indexEtudiant).getNiveau()+((liste.getEtudiant(indexEtudiant).getExp() == 1) ? 0.5 : 0))+"                            ");                       
                        
                        break;
                    case "pouvoir":
                        if(currEtudiant.getPv()== 0)
                            JOptionPane.showMessageDialog(null,"Pouvoir innacessible! L'élève est mort");
                        else if(listePouvoirs[indexEtudiant][indexPouvoir].getBackground().equals(couleur3) ) {
                            if(ouiOuNon("Voulez vous vraiment utiliser le pouvoir?", "Activer Pouvoir"))
                                currEtudiant.setPouvoir(indexPouvoir, false);
                        }else if(listePouvoirs[indexEtudiant][indexPouvoir].getBackground().equals(couleur5)) 
                            JOptionPane.showMessageDialog(null,"Le pouvoir est déja actif");
                        else
                            JOptionPane.showMessageDialog(null,"Le niveau de l'etudiant est insuffisant pour utiliser le pouvoir"); 
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
    
    protected void setCouleurPouvoirs(int indEtudiant){
        int indPouvoir = 0;
        Etudiant currEtudiant = this.liste.getEtudiant(indEtudiant);
        
        if(currEtudiant.getPv()== 0){ // bouton inutilisable couleur
            for(int j=0; j<6; j++){
                listePouvoirs[indEtudiant][j].setBackground(couleur4); 
                listePouvoirs[indEtudiant][j].setForeground(couleur1);
            }
        }else{      // couleur pour un bouton normal  
            for(int j=0; j<6; j++){
                listePouvoirs[indEtudiant][j].setBackground(couleur2); 
                listePouvoirs[indEtudiant][j].setForeground(couleur1);
            }   

            if(currEtudiant.getNiveau()>=5){ //couleur pour un pouvoir actif
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
            if(currEtudiant.getPouvoir(indPouvoir) == false){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
            }
                indPouvoir++;        
            }            
                if(currEtudiant.getNiveau()>=10){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                //Moi: j'ai fait des copier coller pour bouton utlisable. j'ai l'impression qu'on peut le faire une fois pour tout mais ... la prochaine fois peu etre
                
                if(currEtudiant.getPouvoir(indPouvoir) == false){
                    listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                    listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                }
                indPouvoir++;
            } if(currEtudiant.getNiveau()>=15){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                if(currEtudiant.getPouvoir(indPouvoir) == false){
                    listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                    listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                }
                indPouvoir++;
            } if(currEtudiant.getNiveau()>=20){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                if(currEtudiant.getPouvoir(indPouvoir) == false){
                   listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                   listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                }
                indPouvoir++;
            } if(currEtudiant.getNiveau()>=25){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                if(currEtudiant.getPouvoir(indPouvoir) == false){
                    listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                    listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                }
                indPouvoir++;
            } if(currEtudiant.getNiveau()>=30){
                listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur3);
                listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                
                if(currEtudiant.getPouvoir(indPouvoir) == false){
                    listePouvoirs[indEtudiant][indPouvoir].setBackground(couleur5);
                    listePouvoirs[indEtudiant][indPouvoir].setForeground(couleur2);
                }
            }
        }
    }
    
    //Copiez collez de https://dzone.com/articles/programmatically-restart-java
    public static void restart() {
        try {
            // java binary
            String java = System.getProperty("java.home") + "/bin/java";
            // vm arguments
            List<String> vmArguments = (List<String>)ManagementFactory.getRuntimeMXBean().getInputArguments();
            StringBuffer vmArgsOneLine = new StringBuffer();
            
            for (String arg : vmArguments) {
                // if it's the agent argument : we ignore it otherwise the
                // address of the old application and the new one will be in conflict
                if (!arg.contains("-agentlib")) {
                    vmArgsOneLine.append(arg);
                    vmArgsOneLine.append(" ");
                }
            }
            // init the command to execute, add the vm args
            final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

            // program main and program arguments
            String[] mainCommand = System.getProperty("sun.java.command").split(" ");
            
            // program main is a jar
            if (mainCommand[0].endsWith(".jar")) {
                // if it's a jar, add -jar mainJar
                cmd.append("-jar " + new File(mainCommand[0]).getPath());
            } else {
                // else it's a .class, add the classpath and mainClass
                cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
            }
            
            // finally add program arguments
            for (int i = 1; i < mainCommand.length; i++) {
                cmd.append(" ");
                cmd.append(mainCommand[i]);
            }
            
            // execute the command in a shutdown hook, to be sure that all the
            // resources have been disposed before restarting the application
            Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec(cmd.toString()+" "+fichierPrincipale);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erreur lors du redémarrage, le programme doit s'arrêter.");
                    System.exit(1);
                }
            }
            });

            // exit
            System.exit(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du redémarrage, le programme doit s'arrêter.");
            System.exit(1);
        }
    }
}

