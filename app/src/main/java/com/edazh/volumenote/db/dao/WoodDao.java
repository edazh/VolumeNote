package com.edazh.volumenote.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.edazh.volumenote.db.entity.WoodEntity;

import java.util.List;

/**
 * Created by edazh on 2017/12/8 0008.
 */
@Dao
public interface WoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WoodEntity> woods);

    @Query("select * from woods where billId = :billId")
    LiveData<List<WoodEntity>> loadWoods(String billId);

    @Query("select * from woods where billId = :billId")
    List<WoodEntity> loadWoodsSync(String billId);

    @Query("select * from woods where id = :id")
    WoodEntity loadWood(String id);
}
