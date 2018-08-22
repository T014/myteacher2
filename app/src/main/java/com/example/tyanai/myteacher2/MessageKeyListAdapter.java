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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

class MessageKeyListViewHolder {
    ImageView userIconImageView;
    TextView userNameTextVew;
    TextView contentsTextView;
    TextView timeTextView;
    LinearLayout listLinearLayout;
}

public class MessageKeyListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<MessageListData> newMessageKeyArrayList = new ArrayList<MessageListData>();

    public MessageKeyListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //ファイル名
        String bitmapString = newMessageKeyArrayList.get(position).getBitmapString();
        String userIconBitmapString = newMessageKeyArrayList.get(position).getIconBitmapString();
        String userName = newMessageKeyArrayList.get(position).getUserName();
        String contents = newMessageKeyArrayList.get(position).getContent();
        String time = newMessageKeyArrayList.get(position).getTime();

        MessageKeyListViewHolder messageKeyListViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            messageKeyListViewHolder = new MessageKeyListViewHolder();
            messageKeyListViewHolder.userIconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            messageKeyListViewHolder.userNameTextVew = (TextView) convertView.findViewById(R.id.userNameTextView);
            messageKeyListViewHolder.contentsTextView = (TextView) convertView.findViewById(R.id.contentsTextView);
            messageKeyListViewHolder.timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
            messageKeyListViewHolder.listLinearLayout = (LinearLayout)convertView.findViewById(R.id.listLinearLayout);
            convertView.setTag(messageKeyListViewHolder);
        } else {
            messageKeyListViewHolder = (MessageKeyListViewHolder) convertView.getTag();
        }
        if (userName != null){
            messageKeyListViewHolder.userNameTextVew.setText(userName);
        }

        if (time!=null){
            messageKeyListViewHolder.timeTextView.setText(time);
        }

        byte[] postImageBytes = Base64.decode(bitmapString,Base64.DEFAULT);
        if(postImageBytes.length!=0){
            messageKeyListViewHolder.contentsTextView.setText("画像が送信されました。");
        }else{
            if (contents != null){
                messageKeyListViewHolder.contentsTextView.setText(contents);
            }
        }

        byte[] iconImageBytes = Base64.decode(userIconBitmapString,Base64.DEFAULT);
        if(iconImageBytes.length!=0){
            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            messageKeyListViewHolder.userIconImageView.setImageBitmap(iconImageBitmap);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return newMessageKeyArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setNewMessageKeyArrayList(ArrayList<MessageListData> list){
        newMessageKeyArrayList = list;
    }




}
