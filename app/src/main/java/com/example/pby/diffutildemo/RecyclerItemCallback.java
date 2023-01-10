package com.example.pby.diffutildemo;


import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Objects;

public class RecyclerItemCallback extends DiffUtil.Callback {

    private List<Bean> mOldDataList;
    private List<Bean> mNewDataList;

    public RecyclerItemCallback(List<Bean> oldDataList, List<Bean> newDataList) {
        this.mOldDataList = oldDataList;
        this.mNewDataList = newDataList;
    }

    @Override
    public int getOldListSize() {
        return mOldDataList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDataList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        boolean result = Objects.equals(mNewDataList.get(newItemPosition).getId(), mOldDataList.get(oldItemPosition).getId());
        System.out.println("areItemsTheSame newItemPosition  " + newItemPosition + " oldItemPosition   " + oldItemPosition + ",result " + result);
        return result;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        boolean result = Objects.equals(mOldDataList.get(oldItemPosition).getContent(), mNewDataList.get(newItemPosition).getContent());
        System.out.println("areContentsTheSame newItemPosition  " + newItemPosition + " oldItemPosition   " + oldItemPosition + ",result " + result);
        return result;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        System.out.println("getChangePayload newItemPosition  " + newItemPosition + " oldItemPosition   " + oldItemPosition);
        Bean oldBean = mOldDataList.get(oldItemPosition);
        Bean newBean = mNewDataList.get(newItemPosition);
        if (!Objects.equals(oldBean.getContent(), newBean.getContent())) {
            return "content";
        }
        return null;
    }
}
