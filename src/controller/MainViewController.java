/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.GestionServeur;
import java.io.PrintStream;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Console;

/**
 *
 * @author maxime
 */
public class MainViewController implements Initializable {
    
    @FXML
    private Label nbthread;
    
    @FXML
    private Label etat;
    
    @FXML
    private Label port;
    
    @FXML
    private Label lastclient;
    
    @FXML
    private TextArea message;
    
    
    @FXML
    private Button start;
    @FXML
    private Button stop;
    
    private static GestionServeur gs;
    
    @FXML
    private void start() {
        start.setDisable(true); //eviter la création d'un nouveau thread
        stop.setDisable(false);
        etat.setText("En Marche");
        gs = new GestionServeur(message);  // create new runnable
        Thread t = new Thread(gs); // create thread
        t.start();
    }
    
    
    @FXML
    private void stop() {
        start.setDisable(false);
        stop.setDisable(true);
        etat.setText("A l'arrêt");
        gs.stop();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
