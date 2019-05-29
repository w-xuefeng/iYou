package cn.com.wangxuefeng.iyou.util;

import java.io.*;

public class DownloadFile {

    /**
     * 1.使用缓存流拷贝文件
     * 2.记录拷贝过程用时
     */
    public static long DownloadFiles(DataInputStream dataInputStream, String savePath){
        long starTime = System.currentTimeMillis();
        BufferedInputStream bInStream = null; // 创建缓冲输入字节流
        OutputStream outStream = null; // 创建字节输出流
        BufferedOutputStream bOutStream = null; // 创建缓冲字节输出流
        try {
            bInStream = new BufferedInputStream(dataInputStream); // 用缓冲输入流装饰文件输入流
            outStream = new FileOutputStream(savePath); // 创建文件输出流，指向目标路径
            bOutStream = new BufferedOutputStream(outStream); // 用缓冲输出流来包装文件输出流

            int i = 0;
            while((i = bInStream.read()) != -1){
                bOutStream.write(i);    // 对接流，输出文件
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                bOutStream.close();
                bInStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        return endTime - starTime;
    }
}
