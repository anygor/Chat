package com.epam;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Properties;


public class Server {
    private static final Logger log = Logger.getLogger(Server.class);
    public static History history;
    static LinkedList<ServerThread> serverList;
    private ServerSocket server;
    private int port;
    private int timeout;

    public Server(){
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
            Properties property = new Properties();
            property.load(fileInputStream);
            serverList = new LinkedList<>();
            port = Integer.parseInt(property.getProperty("db.port"));
            timeout = Integer.parseInt(property.getProperty("db.timeout"));
            server = new ServerSocket(port);
            history = new History();
        }
        catch(IOException e){
            log.error("Constructor IO exception");
        }
    }

    public void initiate() {
        log.info("Server initiated");
        try {
            do {
                try {
                    server.setSoTimeout(timeout);
                    Socket socket = server.accept();
                    serverList.add(new ServerThread(socket));
                } catch (SocketTimeoutException e) {
                    if (serverList.isEmpty()) {
                        log.info("Empty server timeout exception");
                        History.fileHistoryOutput();
                        server.close();
                        break;
                    }
                }
            } while (true);
        }
        catch(IOException err){
            log.error(err);
        }
    }
}
