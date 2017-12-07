package com.edazh.volumenote.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.edazh.volumenote.model.Folder;

import java.util.Date;
import java.util.UUID;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Entity(tableName = "folders")
public class FolderEntity implements Folder {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private Date updatedTime;

    @Ignore
    public FolderEntity(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.updatedTime = new Date(System.currentTimeMillis());
    }

    @Ignore
    public FolderEntity(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        this.updatedTime = folder.getUpdatedTime();
    }

    public FolderEntity(@NonNull String id, String name, Date updatedTime) {
        this.id = id;
        this.name = name;
        this.updatedTime = updatedTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Date getUpdatedTime() {
        return this.updatedTime;
    }
}
