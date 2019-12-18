package com.epam;

import org.apache.log4j.Logger;

import java.io.IOException;

public class Reader extends Thread {
    private static final Logger log = Logger.getLogger(Reader.class);
    public void run(){
        String line;
        try {
            while (true) {
                log.info(">");
                line = Client.in.readLine();
                if (line.equals("/quit")) {
                    Client.abort();
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
