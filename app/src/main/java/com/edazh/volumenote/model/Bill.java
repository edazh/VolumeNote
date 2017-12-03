package com.edazh.volumenote.model;

import java.util.Date;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public interface Bill {
    int getId();

    String getName();

    String getDescription();

    int getFolderId();

    Date getUpdatedTime();
}
