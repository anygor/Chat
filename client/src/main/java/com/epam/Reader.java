package com.epam;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader extends Thread{
    private BufferedReader reader;
    private String nickname;

    public Reader(BufferedReader br, String _nickname){
        reader = br;
        nickname = _nickname;
    }

    private static final Logger log = Logger.getLogger(Reader.class);
    public void run(){
        String line;
        try {
            while (true) {
                line = reader.readLine();
                log.info(line);

            }
        }
        catch (IOException err){
            log.info("'" + nickname + "'" + " has left the chat" + '\n');
        }
        finally {
            try {
                reader.close();
            }
            catch(IOException err) {}
        }
    }
}
