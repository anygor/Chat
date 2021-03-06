package com.epam;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This thing is in charge of getting messages from the server
 * @author Andrei_Gordeev
 */

public class Reader extends Thread{
    private BufferedReader reader;

    public Reader(BufferedReader br){
        reader = br;
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
        catch (IOException err){}
        finally {
            try {
                reader.close();
            }
            catch(IOException err) {
                log.error(err);
            }
        }
    }
}
