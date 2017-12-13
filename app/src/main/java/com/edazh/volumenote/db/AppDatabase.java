package com.edazh.volumenote.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.edazh.volumenote.AppExecutors;
import com.edazh.volumenote.db.converter.DateConverter;
import com.edazh.volumenote.db.dao.BillDao;
import com.edazh.volumenote.db.dao.FolderDao;
import com.edazh.volumenote.db.dao.WoodDao;
import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.db.entity.WoodEntity;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */
@Database(entities = {FolderEntity.class, BillEntity.class, WoodEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "volume-note-db";

    public abstract FolderDao folderDao();

    public abstract BillDao billDao();

    public abstract WoodDao woodDao();

    private static AppDatabase sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context, executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                //模拟长时间操作
                                addDelay();

                                AppDatabase database = AppDatabase.getInstance(appContext, executors);

                                List<FolderEntity> folders = DatabaseGenerator.generatorFolders();
                                List<BillEntity> bills = DatabaseGenerator.generatorBills(folders);
                                List<WoodEntity> woods = DatabaseGenerator.generatorWoods(bills);

                                insertData(database, folders, bills, woods);

                                //通知数据库已经创建完成
                                database.setDatabaseCreated();
                            }
                        });
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private static void deleteDatabase(Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            context.deleteDatabase(DATABASE_NAME);
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDatabase database, final List<FolderEntity> folders, final List<BillEntity> bills, final List<WoodEntity> woods) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.folderDao().insertAll(folders);
                database.billDao().insertAll(bills);
                database.woodDao().insertAll(woods);
            }
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
