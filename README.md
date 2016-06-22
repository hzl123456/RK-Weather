# RK-Weather
   RK-Weather是一款查看天气的android应用软件，UI基本上参了一款叫做天气的软件，天气数据来自与网络，api是从百度api store上获取的。
具体的地址链接为：http://apistore.baidu.com/apiworks/servicedetail/2222.html

   基本功能添加管理城市，查看城市天气信息（包括每个时刻的信息），全球台风信息（https://earth.nullschool.net/，该网页app上显示会
存在一些问题），通知栏进行当前城市信息通知以及分享城市天气截图。

   该app主要用到的library如下,包含百度定位
   
    compile 'org.greenrobot:eventbus:3.0.0'//eventBus //进行事件的分发
    compile 'com.google.code.gson:gson:2.2.4' //gson解析jison
    compile 'cn.pedant.sweetalert:library:1.3'//dialog弹窗
    compile 'com.squareup.okhttp3:okhttp:3.3.1'//okhttp网络请求框架
    
    下面是一些程序截图：
