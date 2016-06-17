package cn.xmrk.weather.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Au61 on 2016/6/16.
 */
public class WeatherInfo implements Parcelable {


    /**
     * city : 阿克塞
     * cityid : 641
     * citycode : 101160804
     * date : 2016-06-16
     * week : 星期四
     * weather : 晴
     * temp : 21
     * temphigh : 36
     * templow : 17
     * img : 0
     * humidity : 23
     * pressure : 876
     * windspeed : 7.0
     * winddirect : 东风
     * windpower : 2级
     * updatetime : 2016-06-16 10:50:09
     * index : [{"iname":"空调指数","ivalue":"部分时间开启","detail":"您将感到些燥热，建议您在适当的时候开启制冷空调来降低温度，以免中暑。"},{"iname":"运动指数","ivalue":"较适宜","detail":"天气较好，户外运动请注意防晒。推荐您进行室内运动。"},{"iname":"紫外线指数","ivalue":"很强","detail":"紫外线辐射极强，建议涂擦SPF20以上、PA++的防晒护肤品，尽量避免暴露于日光下。"},{"iname":"感冒指数","ivalue":"少发","detail":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"},{"iname":"洗车指数","ivalue":"较适宜","detail":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"iname":"空气污染扩散指数","index":"中","detail":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"},{"iname":"穿衣指数","ivalue":"炎热","detail":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"}]
     * aqi : {"so2":"16","so224":"7","no2":"60","no224":"30","co":"0.890","co24":"0.500","o3":"91","o38":"68","o324":"142","pm10":"139","pm1024":"74","pm2_5":"43","pm2_524":"38","iso2":"6","ino2":"30","ico":"10","io3":"29","io38":"34","ipm10":"95","ipm2_5":"60","aqi":"95","primarypollutant":"PM10","quality":"良","timepoint":"2016-06-16 10:00:00","aqiinfo":{"level":"二级","color":"#FFFF00","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响","measure":"极少数异常敏感人群应减少户外活动"}}
     * daily : [{"date":"2016-06-16","week":"星期四","sunrise":"06:15","sunset":"21:12","night":{"weather":"晴","templow":"17","img":"0","winddirect":"西风","windpower":"微风"},"day":{"weather":"晴","temphigh":"36","img":"0","winddirect":"东风","windpower":"微风"}},{"date":"2016-06-17","week":"星期五","sunrise":"06:15","sunset":"21:12","night":{"weather":"晴","templow":"21","img":"0","winddirect":"西风","windpower":"微风"},"day":{"weather":"晴","temphigh":"37","img":"0","winddirect":"东风","windpower":"3-4 级"}},{"date":"2016-06-18","week":"星期六","sunrise":"06:15","sunset":"21:12","night":{"weather":"多云","templow":"20","img":"1","winddirect":"西风","windpower":"微风"},"day":{"weather":"多云","temphigh":"35","img":"1","winddirect":"东风","windpower":"微风"}},{"date":"2016-06-19","week":"星期日","sunrise":"06:15","sunset":"21:12","night":{"weather":"多云","templow":"17","img":"1","winddirect":"西风","windpower":"微风"},"day":{"weather":"阴","temphigh":"32","img":"2","winddirect":"西风","windpower":"4-5 级"}},{"date":"2016-06-20","week":"星期一","sunrise":"06:16","sunset":"21:13","night":{"weather":"晴","templow":"18","img":"0","winddirect":"西风","windpower":"微风"},"day":{"weather":"晴","temphigh":"31","img":"0","winddirect":"东风","windpower":"微风"}},{"date":"2016-06-21","week":"星期二","sunrise":"07:30","sunset":"19:30","night":{"weather":"晴","templow":"17","img":"0","winddirect":"东北风","windpower":"微风"},"day":{"weather":"晴","temphigh":"31","img":"0","winddirect":"东北风","windpower":"微风"}},{"date":"2016-06-22","week":"星期三","sunrise":"07:30","sunset":"19:30","night":{"weather":"多云","templow":"16","img":"1","winddirect":"西北风","windpower":"微风"},"day":{"weather":"晴","temphigh":"31","img":"0","winddirect":"西北风","windpower":"微风"}}]
     * hourly : [{"time":"11:00","weather":"晴","temp":"23","img":"0"},{"time":"12:00","weather":"晴","temp":"26","img":"0"},{"time":"13:00","weather":"晴","temp":"29","img":"0"},{"time":"14:00","weather":"晴","temp":"32","img":"0"},{"time":"15:00","weather":"晴","temp":"33","img":"0"},{"time":"16:00","weather":"晴","temp":"34","img":"0"},{"time":"17:00","weather":"晴","temp":"33","img":"0"},{"time":"18:00","weather":"晴","temp":"33","img":"0"},{"time":"19:00","weather":"晴","temp":"33","img":"0"},{"time":"20:00","weather":"晴","temp":"32","img":"0"},{"time":"21:00","weather":"晴","temp":"31","img":"0"},{"time":"22:00","weather":"晴","temp":"29","img":"0"},{"time":"23:00","weather":"晴","temp":"26","img":"0"},{"time":"0:00","weather":"晴","temp":"25","img":"0"},{"time":"1:00","weather":"晴","temp":"24","img":"0"},{"time":"2:00","weather":"晴","temp":"22","img":"0"},{"time":"3:00","weather":"晴","temp":"22","img":"0"},{"time":"4:00","weather":"晴","temp":"21","img":"0"},{"time":"5:00","weather":"晴","temp":"20","img":"0"},{"time":"6:00","weather":"晴","temp":"20","img":"0"},{"time":"7:00","weather":"晴","temp":"23","img":"0"},{"time":"8:00","weather":"晴","temp":"22","img":"0"},{"time":"9:00","weather":"多云","temp":"23","img":"1"},{"time":"10:00","weather":"多云","temp":"29","img":"1"}]
     */

    private String city;
    private String cityid;
    private String citycode;
    private String date;
    private String week;
    private String weather;
    private String temp;
    private String temphigh;
    private String templow;
    private String img;
    private String humidity;
    private String pressure;
    private String windspeed;
    private String winddirect;
    private String windpower;
    private String updatetime;
    /**
     * so2 : 16
     * so224 : 7
     * no2 : 60
     * no224 : 30
     * co : 0.890
     * co24 : 0.500
     * o3 : 91
     * o38 : 68
     * o324 : 142
     * pm10 : 139
     * pm1024 : 74
     * pm2_5 : 43
     * pm2_524 : 38
     * iso2 : 6
     * ino2 : 30
     * ico : 10
     * io3 : 29
     * io38 : 34
     * ipm10 : 95
     * ipm2_5 : 60
     * aqi : 95
     * primarypollutant : PM10
     * quality : 良
     * timepoint : 2016-06-16 10:00:00
     * aqiinfo : {"level":"二级","color":"#FFFF00","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响","measure":"极少数异常敏感人群应减少户外活动"}
     */

    private AqiBean aqi;
    /**
     * iname : 空调指数
     * ivalue : 部分时间开启
     * detail : 您将感到些燥热，建议您在适当的时候开启制冷空调来降低温度，以免中暑。
     */

    private List<IndexBean> index;
    /**
     * date : 2016-06-16
     * week : 星期四
     * sunrise : 06:15
     * sunset : 21:12
     * night : {"weather":"晴","templow":"17","img":"0","winddirect":"西风","windpower":"微风"}
     * day : {"weather":"晴","temphigh":"36","img":"0","winddirect":"东风","windpower":"微风"}
     */

    private List<DailyBean> daily;
    /**
     * time : 11:00
     * weather : 晴
     * temp : 23
     * img : 0
     */

    private List<HourlyBean> hourly;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemphigh() {
        return temphigh;
    }

    public void setTemphigh(String temphigh) {
        this.temphigh = temphigh;
    }

    public String getTemplow() {
        return templow;
    }

    public void setTemplow(String templow) {
        this.templow = templow;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getWinddirect() {
        return winddirect;
    }

    public void setWinddirect(String winddirect) {
        this.winddirect = winddirect;
    }

    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public AqiBean getAqi() {
        return aqi;
    }

    public void setAqi(AqiBean aqi) {
        this.aqi = aqi;
    }

    public List<IndexBean> getIndex() {
        return index;
    }

    public void setIndex(List<IndexBean> index) {
        this.index = index;
    }

    public List<DailyBean> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyBean> daily) {
        this.daily = daily;
    }

    public List<HourlyBean> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyBean> hourly) {
        this.hourly = hourly;
    }

    public static class AqiBean implements Parcelable {

        private String so2;
        private String so224;
        private String no2;
        private String no224;
        private String co;
        private String co24;
        private String o3;
        private String o38;
        private String o324;
        private String pm10;
        private String pm1024;
        private String pm2_5;
        private String pm2_524;
        private String iso2;
        private String ino2;
        private String ico;
        private String io3;
        private String io38;
        private String ipm10;
        private String ipm2_5;
        private String aqi;
        private String primarypollutant;
        private String quality;
        private String timepoint;
        /**
         * level : 二级
         * color : #FFFF00
         * affect : 空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响
         * measure : 极少数异常敏感人群应减少户外活动
         */

        private AqiinfoBean aqiinfo;

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getSo224() {
            return so224;
        }

        public void setSo224(String so224) {
            this.so224 = so224;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getNo224() {
            return no224;
        }

        public void setNo224(String no224) {
            this.no224 = no224;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getCo24() {
            return co24;
        }

        public void setCo24(String co24) {
            this.co24 = co24;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getO38() {
            return o38;
        }

        public void setO38(String o38) {
            this.o38 = o38;
        }

        public String getO324() {
            return o324;
        }

        public void setO324(String o324) {
            this.o324 = o324;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm1024() {
            return pm1024;
        }

        public void setPm1024(String pm1024) {
            this.pm1024 = pm1024;
        }

        public String getPm2_5() {
            return pm2_5;
        }

        public void setPm2_5(String pm2_5) {
            this.pm2_5 = pm2_5;
        }

        public String getPm2_524() {
            return pm2_524;
        }

        public void setPm2_524(String pm2_524) {
            this.pm2_524 = pm2_524;
        }

        public String getIso2() {
            return iso2;
        }

        public void setIso2(String iso2) {
            this.iso2 = iso2;
        }

        public String getIno2() {
            return ino2;
        }

        public void setIno2(String ino2) {
            this.ino2 = ino2;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getIo3() {
            return io3;
        }

        public void setIo3(String io3) {
            this.io3 = io3;
        }

        public String getIo38() {
            return io38;
        }

        public void setIo38(String io38) {
            this.io38 = io38;
        }

        public String getIpm10() {
            return ipm10;
        }

        public void setIpm10(String ipm10) {
            this.ipm10 = ipm10;
        }

        public String getIpm2_5() {
            return ipm2_5;
        }

        public void setIpm2_5(String ipm2_5) {
            this.ipm2_5 = ipm2_5;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getPrimarypollutant() {
            return primarypollutant;
        }

        public void setPrimarypollutant(String primarypollutant) {
            this.primarypollutant = primarypollutant;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getTimepoint() {
            return timepoint;
        }

        public void setTimepoint(String timepoint) {
            this.timepoint = timepoint;
        }

        public AqiinfoBean getAqiinfo() {
            return aqiinfo;
        }

        public void setAqiinfo(AqiinfoBean aqiinfo) {
            this.aqiinfo = aqiinfo;
        }

        public static class AqiinfoBean implements Parcelable {

            private String level;
            private String color;
            private String affect;
            private String measure;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getAffect() {
                return affect;
            }

            public void setAffect(String affect) {
                this.affect = affect;
            }

            public String getMeasure() {
                return measure;
            }

            public void setMeasure(String measure) {
                this.measure = measure;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.level);
                dest.writeString(this.color);
                dest.writeString(this.affect);
                dest.writeString(this.measure);
            }

            public AqiinfoBean() {
            }

            protected AqiinfoBean(Parcel in) {
                this.level = in.readString();
                this.color = in.readString();
                this.affect = in.readString();
                this.measure = in.readString();
            }

            public static final Creator<AqiinfoBean> CREATOR = new Creator<AqiinfoBean>() {
                @Override
                public AqiinfoBean createFromParcel(Parcel source) {
                    return new AqiinfoBean(source);
                }

                @Override
                public AqiinfoBean[] newArray(int size) {
                    return new AqiinfoBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.so2);
            dest.writeString(this.so224);
            dest.writeString(this.no2);
            dest.writeString(this.no224);
            dest.writeString(this.co);
            dest.writeString(this.co24);
            dest.writeString(this.o3);
            dest.writeString(this.o38);
            dest.writeString(this.o324);
            dest.writeString(this.pm10);
            dest.writeString(this.pm1024);
            dest.writeString(this.pm2_5);
            dest.writeString(this.pm2_524);
            dest.writeString(this.iso2);
            dest.writeString(this.ino2);
            dest.writeString(this.ico);
            dest.writeString(this.io3);
            dest.writeString(this.io38);
            dest.writeString(this.ipm10);
            dest.writeString(this.ipm2_5);
            dest.writeString(this.aqi);
            dest.writeString(this.primarypollutant);
            dest.writeString(this.quality);
            dest.writeString(this.timepoint);
            dest.writeParcelable(this.aqiinfo, flags);
        }

        public AqiBean() {
        }

        protected AqiBean(Parcel in) {
            this.so2 = in.readString();
            this.so224 = in.readString();
            this.no2 = in.readString();
            this.no224 = in.readString();
            this.co = in.readString();
            this.co24 = in.readString();
            this.o3 = in.readString();
            this.o38 = in.readString();
            this.o324 = in.readString();
            this.pm10 = in.readString();
            this.pm1024 = in.readString();
            this.pm2_5 = in.readString();
            this.pm2_524 = in.readString();
            this.iso2 = in.readString();
            this.ino2 = in.readString();
            this.ico = in.readString();
            this.io3 = in.readString();
            this.io38 = in.readString();
            this.ipm10 = in.readString();
            this.ipm2_5 = in.readString();
            this.aqi = in.readString();
            this.primarypollutant = in.readString();
            this.quality = in.readString();
            this.timepoint = in.readString();
            this.aqiinfo = in.readParcelable(AqiinfoBean.class.getClassLoader());
        }

        public static final Creator<AqiBean> CREATOR = new Creator<AqiBean>() {
            @Override
            public AqiBean createFromParcel(Parcel source) {
                return new AqiBean(source);
            }

            @Override
            public AqiBean[] newArray(int size) {
                return new AqiBean[size];
            }
        };
    }

    public static class IndexBean implements Parcelable {
        private String iname;
        private String ivalue;
        private String index;
        private String detail;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getIname() {
            return iname;
        }

        public void setIname(String iname) {
            this.iname = iname;
        }

        public String getIvalue() {
            return ivalue;
        }

        public void setIvalue(String ivalue) {
            this.ivalue = ivalue;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.iname);
            dest.writeString(this.index);
            dest.writeString(this.ivalue);
            dest.writeString(this.detail);
        }

        public IndexBean() {
        }

        protected IndexBean(Parcel in) {
            this.index = in.readString();
            this.iname = in.readString();
            this.ivalue = in.readString();
            this.detail = in.readString();
        }

        public static final Creator<IndexBean> CREATOR = new Creator<IndexBean>() {
            @Override
            public IndexBean createFromParcel(Parcel source) {
                return new IndexBean(source);
            }

            @Override
            public IndexBean[] newArray(int size) {
                return new IndexBean[size];
            }
        };
    }

    public static class DailyBean implements Parcelable {
        private String date;
        private String week;
        private String sunrise;
        private String sunset;
        /**
         * weather : 晴
         * templow : 17
         * img : 0
         * winddirect : 西风
         * windpower : 微风
         */

        private NightBean night;
        /**
         * weather : 晴
         * temphigh : 36
         * img : 0
         * winddirect : 东风
         * windpower : 微风
         */

        private DayBean day;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public NightBean getNight() {
            return night;
        }

        public void setNight(NightBean night) {
            this.night = night;
        }

        public DayBean getDay() {
            return day;
        }

        public void setDay(DayBean day) {
            this.day = day;
        }

        public static class NightBean implements Parcelable {
            private String weather;
            private String templow;
            private String img;
            private String winddirect;
            private String windpower;

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTemplow() {
                return templow;
            }

            public void setTemplow(String templow) {
                this.templow = templow;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getWinddirect() {
                return winddirect;
            }

            public void setWinddirect(String winddirect) {
                this.winddirect = winddirect;
            }

            public String getWindpower() {
                return windpower;
            }

            public void setWindpower(String windpower) {
                this.windpower = windpower;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.weather);
                dest.writeString(this.templow);
                dest.writeString(this.img);
                dest.writeString(this.winddirect);
                dest.writeString(this.windpower);
            }

            public NightBean() {
            }

            protected NightBean(Parcel in) {
                this.weather = in.readString();
                this.templow = in.readString();
                this.img = in.readString();
                this.winddirect = in.readString();
                this.windpower = in.readString();
            }

            public static final Creator<NightBean> CREATOR = new Creator<NightBean>() {
                @Override
                public NightBean createFromParcel(Parcel source) {
                    return new NightBean(source);
                }

                @Override
                public NightBean[] newArray(int size) {
                    return new NightBean[size];
                }
            };
        }

        public static class DayBean implements Parcelable {
            private String weather;
            private String temphigh;
            private String img;
            private String winddirect;
            private String windpower;

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTemphigh() {
                return temphigh;
            }

            public void setTemphigh(String temphigh) {
                this.temphigh = temphigh;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getWinddirect() {
                return winddirect;
            }

            public void setWinddirect(String winddirect) {
                this.winddirect = winddirect;
            }

            public String getWindpower() {
                return windpower;
            }

            public void setWindpower(String windpower) {
                this.windpower = windpower;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.weather);
                dest.writeString(this.temphigh);
                dest.writeString(this.img);
                dest.writeString(this.winddirect);
                dest.writeString(this.windpower);
            }

            public DayBean() {
            }

            protected DayBean(Parcel in) {
                this.weather = in.readString();
                this.temphigh = in.readString();
                this.img = in.readString();
                this.winddirect = in.readString();
                this.windpower = in.readString();
            }

            public static final Creator<DayBean> CREATOR = new Creator<DayBean>() {
                @Override
                public DayBean createFromParcel(Parcel source) {
                    return new DayBean(source);
                }

                @Override
                public DayBean[] newArray(int size) {
                    return new DayBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.date);
            dest.writeString(this.week);
            dest.writeString(this.sunrise);
            dest.writeString(this.sunset);
            dest.writeParcelable(this.night, flags);
            dest.writeParcelable(this.day, flags);
        }

        public DailyBean() {
        }

        protected DailyBean(Parcel in) {
            this.date = in.readString();
            this.week = in.readString();
            this.sunrise = in.readString();
            this.sunset = in.readString();
            this.night = in.readParcelable(NightBean.class.getClassLoader());
            this.day = in.readParcelable(DayBean.class.getClassLoader());
        }

        public static final Creator<DailyBean> CREATOR = new Creator<DailyBean>() {
            @Override
            public DailyBean createFromParcel(Parcel source) {
                return new DailyBean(source);
            }

            @Override
            public DailyBean[] newArray(int size) {
                return new DailyBean[size];
            }
        };
    }

    public static class HourlyBean implements Parcelable {
        private String time;
        private String weather;
        private String temp;
        private String img;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.time);
            dest.writeString(this.weather);
            dest.writeString(this.temp);
            dest.writeString(this.img);
        }

        public HourlyBean() {
        }

        protected HourlyBean(Parcel in) {
            this.time = in.readString();
            this.weather = in.readString();
            this.temp = in.readString();
            this.img = in.readString();
        }

        public static final Creator<HourlyBean> CREATOR = new Creator<HourlyBean>() {
            @Override
            public HourlyBean createFromParcel(Parcel source) {
                return new HourlyBean(source);
            }

            @Override
            public HourlyBean[] newArray(int size) {
                return new HourlyBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.cityid);
        dest.writeString(this.citycode);
        dest.writeString(this.date);
        dest.writeString(this.week);
        dest.writeString(this.weather);
        dest.writeString(this.temp);
        dest.writeString(this.temphigh);
        dest.writeString(this.templow);
        dest.writeString(this.img);
        dest.writeString(this.humidity);
        dest.writeString(this.pressure);
        dest.writeString(this.windspeed);
        dest.writeString(this.winddirect);
        dest.writeString(this.windpower);
        dest.writeString(this.updatetime);
        dest.writeParcelable(this.aqi, flags);
        dest.writeList(this.index);
        dest.writeList(this.daily);
        dest.writeList(this.hourly);
    }

    public WeatherInfo() {
    }

    protected WeatherInfo(Parcel in) {
        this.city = in.readString();
        this.cityid = in.readString();
        this.citycode = in.readString();
        this.date = in.readString();
        this.week = in.readString();
        this.weather = in.readString();
        this.temp = in.readString();
        this.temphigh = in.readString();
        this.templow = in.readString();
        this.img = in.readString();
        this.humidity = in.readString();
        this.pressure = in.readString();
        this.windspeed = in.readString();
        this.winddirect = in.readString();
        this.windpower = in.readString();
        this.updatetime = in.readString();
        this.aqi = in.readParcelable(AqiBean.class.getClassLoader());
        this.index = new ArrayList<IndexBean>();
        in.readList(this.index, IndexBean.class.getClassLoader());
        this.daily = new ArrayList<DailyBean>();
        in.readList(this.daily, DailyBean.class.getClassLoader());
        this.hourly = new ArrayList<HourlyBean>();
        in.readList(this.hourly, HourlyBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<WeatherInfo> CREATOR = new Parcelable.Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel source) {
            return new WeatherInfo(source);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };
}
