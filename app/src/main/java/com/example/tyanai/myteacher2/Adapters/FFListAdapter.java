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

import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Models.UserData;

import java.util.ArrayList;

class FFListViewHolder {
    ImageView iconImageView;
    TextView userNameTextView;
    TextView commentTextView;
    LinearLayout listLinearLayout;
}

public class FFListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<UserData> ffUsersArrayList = new ArrayList<UserData>();

    public FFListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ファイル名
        String iconBitmapString = ffUsersArrayList.get(position).getIconBitmapString();
        String userName = ffUsersArrayList.get(position).getName();
        String comment = ffUsersArrayList.get(position).getComment();

        FFListViewHolder ffListViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            ffListViewHolder = new FFListViewHolder();
            ffListViewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.FFListIcon);
            ffListViewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.FFListName);
            ffListViewHolder.commentTextView = (TextView) convertView.findViewById(R.id.FFListComment);
            ffListViewHolder.listLinearLayout = (LinearLayout)convertView.findViewById(R.id.listLinearLayout);
            convertView.setTag(ffListViewHolder);
        }
        else {
            ffListViewHolder = (FFListViewHolder) convertView.getTag();
        }
        if (userName != null){
            ffListViewHolder.userNameTextView.setText(userName);
        }
        if (comment != null){
            ffListViewHolder.commentTextView.setText(comment);
        }
        byte[] iconImageBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
        if(iconImageBytes.length!=0){
            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            ffListViewHolder.iconImageView.setImageBitmap(iconImageBitmap);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return ffUsersArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setFFUsersArrayList(ArrayList<UserData> list){
        ffUsersArrayList = list;
    }
}