package com.edazh.volumenote.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.edazh.volumenote.R;

/**
 * Created by edazh on 2017/12/29 0029.
 */

public class DeleteDialogFragment extends DialogFragment {

    private static final String KEY_DIALOG_TITLE = "DIALOG_TITLE";
    private static final String KEY_DIALOG_MESSAGE = "DIALOG_MESSAGE";

    public interface OnDialogButtonClickListener {
        void onDialogPositiveClick(DeleteDialogFragment dialog);
    }

    private OnDialogButtonClickListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(KEY_DIALOG_TITLE);
        String message = getArguments().getString(KEY_DIALOG_MESSAGE);

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(DeleteDialogFragment.this);
                    }
                })
                .create();
    }

    public static DeleteDialogFragment newInstance(String title, String message) {

        Bundle args = new Bundle();
        args.putString(KEY_DIALOG_TITLE, title);
        args.putString(KEY_DIALOG_MESSAGE, message);
        DeleteDialogFragment fragment = new DeleteDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnPositiveButtonClickListener(OnDialogButtonClickListener listener) {
        if (mListener == null) {
            mListener = listener;
        }
    }

}
