package com.app.coinally.in.Utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

import static com.app.coinally.in.Utils.AppConstants.UPDATE_NAME;
import static com.app.coinally.in.Utils.AppConstants.UPDATE_PRICE;

/**
 * Created by Aravindraj on 8/2/2017.
 */

public class PersonDiffCallback extends DiffUtil.Callback {
    private List<CryptoCoin> mNewList;
    private List<CryptoCoin> mOldList;

    public PersonDiffCallback(List<CryptoCoin> newlist, List<CryptoCoin> oldlist) {
        this.mNewList = newlist;
        this.mOldList = oldlist;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

//        FeedItemnw newProduct = mNewList.get(newItemPosition);
//        FeedItemnw oldProduct = mOldList.get(oldItemPosition);
//        if (oldProduct.getApp_name().contains(newProduct.getApp_name())) {
//            return true;
//        } else {
//            return false;
//        }
        return mOldList.get(oldItemPosition).getApp_name() == mNewList.get(newItemPosition).getApp_name();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
       // Log.e("k", "k" + mOldList.get(oldItemPosition).getApp_name() + "" + mNewList.get(newItemPosition).getApp_name());
        return ((mOldList.get(oldItemPosition).getApp_name()!=(mNewList.get(newItemPosition).getApp_name())));//&& (mOldList.get(oldItemPosition).getApp_price() == mNewList.get(newItemPosition).getApp_price());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Log.e("k", "k");
        CryptoCoin newProduct = mNewList.get(newItemPosition);
        CryptoCoin oldProduct = mOldList.get(oldItemPosition);
//        if ((oldProduct.getApp_price() != newProduct.getApp_price())) {// && (!(oldProduct.getName().equals(newProduct.getName())))
//            return UPDATE_BOTH;
//        } else {
        if ((oldProduct.getApp_name() != newProduct.getApp_name())) {

            return UPDATE_NAME;
        }
        else {
            return UPDATE_PRICE ;
        }
//        if (oldProduct.getApp_name().contains(newProduct.getApp_name())) {
//
//            return UPDATE_NAME;
//
//
//        } else {
//
//            return UPDATE_PRICE;
//        }
    }
}
