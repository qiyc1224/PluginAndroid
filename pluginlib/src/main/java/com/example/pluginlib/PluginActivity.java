package com.example.pluginlib;

import android.app.Activity;
import android.os.Bundle;

public class PluginActivity extends Activity implements IPlugin {

    private int FROM = FROM_INTERNAL;

    private Activity mProxyActivity;

    @Override
    public void onAttach(Activity proxyActivity) {
        mProxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState!=null){
            FROM=saveInstanceState.getInt("FROM");
        }
        if (FROM==FROM_INTERNAL){
            super.onCreate(saveInstanceState);
            mProxyActivity=this;
        }
    }

    @Override
    public void onPause() {
        if (FROM==FROM_INTERNAL){
            super.onPause();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (FROM==FROM_INTERNAL){
            super.setContentView(layoutResID);
        }else {
            mProxyActivity.setContentView(layoutResID);
        }
    }



}
