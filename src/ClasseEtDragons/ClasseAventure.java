/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasseEtDragons;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 *
 * @author 
 */
public class ClasseAventure{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        MainFrame frame;
        if((args.length == 0) || (args[0] == null))
            frame = new MainFrame(null);
        else
            frame = new MainFrame(args[0]);
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }
}