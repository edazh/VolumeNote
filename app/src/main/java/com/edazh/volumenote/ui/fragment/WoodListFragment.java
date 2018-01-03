package com.edazh.volumenote.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edazh.volumenote.R;
import com.edazh.volumenote.databinding.WoodListFragmentBinding;
import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.WoodEntity;
import com.edazh.volumenote.model.Wood;
import com.edazh.volumenote.ui.WoodAdapter;
import com.edazh.volumenote.ui.WoodClickCallback;
import com.edazh.volumenote.viewmodel.WoodListViewModel;

import java.util.List;

/**
 * Created by edazh on 2017/12/13 0013.
 */

public class WoodListFragment extends Fragment {
    public static final String KEY_BILL_ID = "bill_id";

    private WoodListFragmentBinding mBinding;

    private WoodAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.wood_list_fragment, container, false);
        mAdapter = new WoodAdapter(mWoodClickCallback);
        mBinding.woodList.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    private final WoodClickCallback mWoodClickCallback = new WoodClickCallback() {
        @Override
        public void onClick(Wood wood) {
            //TODO: 木材点击事件
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WoodListViewModel.Factory factory = new WoodListViewModel.Factory(getActivity().getApplication(), getArguments().getString(KEY_BILL_ID));
        final WoodListViewModel viewMode = ViewModelProviders.of(this, factory).get(WoodListViewModel.class);
        subscribeToModel(viewMode);
    }

    private void subscribeToModel(final WoodListViewModel viewMode) {
        viewMode.getObservableBill().observe(this, new Observer<BillEntity>() {
            @Override
            public void onChanged(@Nullable BillEntity billEntity) {
                if (billEntity != null) {
                    mBinding.setIsLoading(false);
                    viewMode.setBill(billEntity);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });

        viewMode.getObservableWoods().observe(this, new Observer<List<WoodEntity>>() {
            @Override
            public void onChanged(@Nullable List<WoodEntity> woodEntities) {
                if (woodEntities != null) {
                    mBinding.setIsLoading(false);
                    mAdapter.setWoodList(woodEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    public static WoodListFragment forBill(String billId) {
        WoodListFragment fragment = new WoodListFragment();
        Bundle args = new Bundle();
        args.putString(KEY_BILL_ID, billId);
        fragment.setArguments(args);
        return fragment;
    }
}
