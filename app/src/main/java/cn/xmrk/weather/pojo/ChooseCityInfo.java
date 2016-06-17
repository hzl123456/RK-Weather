package cn.xmrk.weather.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Au61 on 2016/6/15.
 */
@DatabaseTable(tableName = "ChooseCity")
public class ChooseCityInfo implements Parcelable {

    /**
     * 自增长的id
     **/
    @DatabaseField(columnName = "id", generatedId = true)
    public int id;

    /**
     * 是否为当前城市
     **/
    @DatabaseField(columnName = "isChooseCity")
    public boolean isChooseCity;

    /**
     * 城市信息
     **/
    @DatabaseField(columnName = "cityString")
    public String cityString;
    public CityInfo city;

    /**
     * 保存上一次的天气信息
     **/
    @DatabaseField(columnName = "weatherString")
    public String weatherString;
    public WeatherInfo mWeatherInfo;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeByte(this.isChooseCity ? (byte) 1 : (byte) 0);
        dest.writeString(this.cityString);
        dest.writeParcelable(this.city, flags);
        dest.writeString(this.weatherString);
        dest.writeParcelable(this.mWeatherInfo, flags);
    }

    public ChooseCityInfo() {
    }

    protected ChooseCityInfo(Parcel in) {
        this.id = in.readInt();
        this.isChooseCity = in.readByte() != 0;
        this.cityString = in.readString();
        this.city = in.readParcelable(CityInfo.class.getClassLoader());
        this.weatherString = in.readString();
        this.mWeatherInfo = in.readParcelable(WeatherInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<ChooseCityInfo> CREATOR = new Parcelable.Creator<ChooseCityInfo>() {
        @Override
        public ChooseCityInfo createFromParcel(Parcel source) {
            return new ChooseCityInfo(source);
        }

        @Override
        public ChooseCityInfo[] newArray(int size) {
            return new ChooseCityInfo[size];
        }
    };
}
