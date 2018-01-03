package com.edazh.volumenote.ui;

import android.view.View;

import com.edazh.volumenote.model.Bill;

/**
 * Created by edazh on 2017/12/3 0003.
 */

public interface BillClickCallback {
    void onClick(Bill bill);

    boolean onLongClick(View view, Bill bill);
}
