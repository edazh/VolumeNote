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
import com.edazh.volumenote.databinding.FolderItemBinding;
import com.edazh.volumenote.databinding.FolderListFragmentBinding;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.model.Folder;
import com.edazh.volumenote.viewmodel.FolderListViewModel;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class FolderListFragment extends Fragment {
    public static final String TAG = "folderListViewModel";
    private FolderAdapter mAdapter;
    private FolderListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.folder_list_fragment, container, false);
        mAdapter = new FolderAdapter(mFolderClickCallback);
        mBinding.folderList.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FolderListViewModel viewModel = ViewModelProviders.of(this).get(FolderListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(FolderListViewModel viewModel) {
        viewModel.getFolders().observe(this, new Observer<List<FolderEntity>>() {
            @Override
            public void onChanged(@Nullable List<FolderEntity> myFolders) {
                if (myFolders != null) {
                    mBinding.setIsLoading(false);
                    mAdapter.setFolderList(myFolders);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    private final FolderClickCallback mFolderClickCallback = new FolderClickCallback() {
        @Override
        public void onClick(Folder folder) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(folder);
            }
        }
    };
}
