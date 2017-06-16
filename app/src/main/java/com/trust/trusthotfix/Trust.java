package com.trust.trusthotfix;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by Trust on 2017/6/15.
 */

@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.trust.trusthotfixTrust.",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)


public class Trust  extends DefaultApplicationLike {
    public Trust(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onBaseContextAttached(Context base) {
        MultiDex.install(base);
        TinkerInstaller.install(this,new DefaultLoadReporter(getApplication())
                ,new DefaultPatchReporter(getApplication()),
                new DefaultPatchListener(getApplication()),TrustServer.class,
                new UpgradePatch());

        Toast.makeText(getApplication(),"加载完成", Toast.LENGTH_SHORT).show();

    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //此处写自己的Application逻辑
    }
}
