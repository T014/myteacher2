package com.example.tyanai.myteacher2;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class BusinessDataHolder{
    ImageView imageView;
    TextView userNameTextView;
    TextView cancelTextView;
    TextView dateTextView;
    TextView receiveDateTextView;
    TextView payDayTextView;
    LinearLayout businessLinearLayout;
}
public class BusinessDataListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<BusinessData> businessDataArrayList = new ArrayList<BusinessData>();

    public BusinessDataListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ファイル名
        String iconImageBitmapString = businessDataArrayList.get(position).getUserIcon();
        String userName = businessDataArrayList.get(position).getUserName();
        String cancel = businessDataArrayList.get(position).getCancel();
        String date = businessDataArrayList.get(position).getDate();
        String receiveDate = businessDataArrayList.get(position).getReceiveDate();
        String payDay = businessDataArrayList.get(position).getPayDay();

        BusinessDataHolder businessDataHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            businessDataHolder = new BusinessDataHolder();
            businessDataHolder.imageView = (ImageView) convertView.findViewById(R.id.contentsImageView);
            businessDataHolder.userNameTextView = (TextView) convertView.findViewById(R.id.postUserNameTextView);
            businessDataHolder.cancelTextView = (TextView) convertView.findViewById(R.id.postUserNameTextView);
            businessDataHolder.dateTextView = (TextView) convertView.findViewById(R.id.postUserNameTextView);
            businessDataHolder.receiveDateTextView = (TextView) convertView.findViewById(R.id.postUserNameTextView);
            businessDataHolder.payDayTextView = (TextView) convertView.findViewById(R.id.postUserNameTextView);
            businessDataHolder.businessLinearLayout = (LinearLayout)convertView.findViewById(R.id.listLinearLayout);
            convertView.setTag(businessDataHolder);
        }
        else {
            businessDataHolder = (BusinessDataHolder) convertView.getTag();
        }
        if (userName != null){
            businessDataHolder.userNameTextView.setText(userName);
        }
        if (cancel!=null){
            businessDataHolder.cancelTextView.setText(cancel);
        }
        if (date != null){
            businessDataHolder.receiveDateTextView.setText(receiveDate);
        }
        if (payDay != null){
            businessDataHolder.payDayTextView.setText(payDay);
        }

        byte[] iconImageBytes = Base64.decode(iconImageBitmapString,Base64.DEFAULT);
        if(iconImageBytes.length!=0){
            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            businessDataHolder.imageView.setImageBitmap(iconImageBitmap);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return businessDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setBusinessDataArrayList(ArrayList<BusinessData> list){
        businessDataArrayList = list;
    }

}
