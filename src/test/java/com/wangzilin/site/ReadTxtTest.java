package com.***REMOVED***.site;

import org.junit.Before;

import org.junit.Test;


public class ReadTxtTest {
    private ReadTxt readTxt = null;
    @Before
    public void setup(){
        readTxt = new ReadTxt();
    }
    @Test
    public void testRead() throws Exception{
        readTxt.read();
    }
}
