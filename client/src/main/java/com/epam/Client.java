package com.epam;

import java.io.*;
import java.net.Socket;

import org.apache.log4j.Logger;

public class Client {

    static Socket socket;
    static BufferedReader reader;
    static BufferedReader in;
    static BufferedWriter out;
    static String host;
    static int port;

    private static final Logger log = Logger.getLogger(Client.class);

    public Client(String _host, int _port){
        host = _host;
        port = _port;
        try{
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new Reader().start();
            new Writer().start();
        }
        catch (IOException err){
            log.error(err);
        }
    }

    static void abort(){
        try{
            socket.close();
            in.close();
            out.close();
        }
        catch(IOException err){
            log.error(err);
        }
    }

    public static void launch(){
        String host = "localhost";
        int port = 4470;
        new Client(host, port);
    }

}
