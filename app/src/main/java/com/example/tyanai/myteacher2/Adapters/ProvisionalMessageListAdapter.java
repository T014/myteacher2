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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyanai.myteacher2.Models.ProvisionalMessageData;
import com.example.tyanai.myteacher2.R;

import java.util.ArrayList;

class ProvisionalMessageListViewHolder{
    TextView provisionalMessageTextView;
}

public class ProvisionalMessageListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<ProvisionalMessageData> provisionalMessageArrayList = new ArrayList<ProvisionalMessageData>();

    public ProvisionalMessageListAdapter(Context context,int layoutId){
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
//        String iconBitmapString = provisionalMessageArrayList.get(position).getIconBitmapString();
//        String comment = provisionalMessageArrayList.get(position).getComment();
        String message = provisionalMessageArrayList.get(position).getMessage();

        ProvisionalMessageListViewHolder provisionalMessageListViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            provisionalMessageListViewHolder = new ProvisionalMessageListViewHolder();
//            provisionalMessageListViewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.FFListIcon);
//            provisionalMessageListViewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.FFListName);
//            provisionalMessageListViewHolder.commentTextView = (TextView) convertView.findViewById(R.id.FFListComment);
//            provisionalMessageListViewHolder.listLinearLayout = (LinearLayout)convertView.findViewById(R.id.listLinearLayout);
            provisionalMessageListViewHolder.provisionalMessageTextView = (TextView)convertView.findViewById(R.id.provisionalMessageTextView);
            convertView.setTag(provisionalMessageListViewHolder);
        }
        else {
            provisionalMessageListViewHolder = (ProvisionalMessageListViewHolder) convertView.getTag();
        }
        if (message!=null){
            provisionalMessageListViewHolder.provisionalMessageTextView.setText(message);
        }
//        if (userName != null){
//            provisionalMessageListViewHolder.userNameTextView.setText(userName);
//        }
//        if (comment != null){
//            provisionalMessageListViewHolder.commentTextView.setText(comment);
//        }
//
//
//        byte[] iconImageBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
//        if(iconImageBytes.length!=0){
//            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
//            ffListViewHolder.iconImageView.setImageBitmap(iconImageBitmap);
//        }

        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return provisionalMessageArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setProvisionalMessageArrayList(ArrayList<ProvisionalMessageData> list){
        provisionalMessageArrayList = list;
    }





}
