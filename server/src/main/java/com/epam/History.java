package com.epam;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.log4j.Logger;

public class History {
    private static final Logger log = Logger.getLogger(History.class);
    private static final Logger logFile = Logger.getLogger("HistoryFile");
    private static Queue<String> history;
    private static BufferedReader in;
    private static BufferedWriter out;
    public History(){
        history = new LinkedList<>();
        fileHistoryInput();
        try {
            out = new BufferedWriter(new PrintWriter(new FileOutputStream("server/src/main/resources/historyText.txt", false)));
        }
        catch(FileNotFoundException e) {
            log.error(e);
        }

    }

    public void addToHistory(String msg) {
         if (history.size() >= 5) {
             history.remove();
    }
         history.add(msg);
         logFile.info(msg);
    }
    public void sendHistory(BufferedWriter out){
        if(history.size()!=0){
            try{
                out.write("History: "+'\n');
                for (String iter:history) {
                    out.write(iter + '\n');
                }
                out.write("..."+'\n');
                out.flush();
            }
            catch(IOException err){
                log.error(err);
            }
        }
    }
    public static void fileHistoryOutput(){
        try {
            while (!history.isEmpty()) {
                out.write(history.poll());
                out.flush();
            }
        }
        catch(IOException e){
            log.error(e);
        }
    }
    public static void fileHistoryInput(){
        try {
            in = new BufferedReader(new FileReader("server/src/main/resources/historyText.txt"));
            String buffer;
            while ((buffer = in.readLine()) != null) {
                history.add(buffer+'\n');
            }
        }
        catch(FileNotFoundException e){
            log.error(e);
        }
        catch(IOException e){
            log.error(e);
        }
    }
}
