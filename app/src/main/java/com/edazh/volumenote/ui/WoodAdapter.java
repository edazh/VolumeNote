package com.edazh.volumenote.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.edazh.volumenote.R;
import com.edazh.volumenote.databinding.WoodItemBinding;
import com.edazh.volumenote.model.Wood;

import java.util.List;
import java.util.Objects;

/**
 * Created by edazh on 2017/12/13 0013.
 */

public class WoodAdapter extends RecyclerView.Adapter<WoodAdapter.WoodViewHolder> {

    private List<? extends Wood> mWoodList;

    private final WoodClickCallback mWoodClickCallback;

    public WoodAdapter(@Nullable WoodClickCallback woodClickCallback) {
        mWoodClickCallback = woodClickCallback;
    }

    @Override
    public WoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WoodItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.wood_item, parent, false);
        binding.setCallback(mWoodClickCallback);
        return new WoodViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(WoodViewHolder holder, int position) {
        holder.binding.setWood(mWoodList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mWoodList == null ? 0 : mWoodList.size();
    }

    public void setWoodList(final List<? extends Wood> woodList) {
        if (mWoodList == null) {
            mWoodList = woodList;
            notifyItemRangeInserted(0, woodList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mWoodList.size();
                }

                @Override
                public int getNewListSize() {
                    return woodList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mWoodList.get(oldItemPosition).getId(), woodList.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Wood oldWood = mWoodList.get(oldItemPosition);
                    Wood newWood = woodList.get(newItemPosition);
                    return Objects.equals(newWood.getId(), oldWood.getId())
                            && Objects.equals(newWood.getLength(), oldWood.getLength())
                            && Objects.equals(newWood.getDiameter(), oldWood.getDiameter())
                            && Objects.equals(newWood.getType(), oldWood.getType())
                            && Objects.equals(newWood.getVolume(), oldWood.getVolume());
                }
            });
            mWoodList = woodList;
            result.dispatchUpdatesTo(this);
        }
    }


    static class WoodViewHolder extends RecyclerView.ViewHolder {
        final WoodItemBinding binding;

        public WoodViewHolder(WoodItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
