package biz.churen.self.jdk.simple;

import java.io.*;

/**
 * Created by Administrator on 2018/7/7/007.
 * Java 文件类的常见操作
 */
public class FileExample {

    private static void writeToFile() {
        String filePathName = "/D:/simple.txt";
        File f = new File(filePathName);
        try {
            // 如果文件不存在, 创建文件
            if (!f.exists()) {
                boolean createSuccess = f.createNewFile();
                if (!createSuccess) { throw new IOException("create new file failed!"); }
            }
            // 向文件中写入一些内容
            try (FileWriter fw = new FileWriter(f)) {
                fw.append("1, open a file by file name."); fw.append(System.lineSeparator());
                fw.append("2, create the file if not exists."); fw.append(System.lineSeparator());
                fw.append("3, write something to the file."); fw.append(System.lineSeparator());
                fw.append("4, flush() and close() the file."); fw.append(System.lineSeparator());
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFromFile() {
        String filePathName = "/D:/simple.txt";
        File f = new File(filePathName);
        // 如果文件存在, 按行读取并打印文件内容
        if (f.exists() && f.isFile() && f.canRead()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))){
                br.lines().forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        writeToFile();
        readFromFile();
    }
}
