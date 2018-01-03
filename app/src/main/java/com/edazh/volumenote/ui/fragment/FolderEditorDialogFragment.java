package com.edazh.volumenote.ui.fragment;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.edazh.volumenote.R;
import com.edazh.volumenote.databinding.FolderEditorDialogBinding;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.viewmodel.FolderEditorViewModel;

/**
 * Created by edazh on 2017/12/18 0018.
 */

public class FolderEditorDialogFragment extends DialogFragment {

    private static final String KEY_FOLDER_ID = "FOLDER_ID";
    private FolderEditorDialogBinding mBinding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.folder_editor_dialog, null);
        if (mBinding == null) {
            mBinding = DataBindingUtil.bind(view);
        }
        String folderId = getArguments().getString(KEY_FOLDER_ID);
        FolderEditorViewModel.Factory factory = new FolderEditorViewModel.Factory(getActivity().getApplication(), folderId);
        final FolderEditorViewModel viewModel = ViewModelProviders.of(this, factory).get(FolderEditorViewModel.class);
        viewModel.getObservableFolder().observe(this, new Observer<FolderEntity>() {
            @Override
            public void onChanged(@Nullable FolderEntity folderEntity) {
                if (folderEntity != null) {
                    viewModel.setFolderName(folderEntity.getName());
                    mBinding.setViewModel(viewModel);
                    mBinding.executePendingBindings();
                    mBinding.folderName.setSelection(0, folderEntity.getName().length());
                }
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setTitle(folderId==null?R.string.new_folder:R.string.rename_folder)
                .setView(mBinding.getRoot())
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(folderId == null ? R.string.create : R.string.rename, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.saveFolder();
                    }
                })
                .create();
    }

    public static FolderEditorDialogFragment newInstance(String folderId) {
        Bundle args = new Bundle();
        args.putString(KEY_FOLDER_ID, folderId);
        FolderEditorDialogFragment fragment = new FolderEditorDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
