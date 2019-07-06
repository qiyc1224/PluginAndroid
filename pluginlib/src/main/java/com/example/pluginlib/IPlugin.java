package com.example.pluginlib;

import android.app.Activity;
import android.os.Bundle;

public interface IPlugin {
    void onAttach(Activity proxyActivity);
    void onCreate(Bundle saveInstanceState);
    void onPause();

    int FROM_INTERNAL=0;
    int FROM_EXTERNAL=1;
}
