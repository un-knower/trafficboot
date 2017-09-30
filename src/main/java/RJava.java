import com.sun.org.apache.regexp.internal.RE;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 张超 on 2017/9/25.
 */
//-Djava.library.path="D:\Users\zcer\R-3.4.1\library\rJava\jri\x64"
public class RJava {
    public static void main(String[] args) {
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
        lm(re);

    }
         public static REXP nnet(Rengine re){
             //神经网络
             System.out.println(re.eval("wudata=read.table(\"E:/wri.csv\",header=TRUE,sep=\",\",quote=\"\\\"\")"));
             System.out.println(re.eval("library(\"nnet\")"));
             System.out.println(re.eval("wuhuMl=nnet(formula=y1~ lkld +dt +month + day +num +tianqi+nextWeek+ nextNum,data= wudata,size=90)"));
             // generic vectors are RVector to accomodate names
             System.out.println(re.eval("predict(wuhuM1,wudata[,0:9])"));
             System.out.println(re.eval("write.table (predict(wuhuMl,wudata[,0:9]), file =\"E://nnet.csv\", sep =\" \", row.names =FALSE, col.names =TRUE, quote =TRUE)"));

             return null;
         }
         public static REXP randomForest(Rengine re){
             //随机森林
             System.out.println(re.eval("wudata=read.table(\"E:/wri.csv\",header=TRUE,sep=\",\",quote=\"\\\"\")"));
             System.out.println(re.eval("library(\"randomForest\")"));
             System.out.println(re.eval(" wuhuM2=randomForest(formula=y1~ lkld +dt +month + day +num +tianqi+nextWeek+ nextNum,data= wudata,size=90)"));
             System.out.println(re.eval("predict(wuhuM2,wudata[,0:9])"));
             System.out.println(re.eval("write.table (predict(wuhuMl,wudata[,0:9]), file =\"E://randomForest.csv\", sep =\" \", row.names =FALSE, col.names =TRUE, quote =TRUE)"));
             //XGBoost（弃）
            return null;
         }
         public static REXP lm(Rengine re){
             //线性回归lm
             re.eval("wudata=read.table(\"E:/wri.csv\",header=TRUE,sep=\",\",quote=\"\\\"\")");
             re.eval("library(\"nnet\")");
             re.eval("wuhuM1=lm(formula=y1~ lkld +dt +month + day +num +tianqi+nextWeek+ nextNum,data= wudata)");
             REXP x=re.eval("predict(wuhuM1,wudata[,0:9])");
             re.eval("write.table (predict(wuhuMl,wudata[,0:9]), file =\"E://ls.csv\", sep =\" \", row.names =FALSE, col.names =TRUE, quote =TRUE)");
             return x;
         }
}
