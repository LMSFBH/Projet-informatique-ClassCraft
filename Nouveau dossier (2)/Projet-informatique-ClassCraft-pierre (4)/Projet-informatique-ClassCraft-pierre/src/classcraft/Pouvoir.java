/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;

import static classcraft.MainFrame.pv;

/**
 *
 * V:1.0
 */
public class Pouvoir {
    
    public Pouvoir(){
        
    }
    
    
    public void action (int index,Etudiant utilisateur, ListeDesEtudiants liste ){
        switch(index){
            case 1: {
                utilisateur.setPv(utilisateur.getPv()-10);
                System.out.println("beep boop");
                break;
            }
            case 2:{
                utilisateur.setPv(utilisateur.getPv()+1);
                System.out.println("beep boop 2");
                MainFrame.pv[liste.getIndex(utilisateur)].setText(""+utilisateur.getPv());
                break;
            }
        
        
        
        
        
        
        default : System.out.println("beep boop beep");
    }
}
}