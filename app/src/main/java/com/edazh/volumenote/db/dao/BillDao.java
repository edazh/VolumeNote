package com.edazh.volumenote.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.model.Bill;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Dao
public interface BillDao {

    @Query("select * from bills where folderId = :folderId")
    LiveData<List<BillEntity>> loadBills(String folderId);

    @Query("select * from bills where folderId = :folderId")
    List<BillEntity> loadBillsSync(String folderId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BillEntity> bills);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BillEntity... bills);

    @Query("select * from bills where id = :billId")
    LiveData<BillEntity> loadBill(String billId);

    @Delete
    void deleteBill(BillEntity bill);

    @Delete
    void deleteBills(BillEntity... bills);

    @Update
    void updateBill(BillEntity bill);

}
