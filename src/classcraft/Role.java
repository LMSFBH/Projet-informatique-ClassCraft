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
public class Role extends Pouvoir  {
    private String job;
    private int currentPv;
    public Pouvoir pouvoirs = new Pouvoir();
    
    public Role(){
    
    }
    
    public Role (String nomJob,int max){
        setJob(nomJob);
        setCurrentPv(max);
    }
    
    public String getJob(){
        return job;
    }
    public int getCurrentPv(){
        return currentPv;
    }
   
    public void setJob(String NouveauJob){
        this.job=job;
    }
   
    public void setCurrentPv(int currentPv){
        this.currentPv=currentPv;
    }
}
