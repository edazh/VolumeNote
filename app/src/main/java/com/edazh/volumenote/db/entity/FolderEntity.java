package com.edazh.volumenote.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.edazh.volumenote.model.Folder;

import java.util.Date;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Entity(tableName = "folders")
public class FolderEntity implements Folder {
    @PrimaryKey
    private int id;
    private String name;
    private Date updatedTime;

    public FolderEntity() {
    }

    public FolderEntity(int id, String name, Date updatedTime) {
        this.id = id;
        this.name = name;
        this.updatedTime = updatedTime;
    }

    public FolderEntity(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        this.updatedTime = folder.getUpdatedTime();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
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


    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
