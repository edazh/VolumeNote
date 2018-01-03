package com.edazh.volumenote.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.edazh.volumenote.BasicApp;
import com.edazh.volumenote.DataRepository;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.model.Folder;

import java.util.Date;

/**
 * Created by edazh on 2017/12/19 0019.
 */

public class FolderEditorViewModel extends AndroidViewModel {

    private final DataRepository mRepository;
    public final ObservableField<String> folderName = new ObservableField<>();
    private final LiveData<FolderEntity> mObservableFolder;
    private final String mFolderId;

    public FolderEditorViewModel(@NonNull Application application, String folderId) {
        super(application);
        mRepository = ((BasicApp) application).getRepository();
        mFolderId = folderId;
        mObservableFolder = mRepository.loadFolder(folderId);
    }

    public void saveFolder() {
        if (mFolderId == null) {
            FolderEntity folder = new FolderEntity(folderName.get());
            mRepository.insertFolder(folder);
        } else {
            FolderEntity folder = new FolderEntity(mFolderId, folderName.get(), new Date(System.currentTimeMillis()));
            mRepository.updateFolder(folder);
        }
    }

    public void setFolderName(String folderName) {
        this.folderName.set(folderName);
    }

    public LiveData<FolderEntity> getObservableFolder() {
        return mObservableFolder;
    }

    public static class Factory implements ViewModelProvider.Factory {

        @NonNull
        private final Application mApplication;
        private final String mFolderId;

        public Factory(@NonNull Application application, String folderId) {
            mApplication = application;
            mFolderId = folderId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FolderEditorViewModel(mApplication, mFolderId);
        }
    }
}
