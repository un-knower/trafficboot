package com.xzst.traffic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 张超 on 2017/9/25.
 */
public class TimeUtil {
    //取两个时间的中值
    public  String getMidTime(String time) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm");//如2016-08-10 20:40
        String str[]=time.split("-");
        String h1=str[0].split(":")[0];
        String m1=str[0].split(":")[1];
        String h2=str[1].split(":")[0];
        String m2=str[1].split(":")[1];
        String outTime=null;

        String fromDate = null;
        String toDate=null;
        try {
            fromDate = simpleFormat.format(simpleFormat.parse(str[0]));
             toDate = simpleFormat.format(simpleFormat.parse(str[1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long from = 0;
        int minutes = 0;
        try {
            from = simpleFormat.parse(fromDate).getTime();
            long to = simpleFormat.parse(toDate).getTime();
             minutes = (int) ((to - from)/(1000 * 60));
            System.out.println(minutes);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int h=Integer.parseInt(h1);
        int m=0;
        int halfMin=minutes/2;
        m=Integer.parseInt(m1)+minutes/2;
        while(m>=60){
            m=m-60;
            ++h;
        }

     outTime=h+":"+m;
        return outTime;
    }
    //时间取整 m<30向前取整  >30向后
    public   String getInitialTime(String time){
        String hour="00";//小时
        String minutes="00";//分钟
        String outTime="00:00";
        StringTokenizer st = new StringTokenizer(time, ":");
        List<String> inTime = new ArrayList<String>();
        while (st.hasMoreElements()) {
            inTime.add(st.nextToken());
        }
        hour=inTime.get(0).toString();
        minutes=inTime.get(1).toString();
        if(Integer.parseInt(minutes)>30){
            hour=(Integer.parseInt(hour)+1)+"";
        }
        outTime=hour+"";
        SimpleDateFormat sdf=new SimpleDateFormat("HH");

        try {
            outTime=sdf.format(sdf.parse(outTime));
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return outTime;
    }
    //指定时间加天数
    public  String addDateDay(String day, int x){
        //返回的是字符串型的时间，输入的
        //是String day, int x
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 24小时制
        //引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
        //量day格式一致
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, x);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return format.format(date);

    }
    public static void main(String[] args) {

    }
}
