package com.edazh.volumenote.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.edazh.volumenote.model.Bill;

import java.util.Date;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Entity(tableName = "bills", foreignKeys =
        {@ForeignKey(entity = FolderEntity.class, parentColumns = "id", childColumns = "folderId", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "folderId")})
public class BillEntity implements Bill {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int folderId;
    private String name;
    private Date updatedTime;

    public BillEntity() {
    }

    public BillEntity(int id, int folderId, String name, Date updatedTime) {
        this.id = id;
        this.folderId = folderId;
        this.name = name;
        this.updatedTime = updatedTime;
    }

    public BillEntity(Bill bill) {
        this.id = bill.getId();
        this.folderId = bill.getFolderId();
        this.name = bill.getName();
        this.updatedTime = bill.getUpdatedTime();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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
    public int getFolderId() {
        return this.folderId;
    }

    @Override
    public Date getUpdatedTime() {
        return this.updatedTime;
    }
}
