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
import com.edazh.volumenote.db.entity.WoodEntity;
import com.edazh.volumenote.model.Bill;

import java.util.List;

/**
 * Created by edazh on 2017/12/13 0013.
 */

public class WoodListViewModel extends AndroidViewModel {
    public ObservableField<Bill> bill = new ObservableField<>();
    private final LiveData<BillEntity> mObservableBill;
    private final String mBillId;
    private final LiveData<List<WoodEntity>> mObservableWoods;

    public WoodListViewModel(@NonNull Application application, String billId, DataRepository repository) {
        super(application);
        mBillId = billId;
        mObservableBill = repository.loadBill(billId);
        mObservableWoods = repository.loadWoods(billId);
    }

    public LiveData<BillEntity> getObservableBill() {
        return mObservableBill;
    }

    public LiveData<List<WoodEntity>> getObservableWoods() {
        return mObservableWoods;
    }

    public void setBill(Bill bill) {
        this.bill.set(bill);
    }

    public static class Factory implements ViewModelProvider.Factory {
        @NonNull
        private final Application mApplication;
        private final String mBillId;
        private final DataRepository mRepository;

        public Factory(@NonNull Application application, String billId) {
            mApplication = application;
            mBillId = billId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new WoodListViewModel(mApplication, mBillId, mRepository);
        }
    }
}
