package com.edazh.volumenote.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.edazh.volumenote.model.Wood;

import java.util.UUID;

/**
 * Created by edazh on 2017/12/8 0008.
 */
@Entity(tableName = "woods", foreignKeys = @ForeignKey(entity = BillEntity.class, parentColumns = "id", childColumns = "billId", onDelete = ForeignKey.CASCADE), indices = @Index(value = "billId"))
public class WoodEntity implements Wood {
    @NonNull
    @PrimaryKey
    private String id;
    private String billId;
    private String length;
    private String diameter;
    private String type;
    private String volume;

    @Ignore
    public WoodEntity(String billId, String length, String diameter, String type, String volume) {
        this.id = UUID.randomUUID().toString();
        this.billId = billId;
        this.length = length;
        this.diameter = diameter;
        this.type = type;
        this.volume = volume;
    }

    public WoodEntity(@NonNull String id, String billId, String length, String diameter, String type, String volume) {
        this.id = id;
        this.billId = billId;
        this.length = length;
        this.diameter = diameter;
        this.type = type;
        this.volume = volume;
    }

    @NonNull
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getBillId() {
        return this.billId;
    }

    @Override
    public String getLength() {
        return this.length;
    }

    @Override
    public String getDiameter() {
        return this.diameter;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getVolume() {
        return this.volume;
    }
}
