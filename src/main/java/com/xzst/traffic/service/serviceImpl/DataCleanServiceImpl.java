package com.xzst.traffic.service.serviceImpl;

import com.csvreader.CsvReader;
import com.xzst.traffic.enums.Weather;
import com.xzst.traffic.enums.Week;
import com.xzst.traffic.model.InputModel;
import com.xzst.traffic.model.OutputModel;
import com.xzst.traffic.service.DataCleanService;
import com.xzst.traffic.util.ConfUtil;
import com.xzst.traffic.util.TimeUtil;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 张超 on 2017/9/30.
 */
@Service
public class DataCleanServiceImpl implements DataCleanService {
    private Logger log = LoggerFactory.getLogger(DataCleanServiceImpl.class);
    TimeUtil timeUtil=new TimeUtil();
    @Override
    public List<InputModel> csvReader(String inputUrl) {
        int count=0;
        // String filePath = "E:\\input\\test.csv";
        String filePath=inputUrl;
        List<InputModel> list=new ArrayList<InputModel>();
        //读取路口路段字典
        ConfUtil confUtil=new ConfUtil();
        Map map= confUtil.readLkld();
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
                    String lkld=csvReader.get("路口路段");
                    Boolean b=map.containsKey(lkld);
                    if(b){
                        inputModel.setLkld(Integer.parseInt(String.valueOf(map.get(lkld))));
                    }else {
                        size=map.size();
                        ++size;
                        map.put(lkld,size);
                        confUtil.writeLkld(lkld, String.valueOf(size));
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

    @Override
    public List<OutputModel> DataConvert(List<InputModel> list) {
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

    @Override
    public String csvWriter(List<OutputModel> list, String outputUrl) {
        File csv= new File(outputUrl);
        String flag="false";
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(csv), "GBK"));
            bw.write("lkld,dt,month,day,num,tianqi,nextWeek,nextNum,y1,y2\n");
            for(int i=0;i<list.size();i++){
                bw.write(+list.get(i).getLkld()+","+list.get(i).getYear()+","+list.get(i).getMonth()+","+list.get(i).getDay()+","+list.get(i).getNum()+","+list.get(i).getWeather()+","+list.get(i).getNextWeek()+","+list.get(i).getNextNum()+","+list.get(i).getY1()+","+list.get(i).getY2()+"\n");
            }
            bw.close();
            flag="success";

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public void copyFromHdfs(String hdfsUrl, String localUrl) {
        try {
            Configuration conf = new Configuration();
            Path srcPath= new Path(hdfsUrl); // hdfs路径
            FileSystem fs = srcPath.getFileSystem(conf);
            fs.listFiles(srcPath,false);
            Path dstPath = new Path(localUrl); // 本地存储文件路径
            fs.copyToLocalFile(srcPath,dstPath);
            InputStream in = fs.open(srcPath);
            OutputStream out = new FileOutputStream("E://Demo");
            IOUtils.copyBytes(in, out, 4096, true);
        } catch (IOException e) {
            e.printStackTrace();

        }



    }
}
