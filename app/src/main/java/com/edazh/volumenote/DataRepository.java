package com.edazh.volumenote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.edazh.volumenote.db.AppDatabase;
import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.db.entity.WoodEntity;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class DataRepository {
    private static DataRepository sInstance;
    private final AppDatabase mDatabase;
    private MediatorLiveData<List<FolderEntity>> mObservableFolders;

    private DataRepository(AppDatabase database) {
        mDatabase = database;
        mObservableFolders = new MediatorLiveData<>();

        mObservableFolders.addSource(mDatabase.folderDao().loadFolders(), new Observer<List<FolderEntity>>() {
            @Override
            public void onChanged(@Nullable List<FolderEntity> folderEntities) {
                if (mDatabase.getDatabaseCreated().getValue() != null) {
                    mObservableFolders.postValue(folderEntities);
                }
            }
        });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<FolderEntity>> loadFolders() {
        return mObservableFolders;
    }

    public LiveData<FolderEntity> loadFolder(String folderId) {
        return mDatabase.folderDao().loadFolder(folderId);
    }

    public LiveData<List<BillEntity>> loadBills(String folderId) {
        return mDatabase.billDao().loadBills(folderId);
    }

    public LiveData<BillEntity> loadBill(String billId) {
        return mDatabase.billDao().loadBill(billId);
    }

    public LiveData<List<WoodEntity>> loadWoods(String billId) {
        return mDatabase.woodDao().loadWoods(billId);
    }
}
