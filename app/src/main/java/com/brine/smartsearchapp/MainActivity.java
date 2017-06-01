package com.brine.smartsearchapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static String TAG = MainActivity.class.getCanonicalName();

    private static List<AppInfo> mListAppInfo = new ArrayList<>();
    private GridView mGridView;
    private TextView mTvMessage;
    private AppInfoAdapter mAdapter;
    private List<AppInfo> mListFoundAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(mListAppInfo.size() == 0){
            mListAppInfo.addAll(getAllAppInfos());
        }

        mGridView = (GridView) findViewById(R.id.gridView);
        EditText mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
        mListFoundAppInfo = new ArrayList<>();
        mAdapter = new AppInfoAdapter(this, mListFoundAppInfo);
        mGridView.setAdapter(mAdapter);

        mListFoundAppInfo.addAll(mListAppInfo);
        mAdapter.notifyDataSetChanged();

        mEdtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyword = charSequence.toString();
                if(keyword.trim().length() == 0){
                    mGridView.setVisibility(View.GONE);
                    mTvMessage.setVisibility(View.VISIBLE);
                }else{
                    mAdapter.getFilter().filter(charSequence.toString());
                    if(mAdapter.isFiltedData()){
                        mGridView.setVisibility(View.VISIBLE);
                        mTvMessage.setVisibility(View.GONE);
                    }else{
                        mGridView.setVisibility(View.GONE);
                        mTvMessage.setVisibility(View.VISIBLE);
                    }
                    if(mGridView.getHeight() > 600){
                        mGridView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, 600));
                    }else{
                        mGridView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private List<AppInfo> getAllAppInfos(){
        List<AppInfo> appInfos = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> packages =
                packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo packageInfo : packages){
            if(packageManager.getLaunchIntentForPackage(
                    packageInfo.packageName) != null){
                String appName = packageInfo.loadLabel(packageManager).toString();
                AppInfo appInfo = new AppInfo(appName, packageInfo);
                appInfos.add(appInfo);
            }
        }
        return appInfos;
    }

}
