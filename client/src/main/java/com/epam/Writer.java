package com.epam;

import org.apache.log4j.Logger;

import java.io.IOException;

public class Writer extends Thread {
    private static final Logger log = Logger.getLogger(Writer.class);
    public void run(){
        String line;
        try {
            while (true) {
                line = Client.reader.readLine();
                if (line.equals("/quit")) {
                    Client.abort();
                    break;
                } else {
                    Client.out.write(line + '\n');
                }
                Client.out.flush();
            }
        }
        catch (IOException err){
            log.error(err);
        }
    }
}