package com.edazh.volumenote.ui.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.edazh.volumenote.R;
import com.edazh.volumenote.databinding.FolderListFragmentBinding;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.model.Folder;
import com.edazh.volumenote.ui.FolderAdapter;
import com.edazh.volumenote.ui.FolderClickCallback;
import com.edazh.volumenote.ui.activity.MainActivity;
import com.edazh.volumenote.viewmodel.FolderListViewModel;

import java.util.List;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class FolderListFragment extends Fragment {
    public static final String TAG = "folderListViewModel";
    private FolderAdapter mAdapter;
    private FolderListFragmentBinding mBinding;
    private FolderListViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.folder_list_fragment, container, false);
        mAdapter = new FolderAdapter(mFolderClickCallback);
        mBinding.folderList.setAdapter(mAdapter);
        ((MainActivity) getActivity()).setSupportActionBar(mBinding.toolbar);
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFolderEditorDialog(null);
            }
        });
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FolderListViewModel.class);
        subscribeUi(mViewModel);
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

        @Override
        public boolean onLongClick(View view, Folder folder) {
            showPopuMenu(view, folder);
            return true;
        }
    };

    private void showPopuMenu(View view, final Folder folder) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.rename:
                        showFolderEditorDialog(folder.getId());
                        break;
                    case R.id.delete:
                        showDeleteFolderDialog(folder);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        popupMenu.setGravity(GravityCompat.END);
        popupMenu.show();
    }

    private void showFolderEditorDialog(@Nullable String folderId) {
        FolderEditorDialogFragment dialogFragment = FolderEditorDialogFragment.newInstance(folderId);
        dialogFragment.show(getFragmentManager(), "folderEditor");
    }

    private void showDeleteFolderDialog(final Folder folder) {
        DeleteDialogFragment dialog = DeleteDialogFragment.newInstance("删除文件夹", "你确定要删除文件夹 " + folder.getName() + " ?");
        dialog.setOnPositiveButtonClickListener(new DeleteDialogFragment.OnDialogButtonClickListener() {
            @Override
            public void onDialogPositiveClick(DeleteDialogFragment dialog) {
                mViewModel.deleteFolder(folder);
            }
        });

        dialog.show(getFragmentManager(), "deleteFolder");

    }

    public static FolderListFragment newInstance() {
        return new FolderListFragment();
    }
}
