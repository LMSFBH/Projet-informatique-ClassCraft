/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;

/**
 * 
 * V.1.0
 */
public class Role {
    private String job;
    
    private int MaxPv;
    int indexNv5,indexNv10, indexNv15,indexNv20,indexNv25,indexNv30;
    public Role(){
    
    }
    
    public Role (String nomJob,int maxPv,int iNv5,int iNv10,int iNv15,int iNv20,int iNv25,int iNv30 ){
        job = nomJob;
        MaxPv=maxPv;
        indexNv5 = iNv5;
        indexNv10 = iNv10;
        indexNv15 = iNv15;
        indexNv20 = iNv20;
        indexNv25 = iNv25;
        indexNv30 = iNv30;
       
             
        
    }
    
    public int getindexNv5(){
        return indexNv5;
    }
    public int getindexNv10(){
        return indexNv10;
    }
    public int getindexNv15(){
        return indexNv15;
    }
    public int getindexNv20(){
        return indexNv20;
    }
    public int getindexNv25(){
        return indexNv25;
    }
    public int getindexNv30(){
        return indexNv30;
    }
    
    
    
    
    
    
    public String getJob(){
        return job;
    }
    
   
    public void setJob(String NouveauJob){
        this.job=job;
    }
   
  
}
