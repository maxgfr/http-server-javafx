/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author maxime
 */

import java.io.*;
import java.net.*;
import javafx.scene.control.TextArea;

public class Serveur extends Thread {

	private Socket socket;
        private PrintStream ps;

	public Serveur (Socket socketClient, TextArea ta){
		socket = socketClient;
                ps = new PrintStream(new Console(ta)) ; 
		start();
	}

	public void run(){
            //declaration de variable
            String reponse,mot[],separateur=" ";
            
            //initialisation permettant d'afficher la sortie standard et d'erreur sur la textArea grâce à la classe console
            System.setOut(ps);
            System.setErr(ps);
            
            try {

                // Flux des Messages d'entrée (en provenance du serveur)
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

                // Flux des Messages de sortie (à destination du serveur)
                PrintStream out= new PrintStream(socket.getOutputStream());
                
                // Lecture du message du client
                String messageClient = in.readLine(); 
                
                //partager le message reçu
                mot=messageClient.split(separateur);
                
                //traitement de ce message partagé par l'algorithme de traitement
                reponse = traitementError(mot);
                
                //affichage de la réponse avant son envoi
                System.out.println(reponse);
                
                // Envoi de la réponse
                out.println(reponse);        

                //fermeture de la socket, message d'entrée et de sorties
                in.close();
                out.close();
                socket.close();
              
                } catch (IOException e) {
                    e.getMessage();
                }
            }
	
        
        
        
        private String traitementError (String mot[]) throws IOException {
            //declaration
            String url,reponse;
            char c;
            int charInt;
            
            //analyse de la réponse
            if (mot[0].equals("GET") && mot[2].equals("HTTP/1.1")){     
                
                url=mot[1];
                System.out.println(url+"\n");

                if (url.equals("/")) {
                    url="index.html";
                } else{
                    url=url.substring(1);
                    url.replace("/", "\\");
                }

                try {  
                    
                    File inputFile = new File(url);
                    FileReader htmlFile=new FileReader(inputFile);
                    reponse="HTTP/1.1 200 OK\nServer: test\nMime-Version: 1.0\nContent-Type: text/html\n\n";
                    System.out.println(reponse);
                    charInt=htmlFile.read();
                    
                    while (charInt!=-1){
                            c=(char)charInt;
                            reponse=reponse+c;
                            charInt=htmlFile.read();
                    }
                    
                    htmlFile.close();
                    
                } catch(FileNotFoundException e){
                    //affichage du message d'erreur pour le client
                    reponse="HTTP/1.1 404 NOT FOUND\nServer: test\nMime-Version: 1.0\nContent-Type: text/html\n\n<html><body><h1>404 not found</h1></br>La page que vous demandez n'existe pas !</body></html>";
                    //affichage du message d'erreur sur le programme
                    System.out.println("Fichier non trouvé ! \n Message d'erreur : "+e.getMessage());
                }
            } else{
                //affichage du message d'erreur pour le client
                reponse="HTTP/1.1 300 HTTP Server Problem";
                //affichage du message d'erreur sur le programme
                System.out.println("Problème lié au serveur ! \n");
            }
            
            return reponse;
        }
	
}


