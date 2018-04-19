package site.share2u.view.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * 操作文本的读写
 * @auther: CWM
 * @date: 2018/4/18.
 */
public class TxtUtil {
    /**
     * 根据路径写入string
     */
    public static void writeStr(String fileName,String str,Boolean boo){
        try {
            String path ="d:\\SOM\\"+fileName+".txt";
            File file = new File(path);
            if (!file.exists()){
                file.getParentFile().mkdir();
            }
            file.createNewFile();
            FileWriter fw = new FileWriter(path,boo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(str);
            bw.close();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
}
