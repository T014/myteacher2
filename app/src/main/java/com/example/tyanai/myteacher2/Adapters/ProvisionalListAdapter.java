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

import com.example.tyanai.myteacher2.Models.ProvisionalKeyData;
import com.example.tyanai.myteacher2.R;

import java.util.ArrayList;

class ProvisionalListViewHolder{
    ImageView iconImageView;
    TextView userNameTextView;
    ImageView contentImageView;
    TextView contentTextView;
    TextView countTextView;
    RelativeLayout listRelativeLayout;
}

public class ProvisionalListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<ProvisionalKeyData> provisionalKeyDataArrayList = new ArrayList<ProvisionalKeyData>();

    public ProvisionalListAdapter (Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ファイル名
        String iconBitmapString = provisionalKeyDataArrayList.get(position).getIconBitmapString();
        String userName = provisionalKeyDataArrayList.get(position).getName();
        String content = provisionalKeyDataArrayList.get(position).getContent();
        String count = provisionalKeyDataArrayList.get(position).getCount();
        String contentBitmapString = provisionalKeyDataArrayList.get(position).getContentBitmapString();

        ProvisionalListViewHolder provisionalListViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            provisionalListViewHolder = new ProvisionalListViewHolder();
            provisionalListViewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            provisionalListViewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            provisionalListViewHolder.contentImageView = (ImageView) convertView.findViewById(R.id.contentImageView);
            provisionalListViewHolder.contentTextView = (TextView) convertView.findViewById(R.id.contentTextView);
            provisionalListViewHolder.countTextView = (TextView) convertView.findViewById(R.id.countTextView);
            provisionalListViewHolder.listRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.listRelativeLayout);
            convertView.setTag(provisionalListViewHolder);
        } else {
            provisionalListViewHolder = (ProvisionalListViewHolder) convertView.getTag();
        }
        if (userName != null){
            provisionalListViewHolder.userNameTextView.setText(userName);
        }
        if (content != null){
            provisionalListViewHolder.contentTextView.setText(content);
        }
        if (count!=null){
            provisionalListViewHolder.countTextView.setText("仮契約"+count+"人");
        }
        byte[] iconImageBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
        if(iconImageBytes.length>5){
            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            provisionalListViewHolder.iconImageView.setImageBitmap(iconImageBitmap);
        }
        byte[] contentImageBytes = Base64.decode(contentBitmapString,Base64.DEFAULT);
        if(contentImageBytes.length>5){
            Bitmap contentImageBitmap = BitmapFactory.decodeByteArray(contentImageBytes,0, contentImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            provisionalListViewHolder.contentImageView.setImageBitmap(contentImageBitmap);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return provisionalKeyDataArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setProvisionalKeyDataArrayList(ArrayList<ProvisionalKeyData> list){
        provisionalKeyDataArrayList = list;
    }
}