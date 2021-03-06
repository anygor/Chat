package com.epam;

import org.apache.log4j.Logger;
import java.io.*;
import java.net.Socket;

/**
 * ServerThread is created to support connection to particular client
 * @author Andrei_Gordeev
 * @version 1.0
 */

public class ServerThread extends Thread {
    /**
     * socket for getting io streams
     */
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private static final Logger log = Logger.getLogger(ServerThread.class);

    public ServerThread(Socket _socket){
        socket = _socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter((new OutputStreamWriter(socket.getOutputStream())));
        }
        catch(IOException e){
            log.error(e);
        }
        log.info("Server Thread initiated");

        Server.history.sendHistory(out);
    }
    public void run() {
        String line;
        try {
            while(true) {
                line = in.readLine();
                if (line.equals("#interrupted")) {
                    this.abort();
                    break;
                }
                Server.history.addToHistory(line + '\n');
                for (ServerThread st:Server.serverList) {
                    st.sendMsg(line);
                }
            }
        } catch (IOException err) {
            this.abort();
        }
    }

    private void abort(){
        try{
            Server.serverList.remove(this);
            socket.close();
            in.close();
            out.close();
            log.info("Thread aborted");
        }
        catch(IOException err){
            log.error(err);
        }
    }

    private void sendMsg(String line){
        try{
            out.write(line + '\n');
            out.flush();
        }
        catch(IOException err){
            log.error(err);
        }
    }
}
