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
import com.edazh.volumenote.model.Bill;
import com.edazh.volumenote.model.Folder;

import java.util.List;

/**
 * Created by edazh on 2017/12/3 0003.
 */

public class BillListViewModel extends AndroidViewModel {

    public ObservableField<FolderEntity> folder = new ObservableField<>();

    private final LiveData<FolderEntity> mObservableFolder;

    private final LiveData<List<BillEntity>> mObservableBills;

    private final DataRepository mRepository;


    public BillListViewModel(@NonNull Application application, final String folderId, DataRepository repository) {
        super(application);
        mRepository = repository;
        mObservableFolder = repository.loadFolder(folderId);
        mObservableBills = repository.loadBills(folderId);
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

    public void deleteBill(Bill bill) {
        if (bill instanceof BillEntity)
            mRepository.deleteBill((BillEntity) bill);
    }

    public static class Factory implements ViewModelProvider.Factory {
        @NonNull
        private final Application mApplication;

        private final String mFolderId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, String folderId) {
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
