package com.edazh.volumenote.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edazh.volumenote.R;
import com.edazh.volumenote.databinding.BillListFragmentBinding;
import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.model.Bill;
import com.edazh.volumenote.viewmodel.BillListViewModel;

import java.util.List;

/**
 * Created by edazh on 2017/12/3 0003.
 */

public class BillListFragment extends Fragment {
    private static final String KEY_FOLDER_ID = "folder_id";

    private BillListFragmentBinding mBinding;

    private BillAdapter mBillAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.bill_list_fragment, container, false);
        mBillAdapter = new BillAdapter(mBillClickCallback);
        mBinding.billList.setAdapter(mBillAdapter);
        return mBinding.getRoot();
    }

    private final BillClickCallback mBillClickCallback = new BillClickCallback() {
        @Override
        public void onClick(Bill bill) {
            //TODO:1-账单点击事件
            if (getActivity().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(bill);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BillListViewModel.Factory factory = new BillListViewModel.Factory(getActivity().getApplication(), getArguments().getString(KEY_FOLDER_ID));
        final BillListViewModel viewModel = ViewModelProviders.of(this, factory).get(BillListViewModel.class);

        subscribeToModel(viewModel);
    }

    private void subscribeToModel(final BillListViewModel viewModel) {
        viewModel.getObservableFolder().observe(this, new Observer<FolderEntity>() {
            @Override
            public void onChanged(@Nullable FolderEntity folderEntity) {
                if (folderEntity != null) {
                    mBinding.setIsLoading(false);
                    viewModel.setFolder(folderEntity);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });

        viewModel.getObservableBills().observe(this, new Observer<List<BillEntity>>() {
            @Override
            public void onChanged(@Nullable List<BillEntity> billEntities) {
                if (billEntities != null) {
                    mBinding.setIsLoading(false);
                    mBillAdapter.setBillList(billEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    public static BillListFragment forFolder(String folderId) {
        BillListFragment fragment = new BillListFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FOLDER_ID, folderId);
        fragment.setArguments(args);
        return fragment;
    }
}
