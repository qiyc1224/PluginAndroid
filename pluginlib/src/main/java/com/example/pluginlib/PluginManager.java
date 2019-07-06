package com.example.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static PluginManager sPluginManager = new PluginManager();

    public static PluginManager getInstance() {
        return sPluginManager;
    }

    private PluginManager() {
    }

    private Context mContext;

    public PluginApk getPluginApk() {
        return mPluginApk;
    }

    private PluginApk mPluginApk;

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    //加载apk 文件
    public void loadApk(String apkPath) {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) {
            return;
        }
        DexClassLoader loader = createDexClassLoader(apkPath);
        AssetManager am = createAssetManager(apkPath);
        Resources resources = createResouce(am);
        mPluginApk=new PluginApk(packageInfo,resources,am,loader);
    }

    //创建dexclassloader  加载dex文件
    private DexClassLoader createDexClassLoader(String apkPath){
        File file =mContext.getDir("dex",Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath,file.getAbsolutePath(),null,mContext.getClassLoader());
    }
    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager am =AssetManager.class.newInstance();

            Method method = AssetManager.class.getDeclaredMethod("addAssetPath",String.class);
            method.invoke(am,apkPath);
            return am;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //创建resouce   加载资源文件
    private Resources createResouce(AssetManager am) {
        Resources resources = mContext.getResources();
        return new Resources(am,resources.getDisplayMetrics(),resources.getConfiguration());
    }

}
