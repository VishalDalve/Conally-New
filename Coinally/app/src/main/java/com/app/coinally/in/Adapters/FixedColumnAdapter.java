package com.app.coinally.in.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.coinally.in.R;
import com.app.coinally.in.Utils.CryptoCoin;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.app.coinally.in.Utils.AppConstants.PRICE_UP;


/**
 * Created by pbuhsoft on 19/12/2017.
 */

public class FixedColumnAdapter   extends RecyclerView.Adapter<FixedColumnAdapter.CustomViewHolder> {
	//private List<FeedItemnw> feedItemList;
	private Context mContext;
	private List<CryptoCoin> feedItemList;
	private int width,height;
	private int colorFrom;
	private Drawable drawableLast;

	public FixedColumnAdapter(Context context, List<CryptoCoin> feedItemList) {
		this.feedItemList = feedItemList;
		this.mContext = context;
		width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, context.getResources().getDisplayMetrics());
		height =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, context.getResources().getDisplayMetrics());

	}



	@Override
	public FixedColumnAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fixed_column, null);
		FixedColumnAdapter.CustomViewHolder viewHolder = new FixedColumnAdapter.CustomViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(FixedColumnAdapter.CustomViewHolder customViewHolder, int position) {

		final CryptoCoin feedItem = feedItemList.get(position);
		customViewHolder.textView1.setText(feedItem.getApp_name());
		try {

			Glide.with(mContext).load(feedItem.getApp_image())
					.placeholder(R.drawable.coin_icon)
					.override(width,height)
					.into(customViewHolder.imageViewIcon);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public void onBindViewHolder(FixedColumnAdapter.CustomViewHolder holder, final int position, List<Object> payloads) {
		if (payloads.isEmpty()) {
			onBindViewHolder(holder, position);
		}  else {
			for (Object data : payloads) {
				colorAnimation(holder.lay_row,(int) data);
			}
		}
	}

	@Override
	public int getItemCount() {
		return (null != feedItemList ? feedItemList.size() : 0);
	}

	public class CustomViewHolder extends RecyclerView.ViewHolder {
		ImageView imageViewIcon;
		TextView textView1;
		LinearLayout lay_row;

		public CustomViewHolder(View view) {
			super(view);
			imageViewIcon= (ImageView) view.findViewById(R.id.imageViewIcon);
			lay_row=(LinearLayout) view.findViewById(R.id.lay_row);
			this.textView1 = (TextView) view.findViewById(R.id.textView1);

		}
	}

	public void setFilter(List<CryptoCoin> countryModels) {
		feedItemList = new ArrayList<>();
		feedItemList.addAll(countryModels);
		notifyDataSetChanged();
	}
	void colorAnimation(final View view,int upOrDown)
	{
		colorFrom =mContext.getResources().getColor(R.color.startRed);
		drawableLast= view.getBackground();
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