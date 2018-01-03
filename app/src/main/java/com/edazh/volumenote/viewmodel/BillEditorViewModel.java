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
import com.edazh.volumenote.model.Bill;

/**
 * Created by edazh on 2017/12/27 0027.
 */

public class BillEditorViewModel extends AndroidViewModel {
    public final ObservableField<String> billName = new ObservableField<>();
    private final String mFolderId;
    private final String mBillId;
    private final DataRepository mRepository;

    private final LiveData<BillEntity> mObservableBill;

    public BillEditorViewModel(@NonNull Application application, String folderId, String billId, DataRepository repository) {
        super(application);
        mFolderId = folderId;
        mBillId = billId;
        mRepository = repository;
        mObservableBill = repository.loadBill(billId);
    }

    public void setBillName(String name) {
        this.billName.set(name);
    }

    public void saveBill() {
        if (mBillId == null) {
            BillEntity bill = new BillEntity(mFolderId, this.billName.get());
            mRepository.insertBill(bill);

        } else {
            BillEntity bill = new BillEntity(mBillId, mFolderId, this.billName.get());
            mRepository.updateBill(bill);
        }
    }

    public LiveData<BillEntity> getObservableBill() {
        return mObservableBill;
    }

    public static class Factory implements ViewModelProvider.Factory {
        @NonNull
        private final Application mApplication;
        private final DataRepository mRepository;
        private final String mFolderId;
        private final String mBillId;

        public Factory(@NonNull Application application, String folderId, String billId) {
            mFolderId = folderId;
            mBillId = billId;
            mApplication = application;
            mRepository = ((BasicApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new BillEditorViewModel(mApplication, mFolderId, mBillId, mRepository);
        }
    }
}
