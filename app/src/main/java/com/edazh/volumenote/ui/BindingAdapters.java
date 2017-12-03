package com.edazh.volumenote.ui;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by edazh on 2017/12/1 0001.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
