/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseEtDragons;

import java.io.*;

/**
 * Classe décrivant un étudiant
 */
public class Etudiant {
    private String nAdmission, nom, pseudo, cheminImage;
    private int exp, niveau, pv, role;
    private boolean pouvoirsUtilisable[] = {true, true, true, true, true, true};
    BufferedReader entree;
    
    /**
     * Liste des rôles que l'étudiant peut prendre
     */
    static Role[] roles = new Role[4];
    
    public final int MAX_EXP = 1;

    /**
     * Constructeur à 2 paramètres pour Etudiant
     * 
     * @param nAdmission Le numéro d'admission de cet Etudiant
     * @param nom        Le nom de cet Etudiant
     */
    public Etudiant(String nAdmission, String nom){
        setName(nom);
        setNAdmission(nAdmission);
    }
    
    /**
     * Constructeur à 4 paramètres pour Etudiant
     * 
     * @param nAdmission Le numéro d'admission de cet Etudiant
     * @param nom        Le nom de cet Etudiant
     * @param role       Le role de cet Etudiant
     * @param pseudo     Le pseudo de cet Etudiant
     * 
     * @throws IllegalArgumentException      Si l'un des arguments n'est pas permis par un setteur
     * @throws java.io.FileNotFoundException Si le fichier rôle n'a pas put être accéder
     * @throws IOException                   Si le fichier rôle n'a pas put être accéder
     * @throws Exception                     Si le fichier rôle n'a pas put être accéder
     */
    public Etudiant(String nAdmission, String nom, int role, String pseudo) throws IllegalArgumentException, FileNotFoundException, IOException, Exception{
        setName(nom);
        setNAdmission(nAdmission);
        setRole(role);
        setPseudo(pseudo);
        setExp(0);
        setNiveau(1);
        
        setCheminImage(null);
        
        try{
            entree = new BufferedReader(new FileReader("docs/roles.txt "));
            String ligne, nomRole=null;
            int nombreLigne = 0, vie=0, i=0;
            while((ligne=entree .readLine() ) != null){
                if(nombreLigne==0){
                    nomRole=ligne;
                    nombreLigne++;
                }else if(nombreLigne==1){
                    vie=Integer.parseInt(ligne);
                    roles[i] = (new Role(nomRole,vie));
                    i++;
                    nombreLigne=0;
                }
            }
        } catch (FileNotFoundException e)    { // Exception déclenchée si le fichier n'existe pas 
            throw new FileNotFoundException("Le fichier n'existe pas");
        }
        finally {
            entree.close();
        }
        
        setPv(getRole().getMaxPv());
    }
    
    /**
     * Constructeur à 4 paramètres pour Etudiant
     * 
     * @param nAdmission         Le numéro d'admission de cet Etudiant
     * @param nom                Le nom et le nom de famille de cet Etudiant
     * @param role               Le role de cet Etudiant
     * @param pseudo             Le pseudo de cet Etudiant
     * @param cheminImage        Chemin de l'image qu'a choisis cet Etudiant
     * @param exp                L'expérience de cet Etudiant
     * @param niveau             Le niveau de cet Etudiant
     * @param pv                 Les points de vie de cet Etudiant
     * @param pouvoirsUtilisable La liste des pouvoirs utilisable par cet Etudiant
     * 
     * @throws IllegalArgumentException      Si l'un des arguments n'est pas permis par un setteur
     * @throws java.io.FileNotFoundException Si le fichier rôle n'a pas put être accéder
     * @throws IOException                   Si le fichier rôle n'a pas put être accéder
     * @throws Exception                     Si le fichier rôle n'a pas put être accéder
     */
    public Etudiant(String nAdmission, String nom, int role, String pseudo, String cheminImage, int exp, int niveau, int pv, boolean[] pouvoirsUtilisable) throws FileNotFoundException, IOException, Exception{
        setNAdmission(nAdmission);
        setName(nom);
        setRole(role);
        setPseudo(pseudo);
        setExp(exp);
        setNiveau(niveau);
        setPv(pv);
        setCheminImage(cheminImage);
        setPouvoirs(pouvoirsUtilisable);
        
        try{
            entree= new BufferedReader(new FileReader("docs/roles.txt "));
            String ligne, nomRole=null;
            int nombreLigne = 0, vie=0, i=0;
            while((ligne=entree .readLine()) != null){
                if(nombreLigne==0){
                    nomRole=ligne;
                    nombreLigne++;
                }else if(nombreLigne==1){
                    vie=Integer.parseInt(ligne);
                    roles[i] = (new Role(nomRole,vie));
                    i++;
                    nombreLigne=0;
                }
            }
        } catch (FileNotFoundException e)    { // Exception déclenchée si le fichier n'existe pas 
            throw new FileNotFoundException("Le fichier n'existe pas");
        }
        
        finally {
            entree.close();
        }
    }
    
    //Accesseurs/Setteurs pour Etudiant
    public void setNAdmission(String nAdmission){
        if((nAdmission == null) || nAdmission.isEmpty())
            throw new IllegalArgumentException("Le nom est vide.");
        
        this.nAdmission=nAdmission;
    }
    
    public void setName(String nom){
        if((nom == null) || nom.isEmpty())
            throw new IllegalArgumentException("Le nom est vide.");
        
        this.nom=nom;
    }
    
    public void setRole(int role){
        if((role < 0) || role > roles.length)
            throw new IllegalArgumentException("Le role doit être entre 0 et "+roles.length+".");
        
        this.role = role;
    }
    
    public void setPseudo(String pseudo){
        if((pseudo == null) || pseudo.isEmpty())
            throw new IllegalArgumentException("Le pseudo est vide.");
        
        this.pseudo=pseudo;
    }
    
    public void setCheminImage(String cheminImage){
        // Ne pas verifiez si cheminImage est vide ou nom, car l'image n'est pas obligatoire
        this.cheminImage=cheminImage;
    }
    
    public void setExp(int exp){
        if((exp < 0) || (exp > MAX_EXP)){
            throw new IllegalArgumentException("L'exp doit etre soit 0 ou 1.");
        }
        
        this.exp=exp;
    }
    
    public void setNiveau(int niveau){
        if(niveau <= -1)
            throw new IllegalArgumentException("Le niveau ne peut pas etre negatif.");
        
        this.niveau=niveau;
    }
    
    public void setPv(int pv){
        if(pv < 0)
            throw new IllegalArgumentException("Les pv ne peuvent pas etre negatif.");
        
        this.pv=pv;
    }
    
    public void setPouvoir(int index, boolean estUtilisable){
        pouvoirsUtilisable[index] = estUtilisable;
    }
    
    public void setPouvoirs(boolean[] pouvoirsUtilisable){
        if(pouvoirsUtilisable.length != ListeDesEtudiants.NBR_POUVOIRS)
            throw new IllegalArgumentException("Nombre de pouvoirs incorrect.");
        
        this.pouvoirsUtilisable = pouvoirsUtilisable;
    }
    
    public String getNAdmission(){
        return nAdmission; 
    }
    
    public String getName(){
        return nom; 
    } 
    
    public int getRoleIndex(){
        return role;
    }
    
    public String getPseudo(){
        return pseudo; 
    }
    
    public String getCheminImage(){
        return cheminImage; 
    }
    
    public int getExp(){
        return exp;
    }
    
    public int getNiveau(){
        return niveau;
    }
    
    public int getPv(){
        return pv;
    }
    
    public Role getRole(){
       return roles[getRoleIndex()]; 
    }
    
    public boolean[] getPouvoirs(){
        return pouvoirsUtilisable;
    }
    
    public boolean getPouvoir(int index){
        return pouvoirsUtilisable[index];
    }
}