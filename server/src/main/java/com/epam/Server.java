package com.epam;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Properties;


public class Server {
    private static final Logger log = Logger.getLogger(Server.class);
    public static History history;
    static LinkedList<ServerThread> serverList = new LinkedList<>();
    static ServerSocket server;

    public static void initiate() {
        log.info("Server initiated");
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
            Properties property = new Properties();
            property.load(fileInputStream);
            int port = Integer.parseInt(property.getProperty("db.port"));
            server = new ServerSocket(port);
            history = new History();
            do {
                try {
                    server.setSoTimeout(10000);
                    Socket socket = server.accept();
                    try {
                        serverList.add(new ServerThread(socket));
                    } catch (IOException err) {
                        socket.close();
                    }
                }
                catch(SocketTimeoutException e){
                    if(serverList.isEmpty()){
                        log.info("Empty server timeout exception");
                        History.fileHistoryOutput();
                        server.close();
                        break;
                    }
                }
            }while(true);
        }
        catch(FileNotFoundException err) {
            log.error("config.properties file not found");
        }
        catch (IOException err) {
            log.error(err);
        }
    }
}
