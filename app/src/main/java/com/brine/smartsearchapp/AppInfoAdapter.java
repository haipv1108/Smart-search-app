package com.brine.smartsearchapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phamhai on 31/05/2017.
 */

public class AppInfoAdapter extends BaseAdapter implements Filterable {
    private List<AppInfo> mOriginalAppInfos;
    private List<AppInfo> mDisplayAppInfos;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    AppInfoAdapter(Context _context, List<AppInfo> _listAppInfo){
        this.mContext = _context;
        this.mOriginalAppInfos = _listAppInfo;
        this.mDisplayAppInfos = _listAppInfo;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount(){
        return mDisplayAppInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return mDisplayAppInfos.get(i);
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
        final AppInfo appInfo = mDisplayAppInfos.get(i);
        if(appInfo.getName().length() > 7){
            holder.appName.setText(appInfo.getName().substring(0, 7) + "..");
        }else{
            holder.appName.setText(appInfo.getName());
        }
        holder.appImage.setImageDrawable(
                appInfo.getApplicationInfo().loadIcon(mContext.getPackageManager()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mContext.getPackageManager()
                        .getLaunchIntentForPackage(appInfo.getApplicationInfo().packageName);
                if(intent != null){
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                mDisplayAppInfos = (ArrayList<AppInfo>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<AppInfo> FilteredArrList = new ArrayList<AppInfo>();

                if (mOriginalAppInfos == null) {
                    mOriginalAppInfos = new ArrayList<AppInfo>(mDisplayAppInfos);
                }

                if (constraint == null || constraint.length() == 0) {
                    results.count = mOriginalAppInfos.size();
                    results.values = mOriginalAppInfos;
                } else {
                    for (int i = 0; i < mOriginalAppInfos.size(); i++) {
                        String data = mOriginalAppInfos.get(i).getName();
                        if (data.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            FilteredArrList.add(new AppInfo(
                                    mOriginalAppInfos.get(i).getName(),
                                    mOriginalAppInfos.get(i).getApplicationInfo()));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    public boolean isFiltedData(){
        return mDisplayAppInfos.size() > 0;
    }

    private class ViewHolder{
        TextView appName;
        ImageView appImage;
    }
}
