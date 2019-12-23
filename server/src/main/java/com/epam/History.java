package com.epam;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.log4j.Logger;

public class History {
    private static final Logger logFile = Logger.getLogger("HistoryFile");
    private static Queue<String> history;
    private static BufferedWriter out;
    public History(){
        history = new LinkedList<>();
        try {
            //out = new PrintWriter(new FileOutputStream("src/main/resources/historyText.txt"));
            out = new BufferedWriter(new PrintWriter(new FileOutputStream("src/main/resources/historyText.txt", false)));
        }
        catch(FileNotFoundException e) {}

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
            catch(IOException err){}
        }
    }
    public static void fileHistory(){
        try {
            while (!history.isEmpty()) {
                out.write(history.poll());
                out.flush();
            }
        }
        catch(IOException e){}
    }
}
