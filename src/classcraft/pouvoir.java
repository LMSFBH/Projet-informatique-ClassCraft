/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;

/**
 *
 * V:1.0
 */
public class pouvoir {
    public int debloque;
    boolean utiliser;
    
    public pouvoir(){
        
    }
    
    public pouvoir(int niveau){
        setDebloque(niveau);
        setUtiliser(false);
    }
    
    public void setDebloque(int debloque){
        this.debloque=debloque;
    }
    
    public void setUtiliser(boolean entreUtiliser){
        this.utiliser=utiliser;
    }
    
    public int getDebloque(){
        return debloque;
    }
    
    public boolean getUtiliser(){
        return utiliser;
    }
    
    public void action (int puissance){
        // Methode qui va etre Override pour l'effet du pouvoir
    }
}
