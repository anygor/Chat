package com.epam;

import org.apache.log4j.Logger;
import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private static final Logger log = Logger.getLogger(ServerThread.class);

    public ServerThread(Socket _socket) throws IOException {
        socket = _socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter((new OutputStreamWriter(socket.getOutputStream())));
        log.info("Server Thread initiated");
        start();
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
                for (ServerThread st:Server.serverList) {
                    st.sendMsg(line);
                }
            }
        } catch (IOException err) {
            this.abort();
        }
    }

    void abort(){
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
            out.write("usernameLine: " + line + '\n');
            out.flush();
        }
        catch(IOException err){
            log.error(err);
        }
    }
}
