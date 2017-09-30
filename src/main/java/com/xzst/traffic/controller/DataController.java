package com.xzst.traffic.controller;

import com.xzst.traffic.model.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by 张超 on 2017/9/29.
 */
@RestController
@RequestMapping(value="/data")
public class DataController {
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }
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
