package com.van.gplibrary.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.van.gplibrary.R;

/**
 * Created by zp on 2017/3/27.
 */

public class GridDotPager extends LinearLayout {

    GridPager mGridPager;
    LinearLayout mLL;
    int mPageSize;
    int mNumColumn;
    int mVerticalSpacing;
    int mHorizonticalSpacing;
    boolean scrollBarEnable;
    private int mPageCount;
    private LayoutInflater inflater;
    private int curIndex;

    public GridDotPager setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
        return this;
    }

    public GridDotPager setUnSelectedColor(int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
        return this;
    }

    private int selectedColor;
    private int unSelectedColor;


    //   GridPager.IGridView adapter;
    public GridDotPager(Context context) {
        super(context);

    }


    public GridDotPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public GridDotPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs) {

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


        setOrientation(VERTICAL);
        mGridPager = new GridPager(context, attrs);
        addView(mGridPager);
        mLL = new LinearLayout(context);
        mLL.setOrientation(HORIZONTAL);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mLL.setLayoutParams(params);
        mLL.setPadding(0, 20, 0, 20);
        mLL.setHorizontalGravity(Gravity.CENTER);
        inflater = LayoutInflater.from(context);
        addView(mLL);
        selectedColor = Color.parseColor("#ff1344");
        unSelectedColor = Color.parseColor("#D4D3D3");
    }


    public void setAdapter(GridPager.IGridView adapter) {
        //this.adapter = adapter;
        mGridPager.setAdapter(adapter);
        mPageCount = adapter.getCount() % mPageSize == 0 ? adapter.getCount() / mPageSize : adapter.getCount() / mPageSize + 1;
        for (int i = 0; i < mPageCount; i++) {
            View view = inflater.inflate(R.layout.dot, null);
            GradientDrawable background = (GradientDrawable) view.findViewById(R.id.v_dot).getBackground();
            background.setColor(unSelectedColor);
            mLL.addView(view);
        }
        if (mPageCount <= 1)
            mLL.setVisibility(GONE);
        // 默认显示第一页
        curIndex = 0;

        GradientDrawable background = (GradientDrawable) mLL.getChildAt(curIndex).findViewById(R.id.v_dot).getBackground();
        background.setColor(selectedColor);
        // .setBackgroundResource(R.drawable.dot_selected);
        //  .setBackgroundDrawable();
        mGridPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 取消圆点选中
                ((GradientDrawable) mLL.getChildAt(curIndex)
                        .findViewById(R.id.v_dot).getBackground()).setColor(unSelectedColor);

                // 圆点选中
                ((GradientDrawable) mLL.getChildAt(position)
                        .findViewById(R.id.v_dot).getBackground()).setColor(selectedColor);

                // 圆点选中
                curIndex = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
