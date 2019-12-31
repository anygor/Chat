package com.epam;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Global connection center for clients of the chat
 * @author Andrei_Gordeev
 * @version 1.0
 */
public class Server {
    /**
     * logger for logging
     * history for saving messages after server abortion/restart
     * serverList - a list of threads for each client
     * timeout - amount of time in for the server to shut down if empty
     *
     */
    private static final Logger log = Logger.getLogger(Server.class);
    public static History history;
    static LinkedList<ServerThread> serverList;
    private ServerSocket server;
    private int port;
    private int timeout;

    public Server(){
        try {
            FileInputStream fileInputStream = new FileInputStream("server/src/main/resources/config.properties");
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
                    ServerThread serverThread = new ServerThread(socket);
                    serverList.add(serverThread);
                    serverThread.start();
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
