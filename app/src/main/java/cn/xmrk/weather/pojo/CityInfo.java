package cn.xmrk.weather.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Au61 on 2016/6/15.
 */
public class CityInfo implements Parcelable {
    /**
     * 城市id
     **/
    public long city_id;
    /**
     * 城市名称
     **/
    public String city_child;

    /**
     * 城市英文名称，其实是拼音
     **/
    public String city_child_en;
    /**
     * 上一级名称
     **/
    public String city_parent;
    /**
     * 所属省份名称
     **/
    public String provcn;
    /**
     * 所属国家
     **/
    public String country;
    /**
     * 经纬度
     **/
    public long longitude;
    public long latitude;
    /**
     * 城市拼音名称缩写
     **/
    public String city_name_ab;
    /**
     * 城市拼音名称
     **/
    public String city_pinyin_name;


    public static Type getListType() {
        return new TypeToken<List<CityInfo>>() {
        }.getType();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.city_id);
        dest.writeString(this.city_child);
        dest.writeString(this.city_child_en);
        dest.writeString(this.city_parent);
        dest.writeString(this.provcn);
        dest.writeString(this.country);
        dest.writeLong(this.longitude);
        dest.writeLong(this.latitude);
        dest.writeString(this.city_name_ab);
        dest.writeString(this.city_pinyin_name);
    }

    public CityInfo() {
    }

    protected CityInfo(Parcel in) {
        this.city_id = in.readLong();
        this.city_child = in.readString();
        this.city_child_en = in.readString();
        this.city_parent = in.readString();
        this.provcn = in.readString();
        this.country = in.readString();
        this.longitude = in.readLong();
        this.latitude = in.readLong();
        this.city_name_ab = in.readString();
        this.city_pinyin_name = in.readString();
    }

    public static final Parcelable.Creator<CityInfo> CREATOR = new Parcelable.Creator<CityInfo>() {
        @Override
        public CityInfo createFromParcel(Parcel source) {
            return new CityInfo(source);
        }

        @Override
        public CityInfo[] newArray(int size) {
            return new CityInfo[size];
        }
    };
}
