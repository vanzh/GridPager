# GridPager 动态设置行列且支持滑动的网格控件

##使用方法
* 1、项目build.gradle文件中添加远程库https://jitpack.io
```
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}

```
* 2、在Module的build.gradle文件中添加依赖
```
 compile 'com.github.vanzh:GridPager:latestVersion'
```
* 3、重写Adapter
```
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
```
* 4、给GridPager设置各项属性及Adapter
```
 mGridPager = (GridPager) findViewById(R.id.gp_data);
        mGridPager.setmNumColumn(4);
        mGridPager.setmPageSize(8);
        mGridPager.setmVerticalSpacing(10);
        mGridPager.setmHorizonticalSpacing(10);
        MyAdapter adapter = new MyAdapter(this, data, R.layout.adapter_column_item);
        mGridPager.setAdapter(adapter);
```