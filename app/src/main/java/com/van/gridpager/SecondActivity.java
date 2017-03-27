package com.van.gridpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.van.gplibrary.adapter.GridViewAdapter;
import com.van.gplibrary.weight.GridDotPager;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    List<ColumnItem> data;
    private GridDotPager mGridPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initData();
        mGridPager = (GridDotPager) findViewById(R.id.gdp_data);
        mGridPager.setSelectedColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_green_dark))
                .setUnSelectedColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_green_light));
        MyAdapter adapter = new MyAdapter(this, data, R.layout.adapter_column_item);
        mGridPager.setAdapter(adapter);
    }

    private void initData() {
        data = new ArrayList<>();
        data.add(new ColumnItem("http://img3.imgtn.bdimg.com/it/u=3836041083,580593994&fm=23&gp=0.jpg", "标题3"));
        data.add(new ColumnItem("http://img2.imgtn.bdimg.com/it/u=2696042776,96554494&fm=23&gp=0.jpg", "标题4"));
        data.add(new ColumnItem("http://img1.imgtn.bdimg.com/it/u=2839151995,1130488986&fm=23&gp=0.jpg", "标题5"));
        data.add(new ColumnItem("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2477016780243579597&fm=206&gp=0.jpg", "标题6"));
        data.add(new ColumnItem("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1009239447,25444153&fm=206&gp=0.jpg", "标题7"));
        data.add(new ColumnItem("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1607469202,425090956&fm=206&gp=0.jpg", "标题8"));
        data.add(new ColumnItem("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=963551012,3660149984&fm=206&gp=0.jpg", "标题9"));
        data.add(new ColumnItem("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3772766089,912589413&fm=206&gp=0.jpg", "标题10"));
        data.add(new ColumnItem("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2350713065,2117161376&fm=206&gp=0.jpg", "标题11"));
        data.add(new ColumnItem("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=78774946,2151458707&fm=206&gp=0.jpg", "标题12"));
        data.add(new ColumnItem("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1774562268,2646553900&fm=11&gp=0.jpg", "标题13"));
        data.add(new ColumnItem("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1680198908,1791051517&fm=23&gp=0.jpg", "标题14"));
        data.add(new ColumnItem("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1039422118,3422096192&fm=23&gp=0.jpg", "标题15"));
        data.add(new ColumnItem("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=185060569,126248125&fm=23&gp=0.jpg", "标题16"));
        data.add(new ColumnItem("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2599922495,1206735901&fm=23&gp=0.jpg", "标题17"));
        data.add(new ColumnItem("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2549006498,90450730&fm=23&gp=0.jpg", "标题18"));
        data.add(new ColumnItem("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3507165996,1128973509&fm=23&gp=0.jpg", "标题19"));
        data.add(new ColumnItem("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1688113632,979567485&fm=23&gp=0.jpg", "标题20"));
    }


    private class MyAdapter extends GridViewAdapter<ColumnItem> {


        public MyAdapter(Context context, List<ColumnItem> data, int resId) {
            super(context, data, resId);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(resId, null);
                convertView.findViewById(R.id.iv_img);
                viewHolder.img = (CircleImageView) convertView.findViewById(R.id.iv_img);

                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(mData.get(position).getTitle());
            Glide.with(mContext).load(mData.get(position).getPic()).dontAnimate()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(viewHolder.img);
            return convertView;
        }

        class ViewHolder {
            CircleImageView img;
            TextView title;
        }
    }


}
