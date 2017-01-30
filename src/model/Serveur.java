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

public class Serveur extends Thread {

	private Socket socket;

	public Serveur (Socket socketClient){
		socket = socketClient;
		start();
	}

	public void run(){
            String reponse,url,mot[],separateur=" ";
            
            try {

                // Flux des Messages d'entrée (en provenance du serveur)
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

                // Flux des Messages de sortie (à destination du serveur)
                PrintStream out= new PrintStream(socket.getOutputStream());
                
                // Lecture du message du client
                String messageClient = in.readLine(); 
                
                mot=messageClient.split(separateur);
                
                 reponse = traitementError(mot);
                 
                // Envoi de la réponse
                out.println(reponse);
                
                // Lecture du message du client
                messageClient = in.readLine(); 


                in.close();
                out.close();
                socket.close();
              
                } catch (IOException e) {
                    e.getMessage();
                }
            }
	
        
        
        
        private String traitementError (String mot[]) throws IOException {
            String url,reponse;
            char c;
            int charInt;
             if (mot[0].equals("GET")&&mot[2].equals("HTTP/1.1")){
                            url=mot[1];
                            System.out.println(url+"\n");
                            if (url.equals("/")) url="index.html";
                            else{
                                    url=url.substring(1);
                            url.replace("/", "\\");
                            }
                            try{
                                    FileReader htmlFile=new FileReader(url);
                                    reponse="HTTP/1.1 200 OK\nDate: Wed, 10 Sep 2015 14:20:21 GMT\nServer: test\nMime-Version: 1.0\nContent-Type: text/html\nLast-Modified: Wed, 10 Sep 2015 14:20:23 GMT\nContent-Length: 139\n\n";
                                    charInt=htmlFile.read();
                                    while (charInt!=-1){
                                            c=(char)charInt;
                                            reponse=reponse+c;
                                            charInt=htmlFile.read();
                                    }
                                    htmlFile.close();
                            } catch(FileNotFoundException e){
                                    reponse="HTTP/1.1 404 NOTFOUND\nDate: Wed, 10 Sep 2015 14:20:21 GMT\nServer: test\nMime-Version: 1.0\nContent-Type: text/html\nLast-Modified: Wed, 10 Sep 2015 14:20:23 GMT\nContent-Length: 139\n\n<html>404 not found</html>";
                                    System.out.println("fichier non trouve \n");
                            }
                    }
                    else{
                            reponse="300";
                    }
             return reponse;
        }
	
}


