package houtai.maps;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

import io.dcloud.EntryProxy;
import io.dcloud.feature.internal.sdk.SDK;

/* webview方式创建内核类 */
public class StartTwo extends Activity {
    EntryProxy mEntryProxy;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(mEntryProxy == null){
            FrameLayout rootView = new FrameLayout(this);
            // 创建5+内核运行事件监听
            initTwo wm = new initTwo(this,rootView);
            // 初始化5+内核
            mEntryProxy = EntryProxy.init(this,wm);
            // 启动5+内核，并指定内核启动类型
            mEntryProxy.onCreate(savedInstanceState, SDK.IntegratedMode.WEBVIEW,null);
            setContentView(rootView);
        }
    }
}
