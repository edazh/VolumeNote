package com.edazh.volumenote.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.edazh.volumenote.model.Folder;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Entity(tableName = "folders")
public class FolderEntity implements Folder {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @Ignore
    private int billsCount;

    public FolderEntity() {
    }

    public FolderEntity(int id, String name, int billsCount) {
        this.id = id;
        this.name = name;
        this.billsCount = billsCount;
    }

    public FolderEntity(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        this.billsCount = folder.getBillsCount();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBillsCount(int billsCount) {
        this.billsCount = billsCount;
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
    public int getBillsCount() {
        return 0;
    }
}
