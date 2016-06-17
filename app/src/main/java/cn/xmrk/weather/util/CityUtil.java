package cn.xmrk.weather.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.rkandroid.utils.StringUtil;
import cn.xmrk.weather.application.WeatherApplication;
import cn.xmrk.weather.pojo.CityInfo;

/**
 * Created by Au61 on 2016/6/15.
 * <p/>
 * 操作处理数据库中的城市信息
 */
public class CityUtil {

    /**
     * 使用的城市信息
     **/
    public List<CityInfo> cityInfos;

    private CityUtil() {

    }

    private static CityUtil mCityUtil;

    public static CityUtil getInstance() {
        if (mCityUtil == null) {
            mCityUtil = new CityUtil();
            mCityUtil.loadCityInfos(mCityUtil.loadDbFile());
        }
        return mCityUtil;
    }

    /**
     * 读取数据库文件
     **/
    private SQLiteDatabase loadDbFile() {
        File dbFile = new File(CommonUtil.getDir() + File.separator + "city.db");
        if (!dbFile.exists()) {
            InputStream inputStream = null;
            FileOutputStream fos = null;
            try {
                //得到数据库文件的输入流
                inputStream = WeatherApplication.getInstance().getAssets().open("city.db3");
                fos = new FileOutputStream(dbFile.getAbsoluteFile());
                //创建byte数组  用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    //关闭流
                    if (fos != null) {
                        fos.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return SQLiteDatabase.openOrCreateDatabase(dbFile.getAbsoluteFile(), null);
    }

    /**
     * 获取数据文件并且转化数据
     **/
    private void loadCityInfos(SQLiteDatabase db) {
        cityInfos = new ArrayList<>();
        CityInfo info = null;
        Cursor cursor = db.rawQuery("select * from city where country=? order by city_child_en", new String[]{"中国"});
        while (cursor.moveToNext()) {
            info = new CityInfo();
            info.city_id = cursor.getLong(cursor.getColumnIndex("city_id"));
            info.city_child_en = cursor.getString(cursor.getColumnIndex("city_child_en"));
            info.city_child = cursor.getString(cursor.getColumnIndex("city_child"));
            info.city_parent = cursor.getString(cursor.getColumnIndex("city_parent"));
            info.provcn = cursor.getString(cursor.getColumnIndex("provcn"));
            info.country = cursor.getString(cursor.getColumnIndex("country"));
            info.longitude = cursor.getLong(cursor.getColumnIndex("longitude"));
            info.latitude = cursor.getLong(cursor.getColumnIndex("latitude"));
            info.city_name_ab = cursor.getString(cursor.getColumnIndex("city_name_ab"));
            info.city_pinyin_name = cursor.getString(cursor.getColumnIndex("city_pinyin_name"));
            cityInfos.add(info);
        }
        cursor.close();
        Log.i("city-->", cityInfos.size() + "");
    }


    /**
     * 根据输入信息获取城市列表
     **/
    public List<CityInfo> getChooseCityInfo(String inputStr) {
        if (StringUtil.isEmptyString(inputStr)) {
            return cityInfos;
        }
        List<CityInfo> infos = new ArrayList<>();
        CityInfo info = null;
        for (int i = 0; i < cityInfos.size(); i++) {
            info = cityInfos.get(i);
            //判断中文或者拼音是否匹配，区分大小写
            if (info.city_child_en.contains(inputStr) || info.city_child.contains(inputStr) || info.city_parent.contains(inputStr) || info.provcn.contains(inputStr) || info.city_pinyin_name.contains(inputStr)) {
                infos.add(info);
            }
        }
        return infos;
    }

}
