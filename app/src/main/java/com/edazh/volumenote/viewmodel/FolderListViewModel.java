package com.edazh.volumenote.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.edazh.volumenote.BasicApp;
import com.edazh.volumenote.DataRepository;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.model.Folder;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class FolderListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<FolderEntity>> mObservableFolders;
    private final DataRepository mRepository;

    public FolderListViewModel(@NonNull Application application) {
        super(application);

        mObservableFolders = new MediatorLiveData<>();
        mRepository = ((BasicApp) application).getRepository();

        mObservableFolders.setValue(null);

        LiveData<List<FolderEntity>> folders = (mRepository.loadFolders());
        mObservableFolders.addSource(folders, new Observer<List<FolderEntity>>() {
            @Override
            public void onChanged(@Nullable List<FolderEntity> folderEntities) {
                mObservableFolders.setValue(folderEntities);
            }
        });

    }

    public LiveData<List<FolderEntity>> getFolders() {
        return mObservableFolders;
    }


    public void deleteFolder(Folder folder) {
        mRepository.deleteFolder((FolderEntity) folder);
    }
}
