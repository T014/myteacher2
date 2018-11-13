package com.example.tyanai.myteacher2.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tyanai.myteacher2.Models.BusinessData;
import com.example.tyanai.myteacher2.R;

import java.util.ArrayList;

class BusinessDataHolder{
    ImageView iconImageView;
    ImageView contentImageView;
    TextView boughtUserNameTextView;
    TextView userNameTextView;
    TextView dateTextView;
    TextView applyDateTextView;
    TextView receiveDateTextView;
    TextView payDayTextView;
    TextView evaluationTextView;
    TextView judgmentTextView;
    TextView permittedDateTextView;
    RelativeLayout businessLinearLayout;
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
        String boughtUserName = businessDataArrayList.get(position).getBuyName();
        String userName = businessDataArrayList.get(position).getUserName();
        String date = businessDataArrayList.get(position).getDate();
        String receiveDate = businessDataArrayList.get(position).getReceiveDate();
        String payDay = businessDataArrayList.get(position).getPayDay();
        String evaluation = businessDataArrayList.get(position).getEvaluation();
        String judgment = businessDataArrayList.get(position).getJudgment();
        String permittedDate = businessDataArrayList.get(position).getPermittedDate();


        BusinessDataHolder businessDataHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            businessDataHolder = new BusinessDataHolder();
            businessDataHolder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            businessDataHolder.boughtUserNameTextView = (TextView) convertView.findViewById(R.id.boughtUserNameTextView);
            businessDataHolder.contentImageView = (ImageView) convertView.findViewById(R.id.contentImageView);
            businessDataHolder.userNameTextView = (TextView) convertView.findViewById(R.id.userNameTextView);
            businessDataHolder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            businessDataHolder.receiveDateTextView = (TextView) convertView.findViewById(R.id.receiveDateTextView);
            businessDataHolder.payDayTextView = (TextView) convertView.findViewById(R.id.payDayTextView);
            businessDataHolder.evaluationTextView = (TextView)convertView.findViewById(R.id.evaluationTextView);
            businessDataHolder.judgmentTextView = (TextView)convertView.findViewById(R.id.judgmentTextView);
            businessDataHolder.permittedDateTextView = (TextView)convertView.findViewById(R.id.permittedDateTextView);
            businessDataHolder.businessLinearLayout = (RelativeLayout)convertView.findViewById(R.id.businessLinearLayout);
            convertView.setTag(businessDataHolder);
        }
        else {
            businessDataHolder = (BusinessDataHolder) convertView.getTag();
        }
        if (userName != null){
            businessDataHolder.userNameTextView.setText(userName);
        }
        if (date != null){
            businessDataHolder.dateTextView.setText("申請日"+date);
        }
        if (date != null){
            businessDataHolder.receiveDateTextView.setText("受講日："+receiveDate);
        }
        if (payDay != null){
            businessDataHolder.payDayTextView.setText("支払日："+payDay);
        }
        if (evaluation != null){
            businessDataHolder.evaluationTextView.setText("投稿の評価："+evaluation);
        }
        if(judgment != null){
            businessDataHolder.judgmentTextView.setText("評価した？"+judgment);
        }else{
            businessDataHolder.judgmentTextView.setText("評価した？");
        }
        if (permittedDate != null){
            businessDataHolder.permittedDateTextView.setText("許可された日" + permittedDate);
        }else{
            businessDataHolder.permittedDateTextView.setText(permittedDate);
        }
        if (boughtUserName!=null){
            businessDataHolder.boughtUserNameTextView.setText("購入者"+boughtUserName);
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
