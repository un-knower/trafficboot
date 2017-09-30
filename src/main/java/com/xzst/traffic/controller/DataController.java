package com.xzst.traffic.controller;

import com.xzst.traffic.model.InputModel;
import com.xzst.traffic.model.OutputModel;
import com.xzst.traffic.model.Singleton;
import com.xzst.traffic.service.DataCleanService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 张超 on 2017/9/29.
 */
@RestController
@RequestMapping(value="/data")
public class DataController {
    private Logger log = LoggerFactory.getLogger(DataController.class);
    @Resource
    private DataCleanService dataCleanService;
    @ApiOperation(value="数据清洗", notes="将输入的inputUrl.xxx.csv转换成算法需要的数据")
    @RequestMapping(value="/dataClean", method=RequestMethod.POST)
    @ResponseBody
    public String dataClean(@RequestParam @ApiParam(required = true, value = "inputUrl") String inputUrl, @RequestParam @ApiParam(required = true, value = "outputUrl") String outputUrl){
       List<InputModel> inputModels= dataCleanService.csvReader(inputUrl);
       List<OutputModel> outputModels=dataCleanService.DataConvert(inputModels);
       String flag=dataCleanService.csvWriter(outputModels,outputUrl);
        return flag;
    }
    @ApiOperation(value="训练", notes="用输入的inputUrl.xxx.csv训练算法")
    @RequestMapping(value="/dataTrain", method=RequestMethod.POST)
    @ResponseBody
    public String dataTrain(@RequestParam @ApiParam(required = true, value = "inputUrl") String inputUrl, @RequestParam @ApiParam(required = true, value = "outputUrl") String outputUrl){
       // Rengine re = new Rengine(new String[] { "--vanilla" }, false, null);
      //  Rengine re=new Rengine();
        Rengine re=Singleton.getSingleton();

        if (!re.waitForR()) {
            System.out.println("Cannot load R");
            return "R error";
        }
        //打印变量
        String version = re.eval("R.version.string").asString();
        System.out.println(version);
        re.eval("wudata=read.table(\"E:/wri.csv\",header=TRUE,sep=\",\",quote=\"\\\"\")");
        //re.eval("wudata=read.table(\""+inputUrl+"\",header=TRUE,sep=\",\",quote=\"\\\"\")");
        re.eval("library(\"nnet\")");
        re.eval("wuhuM1=lm(formula=y1~ lkld +dt +month + day +num +tianqi+nextWeek+ nextNum,data= wudata)");
        System.out.println(re.eval("predict(wuhuM1,wudata[,0:9])"));
        re.eval("write.table (predict(wuhuM1,wudata[,0:9]), file =\"E://ls.csv\", sep =\" \", row.names =FALSE, col.names =TRUE, quote =TRUE)");
        //re.eval("write.table(predict(wuhuM1,wudata[,0:9]), file =\""+outputUrl+"\", sep =\" \", row.names =FALSE, col.names =TRUE, quote =TRUE)");

        return "success";
    }

}
