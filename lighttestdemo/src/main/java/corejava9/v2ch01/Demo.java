package corejava9.v2ch01;

import javafx.scene.control.Separator;

import java.io.File;

/**
 * Created by lszhen on 2018/2/23.
 */
public class Demo {
    public static void main(String[] args) {
        //获取工作根目录
        String rootPath = System.getProperty("user.dir");
        System.out.println(rootPath);

        //跨语言的分割符
        System.out.println(File.separator);


    }
}
