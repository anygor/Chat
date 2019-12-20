package com.epam;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Writer extends Thread {
    private static final Logger log = Logger.getLogger(Writer.class);
    private BufferedReader reader;
    private BufferedWriter writer;

    public Writer(BufferedReader _reader, BufferedWriter _writer){
            reader = _reader;
            writer = _writer;
    }
    public void run() {
        String line;
        try {
            while (true) {
                log.info(">");
                line = reader.readLine();
                if (line.equals("/quit")) {
                    writer.write("#interrupted" + '\n');
                    writer.flush();
                    break;
                } else {
                    writer.write(line + '\n');
                    writer.flush();
                }
            }
        } catch (IOException err) {
        }
    }
}