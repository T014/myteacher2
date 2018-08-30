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


class MessageListViewHolder {

    LinearLayout myMessageLayout;
    LinearLayout myMessageImageLinearLayout;
    LinearLayout otherMessageLayout;
    LinearLayout otherMessageImageLinearLayout;

    TextView messageTimeTextView;
    TextView messageContentsTextView;
    ImageView myMessageImageView;
    ImageView myMessageImageIconImageView;
    TextView myMessageImageTimeTextView;

    ImageView otherMessageIconImageView;
    TextView otherMessageContentsTextView;
    TextView otherMessageTimeTextView;
    ImageView otherMessageImageView;
    ImageView otherMessageImageIconImageView;
    TextView otherMessageImageTimeTextView;



    LinearLayout messageListLinearLayout;
}

public class MessageListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<MessageListData> messageArrayList = new ArrayList<MessageListData>();

    public MessageListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //ファイル名
        String bitmapString = messageArrayList.get(position).getBitmapString();
        String userIconBitmapString = messageArrayList.get(position).getIconBitmapString();
        String userName = messageArrayList.get(position).getUserName();
        String contents = messageArrayList.get(position).getContent();
        String time = messageArrayList.get(position).getTime();
        String uid = messageArrayList.get(position).getUid();
        String myUid = messageArrayList.get(position).getMyUid();

        com.example.tyanai.myteacher2.MessageListViewHolder messageListViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            messageListViewHolder = new com.example.tyanai.myteacher2.MessageListViewHolder();
            messageListViewHolder.messageTimeTextView = (TextView) convertView.findViewById(R.id.messageTimeTextView);
            messageListViewHolder.messageContentsTextView = (TextView) convertView.findViewById(R.id.messageContentsTextView);


            messageListViewHolder.myMessageImageTimeTextView = (TextView) convertView.findViewById(R.id.myMessageImageTimeTextView);
            messageListViewHolder.myMessageImageView = (ImageView) convertView.findViewById(R.id.myMessageImageView);

            messageListViewHolder.otherMessageIconImageView = (ImageView) convertView.findViewById(R.id.otherMessageIconImageView);
            messageListViewHolder.otherMessageContentsTextView = (TextView) convertView.findViewById(R.id.otherMessageContentsTextView);
            messageListViewHolder.otherMessageTimeTextView = (TextView) convertView.findViewById(R.id.otherMessageTimeTextView);

            messageListViewHolder.otherMessageImageView = (ImageView) convertView.findViewById(R.id.otherMessageImageView);
            messageListViewHolder.otherMessageImageIconImageView = (ImageView) convertView.findViewById(R.id.otherMessageImageIconImageView);
            messageListViewHolder.otherMessageImageTimeTextView = (TextView) convertView.findViewById(R.id.otherMessageImageTimeTextView);


            messageListViewHolder.messageListLinearLayout = (LinearLayout)convertView.findViewById(R.id.messageListLinearLayout);
            messageListViewHolder.myMessageLayout = (LinearLayout)convertView.findViewById(R.id.myMessageLayout);
            messageListViewHolder.myMessageImageLinearLayout = (LinearLayout)convertView.findViewById(R.id.myMessageImageLinearLayout);
            messageListViewHolder.otherMessageLayout = (LinearLayout)convertView.findViewById(R.id.otherMessageLayout);
            messageListViewHolder.otherMessageImageLinearLayout = (LinearLayout)convertView.findViewById(R.id.otherMessageImageLinearLayout);
            convertView.setTag(messageListViewHolder);
        } else {
            messageListViewHolder = (com.example.tyanai.myteacher2.MessageListViewHolder) convertView.getTag();
        }


        if (uid!=null){
            if (uid.equals(myUid)){
                //自分の時の処理
                byte[] myContentsImageBytes = Base64.decode(bitmapString,Base64.DEFAULT);
                if(myContentsImageBytes.length!=0){
                    //自分が画像送信した
                    Bitmap myContentsImageBitmap = BitmapFactory.decodeByteArray(myContentsImageBytes,0, myContentsImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    messageListViewHolder.myMessageImageView.setImageBitmap(myContentsImageBitmap);
                    if (time!=null){
                        messageListViewHolder.myMessageImageTimeTextView.setText(time);
                    }
                    byte[] myMessageIconImageBytes = Base64.decode(userIconBitmapString,Base64.DEFAULT);
                    if(myMessageIconImageBytes.length!=0) {
                        Bitmap myMessageIconImageBitmap = BitmapFactory.decodeByteArray(myMessageIconImageBytes, 0, myMessageIconImageBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                        messageListViewHolder.myMessageImageIconImageView.setImageBitmap(myMessageIconImageBitmap);
                    }
                    messageListViewHolder.myMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageImageLinearLayout.setVisibility(View.GONE);

                }else{
                    //自分がテキストの送信
                    if (time!=null){
                        messageListViewHolder.messageTimeTextView.setText(time);
                    }
                    if (contents!=null){
                        messageListViewHolder.messageContentsTextView.setText(contents);
                    }
                    messageListViewHolder.myMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageImageLinearLayout.setVisibility(View.GONE);
                }
            }else{
                //他の人の時の処理
                byte[] otherContentsImageBytes = Base64.decode(bitmapString,Base64.DEFAULT);
                if(otherContentsImageBytes.length!=0){
                    //他の人が画像送信した
                    Bitmap otherContentsImageBitmap = BitmapFactory.decodeByteArray(otherContentsImageBytes,0, otherContentsImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    messageListViewHolder.otherMessageImageView.setImageBitmap(otherContentsImageBitmap);
                    if (time!=null){
                        messageListViewHolder.otherMessageImageTimeTextView.setText(time);
                    }
                    byte[] otherMessageIconImageBytes = Base64.decode(userIconBitmapString,Base64.DEFAULT);
                    if(otherMessageIconImageBytes.length!=0) {
                        Bitmap otherMessageIconImageBitmap = BitmapFactory.decodeByteArray(otherMessageIconImageBytes, 0, otherMessageIconImageBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                        messageListViewHolder.otherMessageImageIconImageView.setImageBitmap(otherMessageIconImageBitmap);
                    }
                    messageListViewHolder.myMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.myMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageLayout.setVisibility(View.GONE);

                }else{
                    //他の人がテキストの送信
                    if (time!=null){
                        messageListViewHolder.otherMessageTimeTextView.setText(time);
                    }
                    if (contents!=null){
                        messageListViewHolder.otherMessageContentsTextView.setText(contents);
                    }
                    byte[] otherMessageIconImageBytes = Base64.decode(userIconBitmapString,Base64.DEFAULT);
                    if(otherMessageIconImageBytes.length!=0) {
                        Bitmap otherMessageIconImageBitmap = BitmapFactory.decodeByteArray(otherMessageIconImageBytes, 0, otherMessageIconImageBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                        messageListViewHolder.otherMessageIconImageView.setImageBitmap(otherMessageIconImageBitmap);
                    }
                    messageListViewHolder.myMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.myMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageImageLinearLayout.setVisibility(View.GONE);
                }
            }
        }


        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return messageArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setMessageArrayList(ArrayList<MessageListData> list){
        messageArrayList = list;
    }




}
