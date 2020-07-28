package com.sce.demo.file;

import java.io.*;
import java.util.HashMap;

public class FileHandler {
    File file;
    BufferedReader bf;
    String line;
    private int buffer = 16384;

    public HashMap<String, Integer> readAllWords(String path){
        HashMap<String, Integer> words = new HashMap<>();
        int i;
        try{
            file = new File(path);
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"8859_1"),buffer);
            i=0;
            while((line = bf.readLine())!=null){
                words.put(line,i);
                i++;
            }
        }
        catch (IOException ioex){
            ioex.printStackTrace();
        }

    return words;
    }

}
