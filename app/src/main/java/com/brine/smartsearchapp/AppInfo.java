package com.brine.smartsearchapp;

import android.content.pm.ApplicationInfo;

/**
 * Created by phamhai on 31/05/2017.
 */

public class AppInfo {
    private String name;
    private ApplicationInfo applicationInfo;

    public AppInfo(String _name, ApplicationInfo _applicationInfo){
        this.name = _name;
        this.applicationInfo = _applicationInfo;
    }

    public String getName() {
        return name;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }
}
