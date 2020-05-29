

package ClasseEtDragons;

/**
 * Classe décrivant un rôle particulier (exemple: Guerrier)
 */
public class Role {
    /**
     * Nom de ce rôle
     */
    private String nomRole;
    
    /**
     * Maximum des pvs de ce rôle
     */
    private int maxPv;
    
    /**
     * Constructeur sans paramètre de la classe Role
     */
    public Role(){
    
    }
    
    /**
     * Constructeur de la classe Role
     * 
     * @param nomRole Nom du role à crée
     * @param maxPv   Pv maximum du role a crée
     */
    public Role (String nomRole,int maxPv){
        this.nomRole = nomRole;
        this.maxPv = maxPv;
    }
    
    //Accesseurs/Setteurs pour nomRole
    public String getNomRole(){
        return nomRole;
    }
   
    public void setNomRole(String role){
        this.nomRole = role;
    }
    
    /**
     * Retourne les pv maximums de ce rôle
     * 
     * @return maxPv maximum des pv de ce rôle
     */
    public int getMaxPv(){
        return maxPv;
    }
  
}
