package cn.su.core.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @AUTHOR: sr
 * @DATE: Create In 0:32 2021/1/18 0018
 * @Description: 文件工具类
 */
public class FileUtil {
    /**
     * 创建写入文件
     *
     * @param txtContent 文件内容
     * @param path       路径
     * @param fileName   文件名，包括后缀名
     * @return 是否成功
     * @throws IOException
     */
    public static boolean createAndWriteTxtFile(String txtContent, String path, String fileName, String fileSuffix) {
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(path + fileName + fileSuffix);
            if (file.exists()) {
                file.delete();
            }

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(txtContent.toCharArray());
            pw.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr   url
     * @param fileName 文件名
     * @param savePath 保存路径
     * @throws IOException
     */
    public static void downLoadByUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");
    }
}
