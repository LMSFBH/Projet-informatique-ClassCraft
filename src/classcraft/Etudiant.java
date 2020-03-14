/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;

/**
 *
 * V:1.05
 */
public class Etudiant {
    private String nAdmission, nom, role, pseudo, cheminImage;
    private int exp, niveau, pv;
    Role job;
    
    public static final int MAX_EXP = 1;
    
    public Etudiant(String nAdmission, String nom){
        setName(nom);
        setNAdmission(nom);
    }
    
    public Etudiant(String nAdmission, String nom, String role, String pseudo, String cheminImage, int exp, int niveau, int pv){
        setNAdmission(nAdmission);
        setName(nom);
        setRole(role);
        setPseudo(pseudo);
        setExp(exp);
        setNiveau(niveau);
        setpv(pv);
        setCheminImage(cheminImage);
    }
    
    /*public Etudiant(String nom,String role,String pseudo){
    setName(nom);
    setRole(role);
    setPseudo(pseudo);
    setExp(0);
    setNiveau(1);
    }*/
    
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
    
    public void setRole(String role){
        if((role == null) || role.isEmpty())
            throw new IllegalArgumentException("Le role est vide.");
        
        this.role=role;
    }
    
    public void setPseudo(String pseudo){
        if((pseudo == null) || pseudo.isEmpty())
            throw new IllegalArgumentException("Le pseudo est vide.");
        
        this.pseudo=pseudo;
    }
    
    public void setCheminImage(String cheminImage){
        // Ne pas verifiez si c'est vide ou nom, car l'image n'est pas obligatoire (pour l'instant?)
        this.cheminImage=cheminImage;
    }
    
    //Est-ce qu'on fait ca
    public void setExp(int exp){
        if((exp < 0) || (exp > MAX_EXP))
            throw new IllegalArgumentException("L'exp doit etre soit 0 ou 1.");
        
        this.exp=exp;
    }
    
    //ou ca
    public void incExp(){
        exp++;
        
        if(exp > MAX_EXP){
            niveau++;
            exp = 0;
        }
    }
    
    //Meme chose, on a jamais besoins de set le niveau si incExp s'en charge
    public void setNiveau(int niveau){
        if(niveau <= 0)
            throw new IllegalArgumentException("Le niveau ne peut pas etre negatif ou nulle.");
        
        this.niveau=niveau;
    }
    
    //On fait ca
    public void setpv(int pv){
        if(pv < 0)
            throw new IllegalArgumentException("Les pv ne peuvent pas etre negatif.");
        
        this.pv=pv;
    }
    
    //Ou ca
    //Plus debatable dans ce cas, vu que je ne sais pas si un heal donne tout les pvs
    public void decpv(boolean isInit){
        //if(isInit || ((pv-1) == 0))
        //    pv = Role.PV_ROLE_BASE;
        //else
            pv--;
    }
    
    public String getNAdmission(){
        return nAdmission; 
    }
    
    public String getName(){
        return nom; 
    } 
    
    public String getRole(){
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
}