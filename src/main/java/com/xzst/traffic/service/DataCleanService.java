package com.xzst.traffic.service;

import com.xzst.traffic.model.InputModel;
import com.xzst.traffic.model.OutputModel;

import java.util.List;
import java.util.Map;

/**
 * Created by 张超 on 2017/9/30.
 */
public interface DataCleanService {

    /**
     * 获取csv数据
     *
     * @param inputUrl
     * @return  List<InputModel>
     */
     List<InputModel> csvReader(String inputUrl);

    /**
     * 读取预测结果返回map
     * @param url
     * @return
     */
     Map resultReader(String url);
    /**
     * 数据转换
     *
     * @param list
     * @return  List<OutputModel>
     */
    List<OutputModel> DataConvert(List<InputModel> list);
    /**
     * 写入csv
     *
     * @param outputUrl
     * @return  List<OutputModel>
     */
    String csvWriter(List<OutputModel> list,String outputUrl);
    /**
     * 从HDFS下载数据到本地
     *
     * @param hdfsUrl,localUrl
     * @return  void
     */
    void copyFromHdfs(String hdfsUrl,String localUrl);
}
