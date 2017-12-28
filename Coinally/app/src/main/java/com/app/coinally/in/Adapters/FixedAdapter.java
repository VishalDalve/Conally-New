package com.app.coinally.in.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.coinally.in.R;
import com.app.coinally.in.Utils.CryptoCoin;
import com.app.coinally.in.Utils.PersonDiffCallback;

import java.util.ArrayList;
import java.util.List;

import static com.app.coinally.in.Utils.AppConstants.PRICE_UP;


/**
 * Created by pbuhsoft on 19/12/2017.
 */

public class FixedAdapter  extends RecyclerView.Adapter<FixedAdapter.CustomViewHolder> {
	//private List<FeedItemnw> feedItemList;
	private Context mContext;
	private List<CryptoCoin> feedItemList;
	private int width,height;
	private int colorFrom;
	private String invisibleColumnList;
	private String[] colsToHide;
	public FixedAdapter(Context context, List<CryptoCoin> feedItemList, String invisibleColumnList) {
		this.feedItemList = feedItemList;
		this.mContext = context;
		this.invisibleColumnList =invisibleColumnList;
		colsToHide= invisibleColumnList.split(",");
	}

	public void dispatch(ArrayList<CryptoCoin> newlist) {
		PersonDiffCallback personDiffCallback = new PersonDiffCallback(newlist, feedItemList);
		DiffUtil.DiffResult diffresult = DiffUtil.calculateDiff(personDiffCallback);

		feedItemList=(ArrayList<CryptoCoin>)newlist.clone();
		diffresult.dispatchUpdatesTo(this);
	}

	@Override
	public FixedAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fixed_tab_item, null);
		FixedAdapter.CustomViewHolder viewHolder = new FixedAdapter.CustomViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(FixedAdapter.CustomViewHolder customViewHolder, int position) {

		final CryptoCoin feedItem = feedItemList.get(position);
		customViewHolder.textView2.setText(feedItem.getApp_price());
		customViewHolder.textView3.setText(feedItem.getApp_24h());
		customViewHolder.textView4.setText(feedItem.getMarket_cap_usd());
		customViewHolder.textView5.setText(feedItem.getApp_price_last());
		customViewHolder.textView6.setText(feedItem.getCurrency_time_diff_price_15_min());
		customViewHolder.textView7.setText(feedItem.getCurrency_time_diff_price_30_min());
		customViewHolder.textView8.setText(feedItem.getCurrency_time_diff_price_1hr());
		customViewHolder.textView9.setText(feedItem.getCurrency_time_diff_price_3hr());
		customViewHolder.textView10.setText(feedItem.getPercent_change_24h());
		customViewHolder.textView11.setText(feedItem.getPercent_change_7d());


		if (invisibleColumnList.length()>0) {
			View[] views = new View[]{customViewHolder.textView2, customViewHolder.textView3,
					customViewHolder.textView4, customViewHolder.textView5,
					customViewHolder.textView6, customViewHolder.textView7,
					customViewHolder.textView8, customViewHolder.textView9,
					customViewHolder.textView10, customViewHolder.textView11};

			for (String col : colsToHide) {
				int pos = Integer.parseInt(col);
				views[pos].setVisibility(View.GONE);
			}
		}
	}
	@Override
	public void onBindViewHolder(FixedAdapter.CustomViewHolder holder, final int position, List<Object> payloads) {
		final CryptoCoin feedItem = feedItemList.get(position);

		if (payloads.isEmpty()) {
			onBindViewHolder(holder, position);
		} else {
			holder.textView2.setText(feedItem.getApp_price());
			for (Object data : payloads) {
					colorAnimation(holder.lay_row, (int) data);
			}
		}

	}

	@Override
	public int getItemCount() {
		return (null != feedItemList ? feedItemList.size() : 0);
	}

	public class CustomViewHolder extends RecyclerView.ViewHolder {
		TextView textView2,textView3,textView4,textView5,textView6,
		textView7,textView8,textView9,textView10,textView11;
		LinearLayout lay_row;

		public CustomViewHolder(View view) {
			super(view);
			lay_row=(LinearLayout) view.findViewById(R.id.lay_row);
			this.textView2 = (TextView) view.findViewById(R.id.textView2);
			this.textView3 = (TextView) view.findViewById(R.id.textView3);
			this.textView4 = (TextView) view.findViewById(R.id.textView4);
			this.textView5 = (TextView) view.findViewById(R.id.textView5);
			this.textView6 = (TextView) view.findViewById(R.id.textView6);
			this.textView7 = (TextView) view.findViewById(R.id.textView7);
			this.textView8 = (TextView) view.findViewById(R.id.textView8);
			this.textView9 = (TextView) view.findViewById(R.id.textView9);
			this.textView10 = (TextView) view.findViewById(R.id.textView10);
			this.textView11 = (TextView) view.findViewById(R.id.textView11);

		}
	}

	public void setFilter(List<CryptoCoin> countryModels) {
		feedItemList = new ArrayList<>();
		feedItemList.addAll(countryModels);
		notifyDataSetChanged();
	}

	void colorAnimation(final TextView view)
	{
		final Integer colorFrom = mContext.getResources().getColor(R.color.colorPrimaryDark);
		Integer colorTo = mContext.getResources().getColor(R.color.colorAccent);
		final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
		colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				view.setTextColor((Integer)animator.getAnimatedValue());
			}

		});
		colorAnimation.setDuration(100);
		colorAnimation.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {

				view.setTextColor(colorFrom);

			}
		});
		colorAnimation.start();


	}
	void colorAnimation(final View view,int upOrDown)
	{
		colorFrom =mContext.getResources().getColor(R.color.startRed);
		Integer colorTo = mContext.getResources().getColor(R.color.colorTableBack);
		if (upOrDown==PRICE_UP){
			colorFrom =mContext.getResources().getColor(R.color.startGreen);
		}
		final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
		colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				view.setBackgroundColor((Integer)animator.getAnimatedValue());
			}

		});
		colorAnimation.setDuration(1000);
		colorAnimation.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				view.setBackgroundColor(mContext.getResources().getColor(R.color.colorTableBack));

			}
		});
		colorAnimation.start();
	}
}
