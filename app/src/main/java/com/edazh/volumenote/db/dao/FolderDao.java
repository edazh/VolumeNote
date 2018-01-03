package com.edazh.volumenote.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.edazh.volumenote.db.entity.FolderEntity;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Dao
public interface FolderDao {

    @Query("select * from folders")
    LiveData<List<FolderEntity>> loadFolders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FolderEntity> folders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FolderEntity... folders);

    @Query("select * from folders where id = :folderId")
    LiveData<FolderEntity> loadFolder(String folderId);

    @Query("select * from folders where id = :folderId")
    FolderEntity loadFolderSync(String folderId);

    @Delete
    void deleteFolder(FolderEntity folder);

    @Delete
    void deleteFolders(FolderEntity... folders);

    @Update
    void updateFolder(FolderEntity folder);
}
