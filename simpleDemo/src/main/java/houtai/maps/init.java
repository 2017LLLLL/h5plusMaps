package houtai.maps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import io.dcloud.RInformation;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.IWebviewStateListener;
import io.dcloud.feature.internal.sdk.SDK;

/* 以widget方式启动h5应用 */
public class init implements ICore.ICoreStatusListener, IOnCreateSplashView {

    Activity activity;
    View splashView = null;
    ViewGroup rootView;
    IApp app = null;
    ProgressDialog pd = null;


    public init(Activity activity, ViewGroup rootView) {
        this.activity = activity;
        this.rootView = rootView;
    }

    @Override
    public void onCoreReady(ICore iCore) {
        System.out.println("initSDK 执行!");
        // 调用SDK的初始化接口，初始化5+ SDK
        SDK.initSDK(iCore);
        // 设置当前应用可使用的5+ API
        SDK.requestAllFeature();
    }

    @Override
    public void onCoreInitEnd(ICore iCore) {

        // 表示Webapp的路径在 file:///android_asset/apps/HelloH5
        String appBasePath = "/apps/H551AFFC9";

        // 设置启动参数,可在页面中通过plus.runtime.arguments;方法获取到传入的参数
        String args = "{url:'http://www.baidu.com'}";

        // 启动启动独立应用的5+ Webapp
        app = SDK.startWebApp(activity, appBasePath, args, new IWebviewStateListener() {
            // 设置Webview事件监听，可在监监听内获取WebIvew加载内容的进度
            @Override
            public Object onCallBack(int pType, Object pArgs) {
                switch (pType) {
                    case IWebviewStateListener.ON_WEBVIEW_READY:
                        // WebApp准备加载事件
                        // 准备完毕之后添加webview到显示父View中，
                        // 设置排版不显示状态，避免显示webview时html内容排版错乱问题
                        View view = ((IWebview) pArgs).obtainApp().obtainWebAppRootView().obtainMainView();
                        view.setVisibility(View.INVISIBLE);
                        rootView.addView(view, 0);
                        break;
                    case IWebviewStateListener.ON_PAGE_STARTED:
                        pd = ProgressDialog.show(activity, "加载中", "0/100");
                        break;
                    case IWebviewStateListener.ON_PROGRESS_CHANGED:
                        // WebApp首页面加载进度变化事件
                        if (pd != null) {
                            pd.setMessage(pArgs + "/100");
                        }
                        break;
                    case IWebviewStateListener.ON_PAGE_FINISHED:
                        // WebApp首页面加载完成事件
                        if (pd != null) {
                            pd.dismiss();
                            pd = null;
                        }
                        // 页面加载完毕，设置显示webview
                        app.obtainWebAppRootView().obtainMainView().setVisibility(View.VISIBLE);
                        break;
                }
                return null;
            }
        }, this);

        app.setIAppStatusListener(new IApp.IAppStatusListener() {
            // 设置APP运行事件监听
            @Override
            public void onStart() {

            }

            @Override
            public void onPause(IApp iApp, IApp iApp1) {

            }


            @Override
            public boolean onStop() {
                // 应用运行停止时调用
                rootView.removeView(app.obtainWebAppRootView().obtainMainView());
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public String onStoped(boolean b, String s) {
                return null;
            }


        });


    }

    @Override
    public boolean onCoreStop() {
        return false;
    }

    @Override
    public Object onCreateSplash(Context pContextWrapper) {
        splashView = new FrameLayout(activity);
        splashView.setBackgroundResource(RInformation.DRAWABLE_DCLOUD_DIALOG_SHAPE);
        rootView.addView(splashView);
        return null;
    }

    @Override
    public void onCloseSplash() {
        rootView.removeView(splashView);
    }
}
