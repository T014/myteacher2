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


import com.example.tyanai.myteacher2.Models.MessageListData;
import com.example.tyanai.myteacher2.R;

import java.util.ArrayList;


class MessageListViewHolder {

    LinearLayout myMessageLayout;
    LinearLayout myMessageImageLinearLayout;
    LinearLayout otherMessageLayout;
    LinearLayout otherMessageImageLinearLayout;
    LinearLayout newDateLinearLayout;
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
    TextView newDateTextView;
    LinearLayout messageListLinearLayout;
    View view;
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
        String contents = messageArrayList.get(position).getContent();
        String time = messageArrayList.get(position).getTime();
        String uid = messageArrayList.get(position).getUid();
        String myUid = messageArrayList.get(position).getMyUid();
        int v=0;
        String newContents="";

        MessageListViewHolder messageListViewHolder;

        if (convertView == null) {
        // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
        convertView = inflater.inflate(layoutId, parent, false);
        // ViewHolder を生成

        messageListViewHolder = new MessageListViewHolder();



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

        messageListViewHolder.newDateTextView = (TextView) convertView.findViewById(R.id.newDateTextView);

        messageListViewHolder.messageListLinearLayout = (LinearLayout)convertView.findViewById(R.id.messageListLinearLayout);
        messageListViewHolder.myMessageLayout = (LinearLayout)convertView.findViewById(R.id.myMessageLayout);
        messageListViewHolder.myMessageImageLinearLayout = (LinearLayout)convertView.findViewById(R.id.myMessageImageLinearLayout);
        messageListViewHolder.otherMessageLayout = (LinearLayout)convertView.findViewById(R.id.otherMessageLayout);
        messageListViewHolder.otherMessageImageLinearLayout = (LinearLayout)convertView.findViewById(R.id.otherMessageImageLinearLayout);
        messageListViewHolder.newDateLinearLayout = (LinearLayout)convertView.findViewById(R.id.newDateLinearLayout);

        messageListViewHolder.view = convertView;

        convertView.setTag(messageListViewHolder);

        } else {
            messageListViewHolder = (MessageListViewHolder) convertView.getTag();
            messageListViewHolder.myMessageLayout.setVisibility(View.VISIBLE);
            messageListViewHolder.myMessageImageLinearLayout.setVisibility(View.VISIBLE);
            messageListViewHolder.otherMessageLayout.setVisibility(View.VISIBLE);
            messageListViewHolder.otherMessageImageLinearLayout.setVisibility(View.VISIBLE);
            messageListViewHolder.messageListLinearLayout.setVisibility(View.VISIBLE);
            messageListViewHolder.newDateLinearLayout.setVisibility(View.VISIBLE);
        }

         if (uid.equals("")){
            if (time!=null){
                messageListViewHolder.newDateTextView.setText(time);
                //時間送信　全部非表示　コンテンツをmatchで表示
                messageListViewHolder.myMessageLayout.setVisibility(View.GONE);
                messageListViewHolder.myMessageImageLinearLayout.setVisibility(View.GONE);
                messageListViewHolder.otherMessageLayout.setVisibility(View.GONE);
                messageListViewHolder.otherMessageImageLinearLayout.setVisibility(View.GONE);
            }
         }else if (uid!=null){
            String n = time.substring(11,16);
            if (uid.equals(myUid)){
                //自分の時の処理
                byte[] myContentsImageBytes = Base64.decode(bitmapString,Base64.DEFAULT);
                if(myContentsImageBytes.length!=0){
                    //自分が画像送信した
                    Bitmap myContentsImageBitmap = BitmapFactory.decodeByteArray(myContentsImageBytes,0, myContentsImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    messageListViewHolder.myMessageImageView.setImageBitmap(myContentsImageBitmap);
                    if (time!=null){
                        messageListViewHolder.myMessageImageTimeTextView.setText(n);
                    }

                    messageListViewHolder.myMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.newDateLinearLayout.setVisibility(View.GONE);
                }else{
                    //自分がテキストの送信
                    if (time!=null){
                        messageListViewHolder.messageTimeTextView.setText(n);
                    }
                    if (contents!=null){
                        int contentsCount = contents.length();
                        if (contentsCount>16){
                            int o = 0;
                            for (int m=0;m<contentsCount;m++){
                                v = o+16;
                                if (contentsCount-o<15 || contentsCount-o==15){
                                    String searchContents = contents.substring(o,contentsCount);
                                    newContents = newContents.concat(searchContents);
                                    break;
                                }
                                String searchContents = contents.substring(o,v);
                                int jg = searchContents.indexOf("\n");

                                if (jg==-1){
                                    newContents = newContents.concat(searchContents.substring(0,15)+"\n");
                                    o=v-1;
                                }else if(jg==16){
                                    //
                                    newContents = newContents.concat(searchContents.substring(0,15).concat("\n"+searchContents.charAt(15)));
                                } else{
                                    String a = searchContents.substring(0,jg+1);
                                    newContents = newContents.concat(a);
                                    o+=jg+2;
                                }
                            }
                        }else if(contentsCount==16) {
                            //15+/n+1
                            newContents = contents.substring(0,15).concat("\n"+contents.charAt(15));
                        }else{
                            newContents = contents;
                        }

                        messageListViewHolder.messageContentsTextView.setText(newContents);
                    }
                    messageListViewHolder.otherMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.myMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.newDateLinearLayout.setVisibility(View.GONE);
                }
            }else{
                //他の人の時の処理
                byte[] otherContentsImageBytes = Base64.decode(bitmapString,Base64.DEFAULT);
                if(otherContentsImageBytes.length!=0){
                    //他の人が画像送信した
                    Bitmap otherContentsImageBitmap = BitmapFactory.decodeByteArray(otherContentsImageBytes,0, otherContentsImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    messageListViewHolder.otherMessageImageView.setImageBitmap(otherContentsImageBitmap);
                    if (time!=null){
                        messageListViewHolder.otherMessageImageTimeTextView.setText(n);
                    }
                    byte[] otherMessageIconImageBytes = Base64.decode(userIconBitmapString,Base64.DEFAULT);
                    if(otherMessageIconImageBytes.length!=0) {
                        Bitmap otherMessageIconImageBitmap = BitmapFactory.decodeByteArray(otherMessageIconImageBytes, 0, otherMessageIconImageBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                        messageListViewHolder.otherMessageImageIconImageView.setImageBitmap(otherMessageIconImageBitmap);
                    }
                    messageListViewHolder.myMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.myMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.newDateLinearLayout.setVisibility(View.GONE);
                }else{
                    //他の人がテキストの送信
                    if (time!=null){
                        messageListViewHolder.otherMessageTimeTextView.setText(n);
                    }
                    if (contents!=null){
                        messageListViewHolder.otherMessageContentsTextView.setText(contents);
                    }
                    byte[] otherMessageIconImageBytes = Base64.decode(userIconBitmapString,Base64.DEFAULT);
                    if(otherMessageIconImageBytes.length>5) {
                        Bitmap otherMessageIconImageBitmap = BitmapFactory.decodeByteArray(otherMessageIconImageBytes, 0, otherMessageIconImageBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                        messageListViewHolder.otherMessageIconImageView.setImageBitmap(otherMessageIconImageBitmap);
                    }

                    messageListViewHolder.myMessageLayout.setVisibility(View.GONE);
                    messageListViewHolder.myMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.otherMessageImageLinearLayout.setVisibility(View.GONE);
                    messageListViewHolder.newDateLinearLayout.setVisibility(View.GONE);
                }
            }
        }
        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return false;
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
