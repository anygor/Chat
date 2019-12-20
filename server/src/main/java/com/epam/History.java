package com.epam;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.log4j.Logger;

public class History {
    private static final Logger logFile = Logger.getLogger("HistoryFile");
    private static Queue<String> history;
    public History(){
        history = new LinkedList<>();
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
}
