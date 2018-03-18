package normal.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 创建文件
 * @author lszhen
 * Created on 2017/11/30.
 */
public class FileTest {
    private static final Logger log = LoggerFactory.getLogger(FileTest.class);
    public static void main(String[] args) throws IOException {
//        createFile();
//        deleteFile();
//        createDir();
//        listAllFiles();
//        listAllFilesWithFullName();
//        isDirOrFile();
//        printAllFilesInDir();
//        writeToFile();
//        readFromFile();

//       writeToFileWithOutPutStream();
        //字节流读取
//        readFromFile2();
//        readFromFile3();
//        readFromFile4();
        //最常用
//        readFromFile5();
//        readFile6();

        //字符流写入
//        writeToFileWithWriter();
        //字符流读取
//        readWithWriter();

//        readWithWriter2();
//        readWithWriter3();

        //文件复制---核心代码
//        fileCopy("hello.text","copy.txt");


    }

    private static void fileCopy(String fromFile,String toFile) throws IOException {
        File fromFile1 = new File(fromFile);
        File toFile1 = new File(toFile);
        try (InputStream in = new FileInputStream(fromFile1)) {
            try (OutputStream out = new FileOutputStream(toFile1)) {
                int temp = 0;
                while ((temp = in.read()) != -1) {
                    out.write(temp);
                }
                in.close();
                out.close();
            }
        }
    }

    private static void readWithWriter3() throws IOException {
        File file = new File("hello.txt");
        char[] chars = new char[2];
        String str = "";
        try (Reader reader = new FileReader(file)) {
            int temp = 0;
            while ((temp = reader.read(chars)) != -1) {
                str +=String.valueOf(chars);
            }
        }
        System.out.println(str);
    }

    private static void readWithWriter2() throws IOException {
        File file = new File("hello.txt");
        char[] chars;
        int count;
        try (Reader reader = new FileReader(file)) {
            chars = new char[1024];
            int temp = 0;
            count = 0;
            while ((temp = reader.read()) != -1) {
                chars[count++] = (char) temp;
            }
        }
        System.out.println(new String(chars,0,count));
    }

    private static void readWithWriter() throws IOException {
        File file = new File("hello.txt");
        char[] chars;
        int count = 0;
        try (Reader reader = new FileReader(file)) {
            chars = new char[(int) file.length()];
            count =  reader.read(chars);
            reader.close();
        }
        System.out.println(new String(chars,0,count));
    }

    private static void writeToFileWithWriter() throws IOException {
        File file = new File("hello.txt");
        try (Writer out = new FileWriter(file, true)) {
            out.write(" write 追加");
            out.close();
        }
    }

    private static void readFile6() throws IOException {
        File file = new File("hello.txt");
        InputStream in = null;
        try{
            in = new FileInputStream(file);
            int temp = 0;
            byte[] b = new byte[2];
            //每次读入两个字节
            while ((temp = in.read(b)) != (-1)){
                System.out.write(b,0,temp);
            }
        }catch (IOException e){

        }finally {
            in.close();
        }
    }

    private static void readFromFile5() throws IOException {
        File file = new File("hello.txt");
        InputStream in = null;
        try{
            in = new FileInputStream(file);
            //available 获取文件的总大小
            byte[] b = new byte[in.available()];
            in.read(b);
            System.out.println(new String(b));
        }catch (IOException e){

        }finally {
            in.close();
        }
    }

    /**
     * inputSteam 从file读取信息
     * byte一个一个读
     * @throws IOException
     */
    private static void readFromFile4() throws IOException {
        File file = new File("hello.txt");
        InputStream in = null;
        try{
            in = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            for (int i =0;i<b.length;i++){
                b[i] = (byte) in.read();
            }
            System.out.println(new String(b));
        }catch (IOException e){

        }finally {
            in.close();
        }
    }

    /**
     * nputSteam 从file读取信息
     * 不指定byte大小
     * @throws IOException
     */
    private static void readFromFile3() throws IOException {
        File file = new File("hello.txt");
        InputStream in = null;
        try{
            in = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            in.read(b);
            System.out.println(new String(b));
        }catch (IOException e){

        }finally {
            in.close();
        }
    }

    /**
     * InputSteam 从file读取信息
     * @throws IOException
     */
    private static void readFromFile2() throws IOException {
        File file = new File("hello.txt");
        InputStream in = null;
        try{
            in = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = in.read(bytes);
            System.out.println(new String(bytes,0,len));
        }catch (Exception e){

        }finally {
            in.close();
        }
    }

    /**
     * 字节流
     * 向文件中写入字符串
     * @throws IOException
     */
    private static void writeToFileWithOutPutStream() throws IOException {
        File file = new File("hello.txt");
        OutputStream out = null;
        try{
            //会覆盖file中原有内容,加true是在文件末尾追加内容
//           out  = new FileOutputStream(file);
            out  = new FileOutputStream(file,true);
            //换行
           String str = "\r\n你好，ls23";
           byte[] bytes = str.getBytes();
           out.write(bytes);
        }finally {
            out.close();
        }
    }

    /**
     * RandomAccessFile 从file中读取信息
     * RandomAccessFile类操作起来比较麻烦，需要按照插入顺序一个一个进行读取
     * 所以在IO中会提供专门的输入输出操作。
     * @throws IOException
     */
    private static void readFromFile() throws IOException {
        File file = new File("hello.txt");
        RandomAccessFile demo =null;
        try{
            //以只读的方式打开文件
            demo = new RandomAccessFile(file,"r");

            byte[] b = new byte[4];
            for (int i = 0;i<b.length;i++){
                b[i] =  demo.readByte();
            }
            String name = new String(b);
            System.out.println("name="+name);
            int age = demo.readInt();
            System.out.println("age = "+ age);
        }catch (Exception e){

        }finally {
            demo.close();
        }
    }

    /**
     * RandomAccessFile 写入信息到file中
     * @throws IOException
     */
    private static void writeToFile() throws IOException {
        File file = new File("hello.txt");
        RandomAccessFile demo = null;
        try{
            //读写模式，如果文件不存在，会自动创建
            demo = new RandomAccessFile(file,"rw");
            demo.writeBytes("abcd");
            demo.writeInt(123);
            demo.writeChar(33);
            demo.writeDouble(3.22);
            demo.writeLong(123232);
            demo.writeFloat(33.2f);
        }catch (Exception e){

        }finally {
            demo.close();
        }
    }

    /**
     * 输出目录下的所有文件，包括所有子目录下的
     */
    private static void printAllFilesInDir() {
        String fileName = "."+ File.separator;
        File file = new File(fileName);
        print(file);
    }

    private static void print(File file) {
        if (file != null){
           if (file.isDirectory()){
               File[] fileArray = file.listFiles();
               for (int i = 0;i<fileArray.length;i++){
                   //递归输出
                   print(fileArray[i]);
               }
           }else {
               System.out.println(file);
           }
        }
    }

    /**
     * 判断一个目录是否是目录或者文件
     */
    private static void isDirOrFile() {
        String fileName = "."+ File.separator;
        File file = new File(fileName);
        if (file.isDirectory()){
            System.out.println("is dir");
        }
        if (file.isFile()){
            System.out.println("is file");
        }
    }

    /**
     * 输出的完整路径的文件
     */
    private static void listAllFilesWithFullName() {
        String fileName = "."+ File.separator;
        File file = new File(fileName);
        File[] list = file.listFiles();
        for (int i = 0;i<list.length;i++){
            System.out.println(list[i]);
        }
    }

    /**
     * 列出目录下所有的文件，包括隐藏文件
     */
    private static void listAllFiles() {
        String fileName = "."+ File.separator;
        File file = new File(fileName);
        String[] list = file.list();
        for (int i = 0;i<list.length;i++){
            System.out.println(list[i]);
        }
    }

    /**
     * 创建文件夹
     */
    private static void createDir() {
        String fileName = "hellodir";
        File file = new File(fileName);
        Boolean direxist = file.mkdir();
        if (direxist){
            log.info("success create dir");
        }else {
            log.error("failed to create dir");
        }
    }

    /**
     * 删除文件
     */
    private static void deleteFile() {
        File file = new File("hello.txt");
        if (file.exists()){
            //删除文件
            file.delete();
            log.info("success");
        }else {
            log.warn("file not exist");
        }
    }

    /**
     * 创建文件
     */
    private static void createFile() {
        //会在项目根目录下创建hello.txt文件
        File file = new File("hello.txt");
        try {
            //创建一个文件
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
