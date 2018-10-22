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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

class ListViewHolder {
    ImageView userIconImageView;
    ImageView contentsImageView;
    TextView userNameTextVew;
    TextView contentsTextView;
    TextView contentsTimeTextView;
    ToggleButton goodButton;
    TextView goodCountTextView;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //ファイル名
        String postImageBitmapString = timeLineArrayList.get(position).getImageBitmapString();
        String userIconImageBitmapString = timeLineArrayList.get(position).getUserIconBitmapString();
        String userName = timeLineArrayList.get(position).getName();
        String contents = timeLineArrayList.get(position).getContents();
        String contentsTime = timeLineArrayList.get(position).getTime();
        String goodCount = timeLineArrayList.get(position).getGood();
        String favFlag = timeLineArrayList.get(position).getFavFlag();
        final ListViewHolder listViewHolder;

        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            listViewHolder = new ListViewHolder();
            listViewHolder.userIconImageView = (ImageView) convertView.findViewById(R.id.userIconImageView);
            listViewHolder.contentsImageView = (ImageView) convertView.findViewById(R.id.contentsImageView);
            listViewHolder.userNameTextVew = (TextView) convertView.findViewById(R.id.postUserNameTextView);
            listViewHolder.contentsTextView = (TextView) convertView.findViewById(R.id.postContentsTextView);
            listViewHolder.contentsTimeTextView = (TextView) convertView.findViewById(R.id.contentsTimeTextView);
            listViewHolder.goodButton = (ToggleButton) convertView.findViewById(R.id.goodButton);
            listViewHolder.goodCountTextView = (TextView) convertView.findViewById(R.id.goodCountTextView);
            listViewHolder.listLinearLayout = (LinearLayout)convertView.findViewById(R.id.listLinearLayout);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ListViewHolder) convertView.getTag();
        }
        if (userName != null){
            listViewHolder.userNameTextVew.setText(userName);
        }
        if (contents != null){
            int aaa = contents.length();
            if (aaa>80){
                String bbb = contents.substring(0,79);
                String ccc = bbb+"...";
                listViewHolder.contentsTextView.setText(ccc);
            }else{
                listViewHolder.contentsTextView.setText(contents);
            }
        }
        if (contentsTime!=null){
            listViewHolder.contentsTimeTextView.setText(contentsTime);
        }
        if (goodCount!=null){
            listViewHolder.goodCountTextView.setText(goodCount);
        }
        if (favFlag!=null){
            if (favFlag.equals("true")){
                listViewHolder.goodButton.setChecked(true);
            }else {
                listViewHolder.goodButton.setChecked(false);
            }
        }
        listViewHolder.goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView)parent).performItemClick(view,position,R.id.goodButton);
            }
        });
        listViewHolder.userIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView)parent).performItemClick(view,position,R.id.userIconImageView);
            }
        });
        listViewHolder.contentsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView)parent).performItemClick(view,position,R.id.contentsImageView);
            }
        });

        //ここで色指定できる評価の高さによって
        if (contents != null){
            if (contents.equals("あああ")){
                listViewHolder.listLinearLayout.setBackgroundColor(Color.rgb(127,127,255));
            }else {
                listViewHolder.listLinearLayout.setBackgroundColor(Color.rgb(255,255,255));
            }
        }
        if (postImageBitmapString!=null){
            byte[] postImageBytes = Base64.decode(postImageBitmapString,Base64.DEFAULT);
            if(postImageBytes.length!=0){
                Bitmap postImageBitmap = BitmapFactory.decodeByteArray(postImageBytes,0, postImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                listViewHolder.contentsImageView.setImageBitmap(postImageBitmap);
            }
        }

        if (userIconImageBitmapString!=null){
            byte[] iconImageBytes = Base64.decode(userIconImageBitmapString,Base64.DEFAULT);
            if(iconImageBytes.length!=0){
                Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                listViewHolder.userIconImageView.setImageBitmap(iconImageBitmap);
            }
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
