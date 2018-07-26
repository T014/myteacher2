package com.example.tyanai.myteacher2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

class ViewHolder {
    ImageView gridImageView;
}
/*
public class GridListAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<ImageData> gridList = new ArrayList<ImageData>();

    public GridListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ファイル名
        String mFilepath = gridList.get(position).getFileName();

        ViewHolder holder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            holder = new ViewHolder();
            holder.gridImageView = (ImageView) convertView.findViewById(R.id.gridImageview);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bitmap bmp = BitmapFactory.decodeFile(mFilepath);
        holder.gridImageView.setImageBitmap(bmp);

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
    public void setImageDataArrayList(ArrayList<ImageData> list){
        gridList = list;
    }
}*/
