/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseAventure;

/**
 *
 * v1.0
 
public class Guerrieur extends Role {
    final int MAX_PV_GUERRIEUR=5;
    
    public Guerrieur(){
        super("Guerrieur",5);
    }
    
    @Override
    public void action(int puissance){
        switch(puissance){
            case 1: setCurrentPv(getCurrentPv()+1);  
            
        }
    }
}*/
