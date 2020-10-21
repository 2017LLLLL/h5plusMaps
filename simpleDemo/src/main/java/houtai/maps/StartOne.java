package houtai.maps;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.dcloud.EntryProxy;
import io.dcloud.feature.internal.sdk.SDK;

/* widget方式创建内核类 */
public class StartOne extends Activity {
    EntryProxy mEntryProxy = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("startone 执行!");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (mEntryProxy == null) {
            FrameLayout f = new FrameLayout(this);
            // 创建5+内核运行事件监听
            init wm = new init(this, f);
            // 初始化5+内核
            mEntryProxy = EntryProxy.init(this, wm);
            // 启动5+内核
            mEntryProxy.onCreate(this, savedInstanceState, SDK.IntegratedMode.WEBAPP, null);
            setContentView(f);
        }
    }
}
