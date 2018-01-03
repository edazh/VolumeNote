package com.edazh.volumenote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.edazh.volumenote.db.AppDatabase;
import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.db.entity.WoodEntity;
import com.edazh.volumenote.model.Folder;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class DataRepository {
    private static DataRepository sInstance;
    private final AppDatabase mDatabase;
    private final AppExecutors mExecutors;
    private MediatorLiveData<List<FolderEntity>> mObservableFolders;

    private DataRepository(AppDatabase database, AppExecutors executors) {
        mDatabase = database;
        mExecutors = executors;
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

    public static DataRepository getInstance(final AppDatabase database, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, executors);
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

    /**
     * 保存Folder
     *
     * @param folder
     */
    public void insertFolder(final FolderEntity folder) {
        Runnable insertThread = new Runnable() {
            @Override
            public void run() {
                mDatabase.folderDao().insert(folder);
            }
        };
        mExecutors.diskIO().execute(insertThread);
    }

    public void insertBill(final BillEntity bill) {
        Runnable insertThread = new Runnable() {
            @Override
            public void run() {
                mDatabase.billDao().insert(bill);
            }
        };
        mExecutors.diskIO().execute(insertThread);
    }

    public void deleteFolder(final FolderEntity folder) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mDatabase.folderDao().deleteFolder(folder);
            }
        };
        mExecutors.diskIO().execute(deleteRunnable);
    }

    public void deleteBill(final BillEntity bill) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mDatabase.billDao().deleteBill(bill);
            }
        };
        mExecutors.diskIO().execute(deleteRunnable);
    }

    public void updateFolder(final FolderEntity folder) {
        Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                mDatabase.folderDao().updateFolder(folder);
            }
        };
        mExecutors.diskIO().execute(updateRunnable);
    }

    public void updateBill(final BillEntity bill) {
        Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                mDatabase.billDao().updateBill(bill);
            }
        };
        mExecutors.diskIO().execute(updateRunnable);
    }
}
