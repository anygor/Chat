package com.epam;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;
    public static void main( String[] args ){
        try{
            try{
                clientSocket = new Socket("localhost", 4470);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                log.info("Type your message");
                String line = reader.readLine();
                out.write(line + '\n');
                out.flush();
                String serverLine = in.readLine();
                log.info(serverLine);
            }
            finally {
                log.info("close client");
                clientSocket.close();
                in.close();
                out.close();
            }
        }
        catch (IOException err){
            log.error(err);
        }
    }
}