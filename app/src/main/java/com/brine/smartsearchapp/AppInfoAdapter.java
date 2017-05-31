package com.brine.smartsearchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by phamhai on 31/05/2017.
 */

public class AppInfoAdapter extends BaseAdapter{
    private List<AppInfo> mListAppInfo;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public AppInfoAdapter(Context _context, List<AppInfo> _listAppInfo){
        this.mContext = _context;
        this.mListAppInfo = _listAppInfo;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount(){
        return mListAppInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return mListAppInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            view = mLayoutInflater.inflate(R.layout.app_info_item, null);
            holder = new ViewHolder();
            holder.appName = (TextView) view.findViewById(R.id.app_name);
            holder.appImage = (ImageView) view.findViewById(R.id.app_image);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final AppInfo appInfo = mListAppInfo.get(i);
        if(appInfo.getName().length() > 7){
            holder.appName.setText(appInfo.getName().substring(0, 7) + "..");
        }else{
            holder.appName.setText(appInfo.getName());
        }
        holder.appImage.setImageDrawable(
                appInfo.getApplicationInfo().loadIcon(mContext.getPackageManager()));

        return view;
    }

    private class ViewHolder{
        TextView appName;
        ImageView appImage;
    }
}
