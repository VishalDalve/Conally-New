package com.app.coinally.in.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.app.coinally.in.R;


/**
 * Created by pbuhsoft on 20/12/2017.
 */

public class ColumnFilterAdapter extends RecyclerView.Adapter<ColumnFilterAdapter.CustomViewHolder> {
	private Context mContext;
	private String[] feedItemList;
	private boolean[] filterColumns;
	public ColumnFilterAdapter(Context context, String[] feedItemList, boolean[] filterColumns) {
		this.feedItemList = feedItemList;
		this.mContext = context;
		this.filterColumns =filterColumns;
	}

	@Override
	public ColumnFilterAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_list_item, null);
		ColumnFilterAdapter.CustomViewHolder viewHolder = new ColumnFilterAdapter.CustomViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ColumnFilterAdapter.CustomViewHolder customViewHolder, int position) {
		customViewHolder.checkBox.setChecked(filterColumns[position]);
		customViewHolder.checkBox.setText(feedItemList[position]);
		customViewHolder.checkBox.setOnCheckedChangeListener(new MyCheckChangeListener(position));
	}
	class MyCheckChangeListener implements CompoundButton.OnCheckedChangeListener{
		int pos;
		MyCheckChangeListener(int pos){
			this.pos=pos;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			filterColumns[pos]=isChecked;
		}
	}
	public boolean[] getFilterColumns(){
		return filterColumns;
	}

	@Override
	public int getItemCount() {
		return feedItemList.length;
	}

	public class CustomViewHolder extends RecyclerView.ViewHolder {
		CheckBox checkBox;
		public CustomViewHolder(View view) {
			super(view);
			checkBox=view.findViewById(R.id.checkboxFilterItem);
		}
	}
}