package com.edazh.volumenote.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.edazh.volumenote.model.Bill;

import java.util.Date;
import java.util.UUID;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Entity(tableName = "bills", foreignKeys =
        {@ForeignKey(entity = FolderEntity.class, parentColumns = "id", childColumns = "folderId", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "folderId")})
public class BillEntity implements Bill {
    @PrimaryKey
    @NonNull
    private String id;
    private String folderId;
    private String name;
    private Date updatedTime;

    @Ignore
    public BillEntity(String folderId, String name) {
        this(UUID.randomUUID().toString(), folderId, name);
    }

    @Ignore
    public BillEntity(@NonNull String id, String folderId, String name) {
        this(id, folderId, name, new Date(System.currentTimeMillis()));
    }

    @Ignore
    public BillEntity(Bill bill) {
        this.id = bill.getId();
        this.folderId = bill.getFolderId();
        this.name = bill.getName();
        this.updatedTime = bill.getUpdatedTime();
    }

    public BillEntity(@NonNull String id, String folderId, String name, Date updatedTime) {
        this.id = id;
        this.folderId = folderId;
        this.name = name;
        this.updatedTime = updatedTime;
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
    public String getFolderId() {
        return this.folderId;
    }

    @Override
    public Date getUpdatedTime() {
        return this.updatedTime;
    }
}
