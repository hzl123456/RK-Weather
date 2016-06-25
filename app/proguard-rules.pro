-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
 
-keepclasseswithmembernames class * {
    native <methods>;
}
 
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
 
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
 
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
 
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# glide的代码混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#okhttputils和okhttp代码的混淆
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}

# gson混淆
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model. { *; }


# EventBus混淆
-keepclassmembers class * {
    public void onEvent*(**);
}
-keepclassmembers class * {
   public void onEventMainThread*(**);
}

# support不混淆
-dontwarn android.support.**
-keep public class * extends android.support.**
-keep class android.support.** { *; }

# 一些第三方包不混淆
-dontwarn org.apache.log4j.**
-keep class org.apache.log4j.**{*;}
-dontwarn org.apache.http.**
-keep class org.apache.http.**{*;}
-dontwarn android.net.http.**
-keep class android.net.http.**{*;}
-dontwarn com.j256.ormlite.**
-keep class com.j256.ormlite.**{*;}

