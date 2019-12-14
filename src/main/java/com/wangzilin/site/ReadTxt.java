package com.***REMOVED***.site;

import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
//@Controller
public class ReadTxt {
    public void read() throws Exception{
        String pathname = "C:\\Users\\78286\\Desktop\\input.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        while (line != null) {
            line = br.readLine(); // 一次读入一行数据
            System.out.println(line);
        }
    }
}
