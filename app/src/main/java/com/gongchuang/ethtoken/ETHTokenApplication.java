/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gongchuang.ethtoken;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;

import com.gongchuang.ethtoken.domain.DaoMaster;
import com.gongchuang.ethtoken.domain.DaoSession;
import com.gongchuang.ethtoken.utils.AppFilePath;
import com.gongchuang.ethtoken.utils.AppUtils;
import com.gongchuang.ethtoken.utils.SharedPreferencesUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class ETHTokenApplication extends MultiDexApplication {

    private static ETHTokenApplication sInstance;

    private RefWatcher refWatcher;

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static RefWatcher getRefWatcher(Context context) {
        ETHTokenApplication application = (ETHTokenApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        sInstance = this;
        AppUtils.init(this);
        AppFilePath.init(this);
//        CrashHandler.getInstance().init(this);
        initPrefs();
        initGreenDao();
    }

    private void initGreenDao() {
        //创建数据库表
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "wallet", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }

    public static ETHTokenApplication getsInstance() {
        return sInstance;
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

}
