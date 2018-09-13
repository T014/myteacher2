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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

class FavListViewHolder {

    TextView favListTextView;
    ImageView favImageView;

    LinearLayout favListLinearLayout;
}

public class NotificationFavListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<NotificationFavData> favUserArrayList = new ArrayList<NotificationFavData>();

    public NotificationFavListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //ファイル名
        String userName = favUserArrayList.get(position).getUserName();
        String userIconImageBitmapString = favUserArrayList.get(position).getIconBitmapString();
        String kind = favUserArrayList.get(position).getKind();
        String kindDetail = favUserArrayList.get(position).getKindDetail();

        FavListViewHolder favListViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            favListViewHolder = new FavListViewHolder();
            favListViewHolder.favListTextView = (TextView) convertView.findViewById(R.id.favListTextView);
            favListViewHolder.favImageView = (ImageView) convertView.findViewById(R.id.favImageView);
            favListViewHolder.favListLinearLayout = (LinearLayout)convertView.findViewById(R.id.favListLinearLayout);
            convertView.setTag(favListViewHolder);
        } else {
            favListViewHolder = (FavListViewHolder) convertView.getTag();
        }
        if (kind.equals("いいね")){
            if (userName != null){
                favListViewHolder.favListTextView.setText(userName+"さんがあなたの投稿にいいねしました。");
            }
        }else if (kind.equals("購入")){
            if (kind.equals("許可")){
                favListViewHolder.favListTextView.setText(userName+"さんがあなたの申請を許可しました。");
            }else if (kindDetail.equals("拒否")) {
                favListViewHolder.favListTextView.setText(userName+"さんがあなたの申請を拒否しました。");
            }else if (kindDetail.equals("リクエスト")){
                favListViewHolder.favListTextView.setText(userName+"さんがあなたの投稿に購入リクエストを送りました。");
            }else if (kindDetail.equals("キャンセル")){
                favListViewHolder.favListTextView.setText(userName+"さんが購入リクエストをキャンセルしました。");
            }
        }else if (kind.equals("その他")){
            favListViewHolder.favListTextView.setText(userName+"さんがあなたの検索履歴に近い投稿をしました。");
        }



        favListViewHolder.favImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView)parent).performItemClick(view,position,R.id.favImageView);
            }
        });

        if (userIconImageBitmapString!=null){
            byte[] iconImageBytes = Base64.decode(userIconImageBitmapString,Base64.DEFAULT);
            if(iconImageBytes.length!=0){
                Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                favListViewHolder.favImageView.setImageBitmap(iconImageBitmap);
            }
        }


        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return favUserArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setFavUserArrayList(ArrayList<NotificationFavData> list){
        favUserArrayList = list;
    }







}