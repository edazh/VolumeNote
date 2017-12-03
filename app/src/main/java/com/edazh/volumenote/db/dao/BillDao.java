package com.edazh.volumenote.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.model.Bill;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Dao
public interface BillDao {

    @Query("select * from bills where folderId = :folderId")
    LiveData<List<BillEntity>> loadBills(int folderId);

    @Query("select * from bills where folderId = :folderId")
    List<BillEntity> loadBillsSync(int folderId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAll(List<BillEntity> bills);

    @Query("select * from bills where id = :billId")
    LiveData<BillEntity> loadBill(int billId);

    @Query("select count(*) as 'number of bills' from bills where folderId = :folderId")
    int loadBillsCount(int folderId);

}
