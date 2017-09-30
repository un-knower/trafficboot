import com.xzst.traffic.enums.Weather;
import com.xzst.traffic.enums.Week;
import com.xzst.traffic.model.InputModel;
import com.xzst.traffic.model.OutputModel;
import com.xzst.traffic.util.TimeUtil;
import com.csvreader.CsvReader;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by 张超 on 2017/9/22.
 */
public class DataClean {
    private static Logger log = Logger.getLogger(String.valueOf(DataClean.class));
    TimeUtil timeUtil=new TimeUtil();
    Properties prop = new Properties();

    public List<InputModel> csvReader(String inputUrl){
        int count=0;
       // String filePath = "E:\\input\\test.csv";
        String filePath=inputUrl;
        List<InputModel> list=new ArrayList<InputModel>();
        //读取路口路段字典
        Map map=readLkld();
        int size=0;
        try {
            // 创建CSV读对象
            InputStreamReader isr=new InputStreamReader(new FileInputStream(filePath),"GBK");
            CsvReader csvReader = new CsvReader(isr);
            // 读表头
            csvReader.readHeaders();
            csvReader.getHeader(0);
            while (csvReader.readRecord()){
                for(int k=0;k<7;k++){
                // 读一整行
                InputModel inputModel=new InputModel();
                //System.out.println(csvReader.getRawRecord());
//                String j=csvReader.get("经度");
//                inputModel.setLongitude(csvReader.get("经度"));
//                String h=csvReader.get("纬度");
//                inputModel.setLatitude(csvReader.get("纬度"));
                String lkld=csvReader.get("路口路段");
                Boolean b=map.containsKey(lkld);
                if(b){
                    inputModel.setLkld(Integer.parseInt(String.valueOf(map.get(lkld))));
                }else {
                    size=map.size();
                    ++size;
                    map.put(lkld,size);
                    writeLkld(lkld, String.valueOf(size));
                    inputModel.setLkld((Integer) map.get(lkld));
                }
                inputModel.setDate(csvReader.get("日期"));
                String str[]=csvReader.get("日期").split("\\.");
                inputModel.setDt(str[0]);
                inputModel.setMouth(str[1]);
                inputModel.setDay(str[2]);
                inputModel.setTimeRegion(timeUtil.getInitialTime(timeUtil.getMidTime(csvReader.get("时间段"))));
                for (Week week:Week.values()){
                    if(week.toString().equals(csvReader.get("星期"))){
                        inputModel.setWeek(week.ordinal()+1);
                        break;
                    }else {
                        inputModel.setWeek(100);

                    }
                }
                if("是".equals(csvReader.get("是否节假日"))){
                    inputModel.setIsHoliday(1);
                }else {
                    inputModel.setIsHoliday(0);
                }
                for(Weather weather1: Weather.values()){
                    if(weather1.toString().equals(csvReader.get("天气"))){
                        inputModel.setWeather(weather1.ordinal());
                        break;
                    }else {
                        inputModel.setWeather(100);

                    }
                }
                if("是".equals(csvReader.get("是否有事件发生"))){
                    inputModel.setIsEvent(1);
                }else {
                    inputModel.setIsEvent(0);
                }
                inputModel.setY1(csvReader.get("交通运行指数"));
                inputModel.setY2(csvReader.get("交通运行等级"));
               // inputModel.setNextNum(csvReader.get("nextNum"));
                // 读这行的某一列
                // System.out.println(csvReader.get("Link"));

                    inputModel.setNextNum(String.valueOf(k+1));
                    list.add(inputModel);
                }
                count++;
            }

            isr.close();
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }



    public List<OutputModel> DataConvert(List<InputModel> list){
       List<OutputModel> oList=new ArrayList<OutputModel>();
        for(int i=0;i<list.size();i++){
            OutputModel outputModel=new OutputModel();
//            outputModel.setLongitude(list.get(i).getLongitude());
//            outputModel.setLatitude(list.get(i).getLatitude());\
            outputModel.setLkld(list.get(i).getLkld());
            outputModel.setYear(list.get(i).getDt());
            outputModel.setMonth(list.get(i).getMouth());
            outputModel.setDay(list.get(i).getDay());
            outputModel.setNum(list.get(i).getTimeRegion());
            outputModel.setWeather(list.get(i).getWeather());
            int nextWeek= Integer.parseInt(list.get(i).getWeek()+list.get(i).getNextNum());
            while(nextWeek>7){
                nextWeek=nextWeek-7;
            }
            outputModel.setNextWeek(nextWeek);
            outputModel.setNextNum(Integer.parseInt(list.get(i).getNextNum()));
            //y1,y2
            String date=list.get(i).getDt()+"-"+list.get(i).getMouth()+"-"+list.get(i).getDay();
            String numDate=timeUtil.addDateDay(date,Integer.parseInt(list.get(i).getNextNum()));
            String str[]=numDate.split("-");
            str[0]=str[0].replaceFirst("^0*", "");
            str[1]=str[1].replaceFirst("^0*", "");
            str[2]=str[2].replaceFirst("^0*", "");
            for(int j=0;j<list.size();j++){
                if(list.get(j).getLkld()==list.get(i).getLkld()&&list.get(j).getNextNum().equals(list.get(i).getNextNum())&&list.get(j).getDt().equals(str[0])&&list.get(j).getMouth().equals(str[1])&&list.get(j).getDay().equals(str[2])&&list.get(j).getTimeRegion().equals(list.get(i).getTimeRegion())){
                    outputModel.setY1(list.get(j).getY1());
                    outputModel.setY2(list.get(j).getY2());
                    oList.add(outputModel);
                }
            }

        }
       return  oList;
    }

    public void csvWriter(List<OutputModel> list,String outputUrl){
        File csv= new File(outputUrl);
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(csv), "GBK"));
            bw.write("lkld,dt,month,day,num,tianqi,nextWeek,nextNum,y1,y2\n");
            for(int i=0;i<list.size();i++){
            bw.write(+list.get(i).getLkld()+","+list.get(i).getYear()+","+list.get(i).getMonth()+","+list.get(i).getDay()+","+list.get(i).getNum()+","+list.get(i).getWeather()+","+list.get(i).getNextWeek()+","+list.get(i).getNextNum()+","+list.get(i).getY1()+","+list.get(i).getY2()+"\n");
            }
            bw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public Map readLkld(){
        int size=0;
        Map map=new HashMap();
        try{
            //读取属性文件lkld.properties
            InputStream in = new BufferedInputStream (new FileInputStream("conf/lkld.properties"));
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
    private static void loadJNILibDynamically() {
        try {
            System.setProperty("java.library.path", System.getProperty("java.library.path")
                    + ":D:/Users/zcer/R-3.4.1/library/rJava/jri/x64");
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);

            System.loadLibrary("rJava");
        } catch (Exception e) {
            // do nothing for exception
        }
    }
    public static void main(String args[]){
        String inputUrl = null;
        String outputUrl = null;

        Properties prop = new Properties();
        try {
            InputStream inStream = new BufferedInputStream (new FileInputStream("conf/conf.properties"));
            prop.load(inStream);
            inStream.close();
            inputUrl=prop.getProperty("inputUrl").trim();
            outputUrl=prop.getProperty("outputUrl").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataClean dataClean=new DataClean();
        List<InputModel> list= dataClean.csvReader(inputUrl);
        List<OutputModel> olist=dataClean.DataConvert(list);
        for(int i=0;i<list.size();i++){
         //   System.out.println(list.get(i).toString());
            log.info("list="+list.get(i).toString());
        }
        dataClean.csvWriter(olist,outputUrl);

        loadJNILibDynamically();
        if (!Rengine.versionCheck()) {
            System.err.println("** Version mismatch - Java files don't match library version.");
            System.exit(1);
        }
        System.out.println("Creating Rengine (with arguments)");
        Rengine re=new Rengine(args, false, new TextConsole());
        System.out.println("Rengine created, waiting for R");
        if (!re.waitForR()) {
            System.out.println("Cannot load R");
            return;
        }

        REXP x;

    }

}
