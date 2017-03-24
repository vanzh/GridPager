package com.van.gridpager;

import com.van.gplibrary.bean.BaseItem;

/**
 * Created by zp on 2017/3/23.
 */

public class ColumnItem extends BaseItem {
    String pic;
    String title;

    public ColumnItem(String pic, String title) {
        this.pic = pic;
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
