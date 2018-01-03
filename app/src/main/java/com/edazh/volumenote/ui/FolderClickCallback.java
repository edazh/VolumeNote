package com.edazh.volumenote.ui;

import android.view.View;

import com.edazh.volumenote.model.Folder;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public interface FolderClickCallback {
    void onClick(Folder folder);

    boolean onLongClick(View view, Folder folder);
}
