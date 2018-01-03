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
import com.edazh.volumenote.databinding.BillEditorDialogBinding;
import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.viewmodel.BillEditorViewModel;

/**
 * Created by edazh on 2017/12/23 0023.
 */

public class BillEditorDialogFragment extends DialogFragment {

    private static final String KEY_FOLDER_ID = "FOLDER_ID";

    private static final String KEY_BILL_ID = "BILL_ID";

    private BillEditorDialogBinding mBinding;

    public static BillEditorDialogFragment newInstance(String folderId, String billId) {

        Bundle args = new Bundle();
        args.putString(KEY_FOLDER_ID, folderId);
        args.putString(KEY_BILL_ID, billId);
        BillEditorDialogFragment fragment = new BillEditorDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String folderId = getArguments().getString(KEY_FOLDER_ID);
        String billId = getArguments().getString(KEY_BILL_ID);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.bill_editor_dialog, null);
        BillEditorViewModel.Factory factory = new BillEditorViewModel.Factory(getActivity().getApplication(), folderId, billId);
        final BillEditorViewModel viewModel = ViewModelProviders.of(this, factory).get(BillEditorViewModel.class);
        if (mBinding == null) {
            mBinding = DataBindingUtil.bind(view);
            mBinding.setViewModel(viewModel);
        }

        viewModel.getObservableBill().observe(this, new Observer<BillEntity>() {
            @Override
            public void onChanged(@Nullable BillEntity billEntity) {
                if (billEntity != null) {
                    viewModel.setBillName(billEntity.getName());
                    mBinding.executePendingBindings();
                    mBinding.billName.setSelection(0, billEntity.getName().length());
                }
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setTitle(billId == null ? R.string.new_bill : R.string.rename_bill)
                .setView(view)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(billId == null ? R.string.create : R.string.rename, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.saveBill();
                    }
                })
                .create();
    }
}
