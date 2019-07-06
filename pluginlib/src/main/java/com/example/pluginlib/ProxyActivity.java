package com.example.pluginlib;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

public class ProxyActivity extends PluginActivity {


    String mClassName;
    PluginApk mPluginApk;
    IPlugin mIPlugin;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk=PluginManager.getInstance().getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (mPluginApk==null){
            Log.e("xxxxx", "apk文件有误，无法进行跳转");
        }
        try {
            Class<?> clazz=mPluginApk.mDexClassLoader.loadClass(mClassName);
            Object object=clazz.newInstance();
            if (object instanceof IPlugin){
                mIPlugin= (IPlugin) object;
                mIPlugin.onAttach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM",IPlugin.FROM_EXTERNAL);
                mIPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk!=null?mPluginApk.mResources:super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk!=null?mPluginApk.mAssetManager:super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
         return mPluginApk!=null?mPluginApk.mDexClassLoader:super.getClassLoader();
    }
}
