package com.epam;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.BufferedWriter;
import java.util.LinkedList;
import java.util.Queue;


public class ServerTests {
    @Test
    public void queueTest() {
        Queue<String> test = new LinkedList<>();
        String death = "Death";
        test.add(death);
        History history = new History();
        history.addToHistory(death);
        assertEquals("Death", test.poll());
    }


}
