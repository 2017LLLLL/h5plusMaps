package houtai.maps;

import android.webkit.WebView;

import java.util.ArrayList;

import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.feature.internal.sdk.SDK;

/*
单例模式获取webview页面，避免获取多次导致页面刷新。
 原功能：获取webview页面，调用JS方法
 */
public class  GetMapInfoToWeb  {
    public static  WebView Webview;
    public static WebView getInstance(){
        if(Webview == null){
            synchronized (GetMapInfoToWeb.class){
                if(Webview == null){
                    IApp iApp = SDK.obtainCurrentApp();
                    IWebview iWebview = SDK.obatinFirstPage(iApp);
                    ArrayList<IWebview> iWebviews = SDK.obtainAllIWebview();
                    System.out.println(iWebviews.get(0).obtainWebview());
                    Webview = iWebview.obtainWebview();
                }
            }
        }
        return Webview;
    }
}
