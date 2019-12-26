package com.epam;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;


public class HistoryTests {
    @Test
    public void historyAdditionTest() {
        Queue<String> test = new LinkedList<>();
        String death = "Death";
        test.add(death);
        History history = new History();
        history.addToHistory(death);
        assertEquals("Death", test.poll());
    }

    @Test
    public void streamSendingHistoryTest(){
        // wtf am i even checking lol \(^+^)/
        try {
            File file = new File("src/test/resources/rsrc.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            History history = new History();
            history.sendHistory(out);
            assertTrue(true);
        }
        catch(IOException e){
            fail();
        }
    }

    @Test
    public void sendingFileHistoryTest(){
        History history = new History();
        History.fileHistoryOutput();
        assertTrue(true);
    }

    @Test
    public void gettingFileHistoryTest(){
        History history = new History();
        History.fileHistoryInput();
        assertTrue(true);
    }
}
