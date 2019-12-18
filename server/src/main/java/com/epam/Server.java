package com.epam;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class);
    private static final int port = 4470;
    public static LinkedList<ServerThread> serverList = new LinkedList<>();

    public static void initiate(){
        log.info("Server initiated");
        try{
            ServerSocket server = new ServerSocket(port);
            while(true){
                Socket socket = server.accept();
                serverList.add(new ServerThread(socket));
            }
        }
        catch(IOException err){
            log.error(err);
        }
    }

}
