package com.gongchuang.ethtoken.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class AppFilePath {


    public static String DATA_ROOT_DIR;     // 数据文件根目录
    public static String DATA_ROOT_DIR_OUTER; // 外置卡中数据文件根目录(系统拍照等产生的文件，不允许存放到应用系统目录下)
    public static String CACHE_ROOT_DIR;    // 缓存根目录
    public static String DB_ROOT_DIR;       // 数据库根目录
    // 钱包文件外置存储目录
    public static String Wallet_DIR;
    //walletTemp
    public static String Wallet_Tmp_DIR;
    // 相片外置存储目录
    public static String picture;

    public static void init(Context context) {

        final boolean innerFirst = false;
        // 优先使用app系统目录，即/data/data/com.pagoda.xxx/
        if (innerFirst) {
            CACHE_ROOT_DIR = context.getCacheDir().getPath();
            DATA_ROOT_DIR = context.getFilesDir().getPath();
            DB_ROOT_DIR = context.getFilesDir().getParent();

            String outerPath = getExternalFilesDir(context).getPath();
            DATA_ROOT_DIR_OUTER = outerPath;


        } else {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                CACHE_ROOT_DIR = getExternalCacheDir(context).getPath();
                DATA_ROOT_DIR = getExternalFilesDir(context).getPath();
                DATA_ROOT_DIR_OUTER = DATA_ROOT_DIR;
                DB_ROOT_DIR = DATA_ROOT_DIR;
            } else {
                CACHE_ROOT_DIR = context.getCacheDir().getPath();
                DATA_ROOT_DIR = context.getFilesDir().getPath();
                DB_ROOT_DIR = context.getFilesDir().getParent();
                DATA_ROOT_DIR_OUTER = DATA_ROOT_DIR;
            }
        }
        Wallet_DIR = getExternalPrivatePath("ethtoken");
        Wallet_Tmp_DIR = getExternalPrivatePath("wallettmp");
        picture = getExternalPrivatePath("picture");
        Log.d("Ville", "DataPath = [" + DATA_ROOT_DIR + "] \n" +
                "DBPath = [" + DB_ROOT_DIR + "] \n" +
                "DataPathOuter = [" + DATA_ROOT_DIR_OUTER + "]");

    }

    /**
     * 这种目录下的文件在应用被卸载时也会跟着被删除
     *
     * @param context
     * @return
     */
    public static File getExternalFilesDir(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            File path = context.getExternalFilesDir(null);

            if (path != null) {
                return path;
            }
        }
        final String filesDir = "/Android/data/" + context.getPackageName() + "/files/";
        return new File(Environment.getExternalStorageDirectory().getPath() + filesDir);
    }

    /**
     * 这种目录下的文件在应用被卸载时也会跟着被删除
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            File path = context.getExternalCacheDir();

            if (path != null) {
                return path;
            }
        }
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }


    /**
     * 这种目录下的文件在应用被卸载时不会被删除
     * 钱包等数据可以存放到这里
     *
     * @return
     */
    public static String getExternalPrivatePath(String name) {
        String namedir = "/" + name + "/";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            return Environment.getExternalStorageDirectory().getPath() + namedir;
        } else {
            return new File(DATA_ROOT_DIR_OUTER, name).getPath();
        }
    }
}
