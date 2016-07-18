package skkk.gogogo.com.dakaizhihu.utils;

import java.io.File;

/**
 * Created by admin on 2016/7/18.
 */
/*
* 
* 描    述：获取文件大小
* 作    者：ksheng
* 时    间：
*/
public class GetFileSize {
    public GetFileSize() {
    }
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
