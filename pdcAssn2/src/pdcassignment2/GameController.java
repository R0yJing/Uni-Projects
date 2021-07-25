/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Controller for the GameGUI, as per the MVC paradigm.
 * @author Roy
 */
public class GameController extends WindowAdapter implements WindowListener{
    
    private GameGUI GUI;
    private GameModel model;
   
    
    //controller cannot addview during construction as that would mean the view is
    //local to the controller only.
    public void addView(GameGUI GUI){
        this.GUI = GUI;
        
    }
    
    
    
    public void addModel(GameModel model){
        this.model = model;
    }
    
    
    public void windowClosing(WindowEvent e){
        if (!GameModel.isGameOver()){
            model.setInterrupted(true);
            GUI.promptQuit();
        } else{
            model.closeSession();
        }
        
    }
    
    
    
    
}


