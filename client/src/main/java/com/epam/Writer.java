package com.epam;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Writer extends Thread {
    private static final Logger log = Logger.getLogger(Writer.class);
    private BufferedReader reader;
    private BufferedWriter writer;
    private String nickname;

    public Writer(BufferedReader _reader, BufferedWriter _writer, String _nickname){
            reader = _reader;
            writer = _writer;
            nickname = _nickname;
    }
    public void run() {
        String line;
        try {
            while (true) {
                line = reader.readLine();
                if (line.equals("/quit")) {
                    writer.write("'" + nickname + "'" + " has left the chat" + '\n');
                    writer.flush();
                    writer.write("#interrupted" + '\n');
                    writer.flush();
                    writer.close();
                    reader.close();
                    break;
                } else {
                    writer.write(nickname + ": " + line + '\n');
                    writer.flush();
                }
            }
        } catch (IOException err) {
            log.error(err);
        }
    }
}