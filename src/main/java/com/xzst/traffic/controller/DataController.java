package com.xzst.traffic.controller;

import com.xzst.traffic.model.InputModel;
import com.xzst.traffic.model.OutputModel;
import com.xzst.traffic.service.DataCleanService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inputUrl", value = "数据输入地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "outputUrl", value = "数据输出地址", required = true, dataType = "String")
    })
    @RequestMapping(value="/dataClean", method=RequestMethod.POST)
    public String dataClean(@PathVariable String inputUrl,@PathVariable String outputUrl){
       List<InputModel> inputModels= dataCleanService.csvReader(inputUrl);
       List<OutputModel> outputModels=dataCleanService.DataConvert(inputModels);
       String flag=dataCleanService.csvWriter(outputModels,outputUrl);
        return flag;
    }


}
