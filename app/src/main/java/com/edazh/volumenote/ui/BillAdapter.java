package com.edazh.volumenote.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.edazh.volumenote.R;
import com.edazh.volumenote.databinding.BillItemBinding;
import com.edazh.volumenote.model.Bill;

import java.util.List;
import java.util.Objects;

/**
 * Created by edazh on 2017/12/3 0003.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private List<? extends Bill> mBillList;

    @Nullable
    private final BillClickCallback mBillClickCallback;

    public BillAdapter(@Nullable BillClickCallback billClickCallback) {
        mBillClickCallback = billClickCallback;
    }

    public void setBillList(final List<? extends Bill> billList) {
        if (mBillList == null) {
            mBillList = billList;
            notifyItemRangeInserted(0, billList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mBillList.size();
                }

                @Override
                public int getNewListSize() {
                    return billList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mBillList.get(oldItemPosition).getId() == billList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Bill newBill = billList.get(newItemPosition);
                    Bill oldBill = mBillList.get(oldItemPosition);

                    return newBill.getId() == oldBill.getId()
                            && newBill.getFolderId() == oldBill.getFolderId()
                            && Objects.equals(newBill.getName(), oldBill.getName())
                            && Objects.equals(newBill.getDescription(), oldBill.getDescription())
                            && Objects.equals(newBill.getUpdatedTime(), oldBill.getUpdatedTime());
                }
            });

            mBillList = billList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BillItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.bill_item, parent, false);
        binding.setCallback(mBillClickCallback);
        return new BillViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BillViewHolder holder, int position) {
        holder.binding.setBill(mBillList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mBillList == null ? 0 : mBillList.size();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {
        final BillItemBinding binding;

        public BillViewHolder(BillItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
