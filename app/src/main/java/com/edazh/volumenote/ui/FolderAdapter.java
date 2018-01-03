package com.edazh.volumenote.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.edazh.volumenote.R;
import com.edazh.volumenote.databinding.FolderItemBinding;
import com.edazh.volumenote.databinding.FolderListFragmentBinding;
import com.edazh.volumenote.model.Folder;

import java.util.List;
import java.util.Objects;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private List<? extends Folder> mFolderList;

    @Nullable
    private final FolderClickCallback mFolderClickCallback;

    public FolderAdapter(@Nullable FolderClickCallback folderClickCallback) {
        mFolderClickCallback = folderClickCallback;
    }

    public void setFolderList(final List<? extends Folder> folderList) {
        if (mFolderList == null) {
            mFolderList = folderList;
            notifyItemRangeInserted(0, folderList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mFolderList.size();
                }

                @Override
                public int getNewListSize() {
                    return folderList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mFolderList.get(oldItemPosition).getId() == folderList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Folder oldFolder = mFolderList.get(oldItemPosition);
                    Folder newFolder = folderList.get(newItemPosition);
                    return newFolder.getId() == oldFolder.getId()
                            && Objects.equals(newFolder.getName(), oldFolder.getName())
                            && Objects.equals(newFolder.getUpdatedTime(), oldFolder.getUpdatedTime());
                }
            });
            mFolderList = folderList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FolderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.folder_item, parent, false);
        binding.setCallback(mFolderClickCallback);
        return new FolderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, int position) {
        holder.binding.setFolder(mFolderList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mFolderList == null ? 0 : mFolderList.size();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {

        final FolderItemBinding binding;

        public FolderViewHolder(FolderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
