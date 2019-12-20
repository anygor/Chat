package com.epam;

import java.io.*;
import java.net.Socket;

import org.apache.log4j.Logger;

public class Client {

    Socket socket;
    Reader reader1;
    Writer writer;
    BufferedReader reader;
    BufferedReader in;
    BufferedWriter out;
    String host;
    int port;

    String nickname;

    private static final Logger log = Logger.getLogger(Client.class);

    public Client(String _host, int _port){
        host = _host;
        port = _port;
        try{
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            setNickname();
            reader1 = new Reader(in);
            writer = new Writer(reader, out, nickname);
            reader1.start();
            writer.start();
        }
        catch (IOException err){
            log.error(err);
        }
    }

    private void setNickname(){
        try {
            log.info("login >");
            nickname = reader.readLine();
            out.write("'" + nickname + "'" + " has joined the chat" + '\n');
            out.flush();
        }
        catch (IOException err) {}
    }

    public static void launch(){
        String host = "localhost";
        int port = 4470;
        new Client(host, port);
    }

}
