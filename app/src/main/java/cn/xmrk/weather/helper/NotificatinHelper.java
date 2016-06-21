package cn.xmrk.weather.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.widget.RemoteViews;

import cn.xmrk.rkandroid.application.RKApplication;
import cn.xmrk.weather.R;
import cn.xmrk.weather.application.WeatherApplication;
import cn.xmrk.weather.pojo.ChooseCityInfo;
import cn.xmrk.weather.pojo.WeatherInfo;

/**
 * Created by Au61 on 2016/6/21.
 */
public class NotificatinHelper {

    private static final int NOTIFICATION_ID = 100;

    /**
     * 是否已经有通知栏了
     **/
    private static boolean hasNotification;

    /**
     * 进行通知，不需要进行activity的跳转
     **/
    public static void showNotification(ChooseCityInfo cityInfo) {
        final WeatherInfo ntInfo = cityInfo.mWeatherInfo;
        //当前城市而且发送通知的才有通知
        if (cityInfo.isChooseCity && SettingHelper.getInstance().getNotice()&&ntInfo!=null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RKApplication appliction = WeatherApplication.getInstance();
                    //自定义通知栏的布局，并且设置信息
                    RemoteViews remoteViews = new RemoteViews(appliction.getPackageName(), R.layout.layout_notification);
                    remoteViews.setTextViewText(R.id.tv_t_show, ntInfo.getTemp() + "°");
                    remoteViews.setTextViewText(R.id.tv_city, ntInfo.getCity());
                    remoteViews.setTextViewText(R.id.tv_weather, ntInfo.getWeather());
                    remoteViews.setTextViewText(R.id.tv_time, ntInfo.getUpdatetime().split(" ")[1] + "更新");

                    NotificationManager notificationManager = (NotificationManager) appliction.getSystemService(Context.NOTIFICATION_SERVICE);
                    //创建通知
                    Notification.Builder mBuilder = new Notification.Builder(appliction)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContent(remoteViews)
                            .setAutoCancel(true);
                    if (!hasNotification) {
                        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                        hasNotification = true;
                    }
                    notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                }
            }).start();
        }
    }
}
