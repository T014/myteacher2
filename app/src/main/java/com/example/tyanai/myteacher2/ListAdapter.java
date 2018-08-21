package com.example.tyanai.myteacher2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class ListViewHolder {
    ImageView userIconImageView;
    ImageView contentsImageView;
    TextView userNameTextVew;
    TextView contentsTextView;
    LinearLayout listLinearLayout;
}

public class ListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<PostData> timeLineArrayList = new ArrayList<PostData>();

    public ListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ファイル名
        String postImageBitmapString = timeLineArrayList.get(position).getImageBitmapString();
        String userIconImageBitmapString = timeLineArrayList.get(position).getUserIconBitmapString();
        String userName = timeLineArrayList.get(position).getName();
        String contents = timeLineArrayList.get(position).getContents();

        ListViewHolder listViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            listViewHolder = new ListViewHolder();
            listViewHolder.userIconImageView = (ImageView) convertView.findViewById(R.id.userIconImageView);
            listViewHolder.contentsImageView = (ImageView) convertView.findViewById(R.id.contentsImageView);
            listViewHolder.userNameTextVew = (TextView) convertView.findViewById(R.id.postUserNameTextView);
            listViewHolder.contentsTextView = (TextView) convertView.findViewById(R.id.postContentsTextView);
            listViewHolder.listLinearLayout = (LinearLayout)convertView.findViewById(R.id.listLinearLayout);
            convertView.setTag(listViewHolder);
        }
        else {
            listViewHolder = (ListViewHolder) convertView.getTag();
        }
        if (userName != null){
            listViewHolder.userNameTextVew.setText(userName);
        }
        if (contents != null){
            listViewHolder.contentsTextView.setText(contents);
        }



        //ここで色指定できる評価の高さによって
        if (contents != null){
            if (contents.equals("あああ")){
                listViewHolder.listLinearLayout.setBackgroundColor(Color.rgb(127,127,255));
            }
        }




        byte[] postImageBytes = Base64.decode(postImageBitmapString,Base64.DEFAULT);
        if(postImageBytes.length!=0){
            Bitmap postImageBitmap = BitmapFactory.decodeByteArray(postImageBytes,0, postImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            listViewHolder.contentsImageView.setImageBitmap(postImageBitmap);
        }

        byte[] iconImageBytes = Base64.decode(userIconImageBitmapString,Base64.DEFAULT);
        if(iconImageBytes.length!=0){
            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            listViewHolder.userIconImageView.setImageBitmap(iconImageBitmap);
        }






        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return timeLineArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setTimeLineArrayList(ArrayList<PostData> list){
        timeLineArrayList = list;
    }



}
