package com.epam;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

public class History {
    private static Queue<String> history;
    private static InputStream io;

    public Queue<String> getHistory(){
        return history;
    }

    public void addToHistory(String msg){
        if(history.size()>=5){
            history.remove(0);
        }
        history.add(msg);
    }
    public void sendHistory(BufferedWriter out){
        if(!history.isEmpty()){
            try{
                for (String iter:
                     history) {
                    out.write(iter);
                }
                out.flush();
            }
            catch(IOException err){}
        }
    }
}
