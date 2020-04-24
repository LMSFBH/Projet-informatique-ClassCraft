/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classcraft;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 *
 * @author 
 */
public class ClassCraft{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws  FileNotFoundException, IOException, Exception{
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }
}