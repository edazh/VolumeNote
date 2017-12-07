package com.edazh.volumenote.model;

import java.util.Date;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public interface Bill {
    String getId();

    String getName();

    String getFolderId();

    Date getUpdatedTime();
}
