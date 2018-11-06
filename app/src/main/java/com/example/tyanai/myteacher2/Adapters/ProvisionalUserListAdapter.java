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

import com.example.tyanai.myteacher2.Models.ProvisionalUserData;
import com.example.tyanai.myteacher2.R;

import java.util.ArrayList;

class ProvisionalUserViewHolder{

    ImageView iconImageView;
    TextView userNameTextView;
    LinearLayout listLinearLayout;
}

public class ProvisionalUserListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<ProvisionalUserData> ProvisionalUserArrayList = new ArrayList<ProvisionalUserData>();

    public ProvisionalUserListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ファイル名
        String iconBitmapString = ProvisionalUserArrayList.get(position).getIconBitmapString();
        String userName = ProvisionalUserArrayList.get(position).getName();

        ProvisionalUserViewHolder provisionalUserViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            provisionalUserViewHolder = new ProvisionalUserViewHolder();
            provisionalUserViewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.FFListIcon);
            provisionalUserViewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.FFListName);
            provisionalUserViewHolder.listLinearLayout = (LinearLayout)convertView.findViewById(R.id.listLinearLayout);
            convertView.setTag(provisionalUserViewHolder);
        }
        else {
            provisionalUserViewHolder = (ProvisionalUserViewHolder) convertView.getTag();
        }
        if (userName != null){
            provisionalUserViewHolder.userNameTextView.setText(userName);
        }


        byte[] iconImageBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
        if(iconImageBytes.length!=0){
            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            provisionalUserViewHolder.iconImageView.setImageBitmap(iconImageBitmap);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return ProvisionalUserArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setProvisionalUserArrayList(ArrayList<ProvisionalUserData> list){
        ProvisionalUserArrayList = list;
    }



}
