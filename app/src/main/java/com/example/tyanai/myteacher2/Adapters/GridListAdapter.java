package com.example.tyanai.myteacher2.Adapters;

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

import com.example.tyanai.myteacher2.Models.PostData;
import com.example.tyanai.myteacher2.R;

import java.util.ArrayList;

class ViewHolder {
    ImageView imageview;
    TextView textview;
    LinearLayout linearLayout;
}

public class GridListAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<PostData> gridList = new ArrayList<PostData>();

    public GridListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ファイル名
        String postImageBitmapString = gridList.get(position).getImageBitmapString();
        String userName = gridList.get(position).getName();
        String contents = gridList.get(position).getContents();

        ViewHolder holder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            holder = new ViewHolder();
            holder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
            holder.textview = (TextView) convertView.findViewById(R.id.textview);
            holder.linearLayout = (LinearLayout)convertView.findViewById(R.id.linearLayout);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textview.setText(userName);

        //ここで色指定できる評価の高さによって
        if (contents.equals("あああ")){
            holder.linearLayout.setBackgroundColor(Color.rgb(127,127,255));
        }
        byte[] postImageBytes = Base64.decode(postImageBitmapString,Base64.DEFAULT);
        if(postImageBytes.length!=0){
            Bitmap postImageBitmap = BitmapFactory.decodeByteArray(postImageBytes,0, postImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            holder.imageview.setImageBitmap(postImageBitmap);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return gridList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setPostDataArrayList(ArrayList<PostData> list){
        gridList = list;
    }
}
