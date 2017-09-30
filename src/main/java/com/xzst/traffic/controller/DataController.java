package com.xzst.traffic.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 张超 on 2017/9/29.
 */
@RestController
@RequestMapping(value="/data")
public class DataController {
    @ApiOperation(value="数据清洗", notes="将输入的inputUrl.xxx.csv转换成算法需要的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inputUrl", value = "数据输入地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "outputUrl", value = "数据输出地址", required = true, dataType = "String")
    })
    @RequestMapping(value="/dataClean", method=RequestMethod.POST)
    public String dataClean(@PathVariable String inputUrl,@PathVariable String outputUrl){

        return "success";
    }


}
