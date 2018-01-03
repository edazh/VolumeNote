package com.edazh.volumenote.ui.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.edazh.volumenote.R;
import com.edazh.volumenote.databinding.BillListFragmentBinding;
import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.model.Bill;
import com.edazh.volumenote.ui.BillAdapter;
import com.edazh.volumenote.ui.BillClickCallback;
import com.edazh.volumenote.ui.activity.MainActivity;
import com.edazh.volumenote.viewmodel.BillEditorViewModel;
import com.edazh.volumenote.viewmodel.BillListViewModel;

import java.util.List;

/**
 * Created by edazh on 2017/12/3 0003.
 */

public class BillListFragment extends Fragment {

    private static final String KEY_FOLDER_ID = "folder_id";

    private BillListFragmentBinding mBinding;

    private BillAdapter mBillAdapter;

    private BillListViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.bill_list_fragment, container, false);
        mBillAdapter = new BillAdapter(mBillClickCallback);
        mBinding.billList.setAdapter(mBillAdapter);
        ((MainActivity) getActivity()).setSupportActionBar(mBinding.toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBillEditorDialog(null);
            }
        });
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

        @Override
        public boolean onLongClick(View view, Bill bill) {
            showPopuMenu(view, bill);
            return true;
        }
    };

    private void showPopuMenu(final View view, final Bill bill) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view, Gravity.END);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.rename:
                        showBillEditorDialog(bill.getId());
                        break;
                    case R.id.delete:
                        showDeleteBillDialog(bill);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void showBillEditorDialog(String billId) {
        BillEditorDialogFragment dialogFragment = BillEditorDialogFragment.newInstance(getFolderId(), billId);
        dialogFragment.show(getFragmentManager(), "bill_editor");
    }


    private void showDeleteBillDialog(final Bill bill) {
        DeleteDialogFragment dialog = DeleteDialogFragment.newInstance("删除账单", "你确定要删除账单 " + bill.getName() + " ?");
        dialog.setOnPositiveButtonClickListener(new DeleteDialogFragment.OnDialogButtonClickListener() {
            @Override
            public void onDialogPositiveClick(DeleteDialogFragment dialog) {
                mViewModel.deleteBill(bill);
            }
        });

        dialog.show(getFragmentManager(), "deleteBill");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BillListViewModel.Factory factory = new BillListViewModel.Factory(getActivity().getApplication(), getFolderId());
        mViewModel = ViewModelProviders.of(this, factory).get(BillListViewModel.class);
        subscribeToModel();
    }

    private void subscribeToModel() {
        mViewModel.getObservableFolder().observe(this, new Observer<FolderEntity>() {
            @Override
            public void onChanged(@Nullable FolderEntity folderEntity) {
                if (folderEntity != null) {
                    mBinding.setIsLoading(false);
                    mBinding.collapsingToolbar.setTitle(folderEntity.getName());
                    mViewModel.setFolder(folderEntity);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });

        mViewModel.getObservableBills().observe(this, new Observer<List<BillEntity>>() {
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

    private String getFolderId() {
        return getArguments().getString(KEY_FOLDER_ID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
