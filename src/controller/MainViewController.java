/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.GestionServeur;
import model.Serveur;

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
    private Label message;
    
    @FXML
    private Button start;
    @FXML
    private Button stop;
    
    private static GestionServeur gs;
    
    @FXML
    private void start() {
        start.setDisable(true); //eviter la création d'un nouveau thread
        stop.setDisable(false);
        etat.setText("connecté");
        gs = new GestionServeur();  // create new runnable
        Thread t = new Thread(gs); // create thread
        t.start();
    }
    
    
    @FXML
    private void stop() {
        start.setDisable(false);
        stop.setDisable(true);
        etat.setText("déconnecté");
        gs.stop();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
