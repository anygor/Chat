package com.epam;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    private static final int port = 4470;
    private static ServerSocket server;
    private static Socket clientSocket;

    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main( String[] args ) {
        try {
            try {
                    log.info("Server start");
                    server = new ServerSocket(port);
                    clientSocket = server.accept();
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String line;
                    try {
                        while(true) {
                            line = in.readLine();
                            if (line.equals("#interrupted")) break;
                            log.info(line);
                            out.write("Line received: " + line + '\n');
                            out.flush();
                        }
                    } catch (IOException err) {
                        log.error(err);
                    } finally {
                        clientSocket.close();
                        in.close();
                        out.close();
                    }
                }
                finally{
                    log.info("Server closed");
                    server.close();
                }
            } catch (IOException e) {
                log.error(e);
            }
    }
}
