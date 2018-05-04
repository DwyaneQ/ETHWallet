package com.gongchuang.ethtoken.base;

import com.gongchuang.ethtoken.utils.AppUtils;
import com.gongchuang.ethtoken.utils.FileUtils;

/**
 * Created by dwq on 2018/3/13/013.
 * e-mail:lomapa@163.com
 */

public class Constants {

    public static String BASE_URL = "接口公共url";

    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";

    public static String PATH_TXT = PATH_DATA + "/token/";

    public static final String SUFFIX_ZIP = ".zip";

    public static String PATH_EPUB = PATH_DATA + "/epub";
}
