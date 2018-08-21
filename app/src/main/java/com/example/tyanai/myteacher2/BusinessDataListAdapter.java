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
    ImageView iconImageView;
    ImageView contentImageView;
    TextView userNameTextView;
    TextView cancelTextView;
    TextView dateTextView;
    TextView receiveDateTextView;
    TextView payDayTextView;
    TextView evaluationTextView;
    TextView judgmentTextView;
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
        String iconImageBitmapString = businessDataArrayList.get(position).getUserIcon();
        String contentsImageBitmapString = businessDataArrayList.get(position).getContentImageBitmapString();
        String userName = businessDataArrayList.get(position).getUserName();
        String cancel = businessDataArrayList.get(position).getCancel();
        String date = businessDataArrayList.get(position).getDate();
        String receiveDate = businessDataArrayList.get(position).getReceiveDate();
        String payDay = businessDataArrayList.get(position).getPayDay();
        String evaluation = businessDataArrayList.get(position).getEvaluation();
        String judgment = businessDataArrayList.get(position).getJudgment();


        BusinessDataHolder businessDataHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            businessDataHolder = new BusinessDataHolder();
            businessDataHolder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            businessDataHolder.contentImageView = (ImageView) convertView.findViewById(R.id.contentImageView);
            businessDataHolder.userNameTextView = (TextView) convertView.findViewById(R.id.userNameTextView);
            businessDataHolder.cancelTextView = (TextView) convertView.findViewById(R.id.cancelTextView);
            businessDataHolder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            businessDataHolder.receiveDateTextView = (TextView) convertView.findViewById(R.id.receiveDateTextView);
            businessDataHolder.payDayTextView = (TextView) convertView.findViewById(R.id.payDayTextView);
            businessDataHolder.evaluationTextView = (TextView)convertView.findViewById(R.id.evaluationTextView);
            businessDataHolder.judgmentTextView = (TextView)convertView.findViewById(R.id.judgmentTextView);
            businessDataHolder.businessLinearLayout = (LinearLayout)convertView.findViewById(R.id.businessLinearLayout);
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
            businessDataHolder.dateTextView.setText(date);
        }
        if (date != null){
            businessDataHolder.receiveDateTextView.setText(receiveDate);
        }
        if (payDay != null){
            businessDataHolder.payDayTextView.setText(payDay);
        }
        if (evaluation != null){
            businessDataHolder.evaluationTextView.setText(evaluation);
        }
        if(judgment != null){
            businessDataHolder.judgmentTextView.setText(judgment);
        }

        byte[] iconImageBytes = Base64.decode(iconImageBitmapString,Base64.DEFAULT);
        if(iconImageBytes.length!=0){
            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            businessDataHolder.iconImageView.setImageBitmap(iconImageBitmap);
        }
        byte[] contentsImageBytes = Base64.decode(contentsImageBitmapString,Base64.DEFAULT);
        if(contentsImageBytes.length!=0){
            Bitmap contentsImageBitmap = BitmapFactory.decodeByteArray(contentsImageBytes,0, contentsImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            businessDataHolder.contentImageView.setImageBitmap(contentsImageBitmap);
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
