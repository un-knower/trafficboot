package com.xzst.traffic.util;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 张超 on 2017/9/30.
 */
public class ConfUtil {
    Properties prop = new Properties();
    public void writeLkld(String key,String value){
        prop.setProperty(key, value);
        FileOutputStream oFile = null;
        try {
            //保存属性到b.properties文件
            oFile = new FileOutputStream("conf/lkld.properties", false);//true表示追加打开,false每次都是清空再重写
            //prop.store(oFile, "此参数是保存生成properties文件中第一行的注释说明文字");//这个会两个地方乱码
            //prop.store(new OutputStreamWriter(oFile, "utf-8"), "汉字乱码");//这个就是生成的properties文件中第一行的注释文字乱码
            prop.store(new OutputStreamWriter(oFile, "GBK"), "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (oFile != null) {
                try {
                    oFile.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public Map readLkld(){
        int size=0;
        Map map=new HashMap();
        try{
            //读取属性文件lkld.properties
            InputStream in = new BufferedInputStream(new FileInputStream("conf/lkld.properties"));
            prop.load(new InputStreamReader(in,"GBK"));     ///加载属性列表
            size=prop.size();
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                map.put(key, prop.getProperty(key));
            }
            in.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return map;
    }
}
