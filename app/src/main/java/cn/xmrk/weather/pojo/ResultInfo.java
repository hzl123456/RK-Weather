package cn.xmrk.weather.pojo;

import com.google.gson.JsonElement;

/**
 * Created by Au61 on 2016/6/16.
 */
public class ResultInfo {

    public int status;//结果码 0表示成功

    public String msg;//结果的语句

    public JsonElement result;//数据结果，有可能为空或者空字符串

}
