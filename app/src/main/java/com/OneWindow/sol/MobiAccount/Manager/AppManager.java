package com.OneWindow.sol.MobiAccount.Manager;

import android.content.Context;

/**
 * Created by waqarbscs on 9/9/2016.
 */
public class AppManager {
    public static Context context;
    public static AppManager instance=new AppManager();

    public static AppManager getInstance(){
        return instance;
    }
    private AppManager(){

    }
}
