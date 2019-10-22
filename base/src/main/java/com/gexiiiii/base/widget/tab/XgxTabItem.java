package com.gexiiiii.base.widget.tab;

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:15
 * description :
 */
public class XgxTabItem {
    public String text;
    public int imageRes;

    public XgxTabItem() {
    }

    public XgxTabItem(String text, int imageRes) {
        this.text = text;
        this.imageRes = imageRes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}