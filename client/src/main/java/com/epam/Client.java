package com.epam;

import java.io.*;
import java.net.Socket;

import org.apache.log4j.Logger;

public class Client {

    Socket socket;
    BufferedReader reader;
    BufferedReader in;
    BufferedWriter out;
    String host;
    int port;

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

    private class Reader extends Thread{
        public void run(){
            String line;
            try {
                while (true) {
                    log.info(">");
                    line = in.readLine();
                    if (line.equals("/quit")) {
                        abort();
                        break;
                    }
                    log.info(line);
                }
            }
            catch (IOException err){
                log.error(err);
            }
        }
    }

    private class Writer extends Thread{
        public void run(){
            String line;
            try {
                while (true) {
                    line = reader.readLine();
                    if (line.equals("/quit")) {
                        out.write("#interrupted" + '\n');
                        out.flush();
                        abort();
                        break;
                    } else {
                        out.write(line + '\n');
                    }
                    out.flush();
                }
            }
            catch (IOException err){
                log.error(err);
            }
        }
    }

    void abort(){
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
