package cn.xmrk.weather.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

import cn.xmrk.rkandroid.application.RKApplication;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.rkandroid.utils.StringUtil;
import cn.xmrk.weather.pojo.ChooseCityInfo;
import cn.xmrk.weather.pojo.CityInfo;
import cn.xmrk.weather.pojo.WeatherInfo;

/**
 * Created by Au61 on 2016/5/3.
 */
public class ChooseCityInfoDbHelper {

    private static final Logger log = Logger.getLogger("ChooseCityInfo");

    public String dbKey;

    public int msgOwner;

    OpenHelper mOpenHelper;

    public ChooseCityInfoDbHelper() {
        this("default", 1);
    }

    public ChooseCityInfoDbHelper(String dbKey, int msgOwner) {
        this(RKApplication.getInstance(), dbKey, msgOwner);
    }

    public ChooseCityInfoDbHelper(Context context, String dbKey, int msgOwner) {
        this.dbKey = dbKey;
        this.msgOwner = msgOwner;
        open(context);
    }

    public void open(Context context) {
        if (mOpenHelper == null || !mOpenHelper.isOpen()) {
            mOpenHelper = OpenHelper.getInstance(context, dbKey);
        }
    }

    public void close() {
        if (mOpenHelper != null) {
            mOpenHelper.close();
        }

    }

    public int getMsgOwner() {
        return msgOwner;
    }

    public Dao getChooseCityInfoDao() {
        return mOpenHelper.getChooseCityInfoDao();
    }


    /**
     * 保存消息到数据库,如果要用这个方法来更新数据,ChooseCityInfo对象里不修改的数据应该以原来的数据进行保存,否则会丢失
     *
     * @param info
     */
    public Dao.CreateOrUpdateStatus saveChooseCityInfo(ChooseCityInfo info) {
        Dao _dao = getChooseCityInfoDao();
        try {
            return _dao.createOrUpdate(info);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Dao.CreateOrUpdateStatus(false, false, 0);
        }
    }

    /**
     * 修改当前城市
     *
     * @param newInfo 新的当前城市
     */
    public void changeChooseCity(ChooseCityInfo newInfo) {
        Dao _dao = getChooseCityInfoDao();
        QueryBuilder<ChooseCityInfo, Integer> _qb = _dao.queryBuilder();
        Where<ChooseCityInfo, Integer> _where = _qb.where();
        try {
            _where.eq("isChooseCity", true);
            //查找出旧的当前城市，然后将其保存为非当前城市
            List<ChooseCityInfo> _result = _qb.query();
            if (_result != null && _result.size() > 0) {
                for (ChooseCityInfo info : _result) {
                    info.isChooseCity = false;
                    saveChooseCityInfo(info);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //将新的城市保存为当前城市
        newInfo.isChooseCity = true;
        saveChooseCityInfo(newInfo);
    }

    /**
     * 删除一条信息
     **/
    public void deleteOneChooseCityInfo(ChooseCityInfo info) {
        Dao _dao = getChooseCityInfoDao();
        DeleteBuilder<ChooseCityInfo, Integer> _db = _dao.deleteBuilder();
        Where<ChooseCityInfo, Integer> _where = _db.where();
        try {
            _where.eq("id", info.id);
            _db.delete();
        } catch (SQLException e) {
            log.error("数据删除失败", e);
        }
    }

    /**
     * 统计当前账号的信息的数量
     *
     * @return
     */
    public long getChooseCityInfoCount() {
        Dao _dao = getChooseCityInfoDao();
        try {
            return _dao.countOf();
        } catch (SQLException e) {
            log.error("获取失败", e);
            return 0;
        }
    }


    /**
     * 获取所有的城市信息,根据当前城市来排序，当前城市需要排在前面
     **/
    public List<ChooseCityInfo> getChooseCityInfoList() {
        Dao _dao = getChooseCityInfoDao();
        try {
            QueryBuilder<ChooseCityInfo, Integer> _qb = _dao.queryBuilder();
            //当前城市排在第一位
            _qb.orderBy("isChooseCity", false);
            List<ChooseCityInfo> _result = _qb.query();
            //如果查询结果数量不为0，需要对一些信息进行转换
            if (_result != null && _result.size() > 0) {
                for (ChooseCityInfo info : _result) {
                    if (!StringUtil.isEmptyString(info.cityString)) {
                        info.city = CommonUtil.getGson().fromJson(info.cityString, CityInfo.class);
                    }
                    if (!StringUtil.isEmptyString(info.weatherString)) {
                        info.mWeatherInfo = CommonUtil.getGson().fromJson(info.weatherString, WeatherInfo.class);
                    }
                }
            }
            return _result;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("获取失败", e);
            return null;
        }
    }

    /**
     * 判断是否含有该城市了
     **/
    public boolean checkHasLocationCity(String cityName) {
        Dao _dao = getChooseCityInfoDao();
        try {
            QueryBuilder<ChooseCityInfo, Integer> _qb = _dao.queryBuilder();
            Where<ChooseCityInfo, Integer> _where = _qb.where();
            _where.eq("cityName", cityName);
            return _qb.query().size() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("获取失败", e);
            return false;
        }
    }

}
