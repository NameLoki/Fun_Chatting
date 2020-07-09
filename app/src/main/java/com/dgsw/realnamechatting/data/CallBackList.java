package com.dgsw.realnamechatting.data;

import java.util.ArrayList;

public class CallBackList<T> extends ArrayList<T> {
    private OnValueChangedCallBack onValueChangedCallBack;

    public CallBackList(OnValueChangedCallBack onValueChangedCallBack) {
        this.onValueChangedCallBack = onValueChangedCallBack;
    }

    public void onValueChangeCallBack() {
        if(onValueChangedCallBack != null) {
            this.onValueChangedCallBack.OnValueChangeCallBack();
        }
    }

    @Override
    public boolean add(T t) {
        boolean val = super.add(t);
        onValueChangeCallBack();
        return val;
    }
}
