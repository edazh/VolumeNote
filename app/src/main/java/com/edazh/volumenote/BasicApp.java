package com.edazh.volumenote;

import android.app.Application;

import com.edazh.volumenote.db.AppDatabase;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class BasicApp extends Application {
    private AppExecutors mExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
