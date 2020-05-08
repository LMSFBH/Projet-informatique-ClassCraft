/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseAventure;

/**
 * 
 * V.1.0
 */
public class Role {
    private String role;
    
    private int MaxPv;
    private int indexNv5,indexNv10, indexNv15,indexNv20,indexNv25,indexNv30;
    
    public Role(){
    
    }
    
    public Role (String nomRole,int maxPv,int iNv5,int iNv10,int iNv15,int iNv20,int iNv25,int iNv30 ){
        role = nomRole;
        MaxPv=maxPv;
        indexNv5 = iNv5;
        indexNv10 = iNv10;
        indexNv15 = iNv15;
        indexNv20 = iNv20;
        indexNv25 = iNv25;
        indexNv30 = iNv30;
    }
    
    public int getIndexNv5(){
        return indexNv5;
    }
    
    public int getIndexNv10(){
        return indexNv10;
    }
    
    public int getIndexNv15(){
        return indexNv15;
    }
    
    public int getIndexNv20(){
        return indexNv20;
    }
    
    public int getIndexNv25(){
        return indexNv25;
    }
    
    public int getIndexNv30(){
        return indexNv30;
    }
    
    public String getRole(){
        return role;
    }
   
    public void setRole(String NouveauJob){
        this.role=role;
    }
    
    public int getMaxPv(){
        return MaxPv;
    }
  
}
