package com.emma.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/3/27.
 */
public class StringUtil {
    /**
     * @param is 流对象
     * @return 转换成字符串 null代表异常
     */
    public static String streamToString(InputStream is) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int temp = -1;
        try {
            while ((temp = is.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
