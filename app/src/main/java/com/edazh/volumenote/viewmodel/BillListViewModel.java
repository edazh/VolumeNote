package com.edazh.volumenote.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.edazh.volumenote.BasicApp;
import com.edazh.volumenote.DataRepository;
import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.model.Folder;

import java.util.List;

/**
 * Created by edazh on 2017/12/3 0003.
 */

public class BillListViewModel extends AndroidViewModel {

    public ObservableField<Folder> folder = new ObservableField<>();

    private final LiveData<FolderEntity> mObservableFolder;

    private final int mFolderId;

    private final LiveData<List<BillEntity>> mObservableBills;


    public BillListViewModel(@NonNull Application application, final int folderId, DataRepository repository) {
        super(application);
        mFolderId = folderId;
        mObservableFolder = repository.loadFolder(mFolderId);
        mObservableBills = repository.loadBills(mFolderId);
    }

    public LiveData<FolderEntity> getObservableFolder() {
        return mObservableFolder;
    }

    public LiveData<List<BillEntity>> getObservableBills() {
        return mObservableBills;
    }

    public void setFolder(FolderEntity folder) {
        this.folder.set(folder);
    }

    public static class Factory implements ViewModelProvider.Factory {
        @NonNull
        private final Application mApplication;

        private final int mFolderId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int folderId) {
            mApplication = application;
            mFolderId = folderId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new BillListViewModel(mApplication, mFolderId, mRepository);
        }
    }
}
