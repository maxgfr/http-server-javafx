/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author maxime
 */
public class GestionServeur implements Runnable {
    private volatile boolean running;  // this indicates it's running

    public GestionServeur() {
        running = true;  // thread is running when it's created (though not really until "run" is called)
    }

    // called from start()
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(80);
            while (running) {
                new Serveur(serverSocket.accept());
            }
        } catch (IOException io) {
            io.getMessage();
        }
  
    }

    // call this method to stop the thread, from outside on the object reference
    public synchronized void stop() {
        running = false;
    }
}