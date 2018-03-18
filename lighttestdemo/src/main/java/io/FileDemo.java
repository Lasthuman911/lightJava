package io;

import java.io.File;
import java.io.IOException;

/**
 * Created by lszhen on 2018/2/23.
 */
public class FileDemo {
    public static void main(String[] args) {
        createFile();
    }

    public static void createFile(){
        File newFile = new File("create.txt");
        try {
            if (!newFile.exists()){
                newFile.createNewFile();
            }
            System.out.println("分区大小:"+newFile.getTotalSpace()/(1024*1024*1024)+"G");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
