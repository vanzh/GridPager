package com.van.gplibrary.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.van.gplibrary.R;
import com.van.gplibrary.utils.DensityUtils;

/**
 * 使ViewPager透明，对外只用GridView的数据
 * Created by zp on 2017/3/23.
 */

public class GridPager extends ViewPager {

    private final String TAG = "GridPager";

    //条目数
    private int mItemCount;
    //每页显示的条目数
    private int mPageSize = 4;
    //每页显示列数
    private int mNumColumn = 4;
    //条目水平间距
    private int mVerticalSpacing = 2;
    //条目垂直间距
    private int mHorizonticalSpacing = 2;
    //GridView是否可滚动
    private boolean scrollBarEnable;

    //总页数
    private int mPageCount;
    private PagerAdapter mPageAdapter;
    //点击监听
    private OnItemListener mItemClickListener;
    private AbsListView.LayoutParams mParams;

    public GridPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        mParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView
                .LayoutParams.WRAP_CONTENT);
        //如果attrs不为空，就从xml布局文件中获取自定义attrs参数
        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GridPager);
            mPageSize = ta.getInteger(R.styleable.GridPager_page_size, 4);
            mNumColumn = ta.getInteger(R.styleable.GridPager_num_columns, 4);
            mVerticalSpacing = (int) ta.getDimension(R.styleable
                    .GridPager_vertical_spacing, 0.5f);
            mHorizonticalSpacing = (int) ta.getDimension(R.styleable
                    .GridPager_horizontal_spacing, 0.5f);
            scrollBarEnable = ta.getBoolean(R.styleable.GridPager_scroll_bar_enable,
                    false);
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + getPaddingBottom() + getPaddingTop(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setmItemCount(int mItemCount) {
        this.mItemCount = mItemCount;
    }

    public void setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }

    public void setmNumColumn(int mNumColumn) {
        this.mNumColumn = mNumColumn;
    }

    public void setmVerticalSpacing(int mVerticalSpacing) {
        this.mVerticalSpacing = mVerticalSpacing;
    }

    public void setmHorizonticalSpacing(int mHorizonticalSpacing) {
        this.mHorizonticalSpacing = mHorizonticalSpacing;
    }

    public void setScrollBarEnable(boolean scrollBarEnable) {
        this.scrollBarEnable = scrollBarEnable;
    }

    public void setmPageCount(int mPageCount) {
        this.mPageCount = mPageCount;
    }

    public void setmItemClickListener(OnItemListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    /**
     * 设置适配器
     *
     * @param adapter 实现了IGridView的Adapter
     */
    public void setAdapter(IGridView adapter) {
        if (null == adapter) {
            throw new IllegalArgumentException("适配器不能为空");
        }
        mItemCount = adapter.getCount();

        if (mItemCount <= 0) {
            throw new RuntimeException("条目总数必须大于0");
        }

        //给ViewPager设置适配器
        mPageAdapter = new ViewPagerAdapter(adapter);
        setAdapter(mPageAdapter);
    }


    /**
     * ViewPager的Adapter
     */
    private class ViewPagerAdapter extends PagerAdapter {

        private IGridView iGridView;

        public ViewPagerAdapter(IGridView iGridView) {
            this.iGridView = iGridView;

        }

        @Override
        public int getCount() {
            mPageCount = mItemCount % mPageSize == 0 ? mItemCount / mPageSize : mItemCount / mPageSize + 1;
            Log.d(TAG, mPageCount + "");
            return mPageCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int pageIndex) {
            MGridView gv = new MGridView(getContext());
            gv.setLayoutParams(mParams);
            gv.setNumColumns(mNumColumn);
            gv.setHorizontalSpacing(DensityUtils.dp2px(getContext(), mHorizonticalSpacing));
            gv.setVerticalSpacing(DensityUtils.dp2px(getContext(), mVerticalSpacing));

            container.addView(gv);
            setAdapter(gv, pageIndex);
            gv.setTag(pageIndex);

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                    if (null != mItemClickListener) {
                        int pos = index + pageIndex * mPageSize;
                        mItemClickListener.onItemClick(parent, view, pos);
                    }
                }
            });
            return gv;
        }

        /**
         * @param gv
         * @param pageIndex ViewPager当前页
         */
        private void setAdapter(GridView gv, int pageIndex) {
            if (gv.getAdapter() instanceof BaseAdapter) {
                BaseAdapter adapter = (BaseAdapter) gv.getAdapter();
                if (null != adapter) {
                    adapter.notifyDataSetChanged();
                    return;
                }
            }

            gv.setAdapter(new GVAdapter(iGridView, pageIndex));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            //无论如何都刷新
            return POSITION_NONE;
        }
    }

    /**
     * 一页GridView的Adapter
     */
    private class GVAdapter<T> extends BaseAdapter {

        private IGridView<T> mAdapter;
        private int mPageIndex;

        public GVAdapter(IGridView<T> mAdapter, int pageIndex) {

            this.mAdapter = mAdapter;
            mPageIndex = pageIndex;
        }


        @Override
        public int getCount() {
            int count = mItemCount;
            if (mPageIndex < mPageCount - 1) {
                //不是最后一页，按config中pageSize显示
                count = mPageSize;
            } else {
                //用总条目数量-已经显示的条目数量
                //已经显示的条目数量=每页显示条目数量*已经显示的页数
                count -= mPageSize * (mPageCount - 1);
            }
            return count;
        }

        @Override
        public T getItem(int position) {
            return mAdapter.getItem(position + mPageIndex * mPageSize);
        }

        @Override
        public long getItemId(int position) {
            return mAdapter.getItemId(position + mPageIndex * mPageSize);
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            return mAdapter.getView(mPageIndex * mPageSize + index, convertView, parent);
        }
    }


    /**
     * 联通单个页面的GVAdapter及所有数据的桥梁
     *
     * @param <T>
     */
    public interface IGridView<T> {

        int getCount();

        T getItem(int position);

        long getItemId(int position);

        View getView(int position, View convertView, ViewGroup parent);
    }

    public interface OnItemListener {
        void onItemClick(AdapterView<?> parent, View view, int position);
    }

}
