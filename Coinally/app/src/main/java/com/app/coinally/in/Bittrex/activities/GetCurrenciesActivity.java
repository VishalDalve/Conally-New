package com.app.coinally.in.Bittrex.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.app.coinally.in.Bittrex.adapters.GetCurrenciesAdapter;
import com.app.coinally.in.R;

import butterknife.BindView;

public class GetCurrenciesActivity extends Activity {
    private static final String TAG = GetCurrenciesActivity.class.getSimpleName();

    @BindView(R.id.recyclerview)
    RecyclerView _recyclerView;

    private GetCurrenciesAdapter _adapter;
    private LinearLayoutManager _layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btx_layout_recyclerview);

        _adapter = new GetCurrenciesAdapter(this);
        _layoutManager = new LinearLayoutManager(this);

        _recyclerView.setLayoutManager(_layoutManager);
        _recyclerView.setAdapter(_adapter);
    }

    @NonNull
    @Override
    protected String getTag() {
        return TAG;
    }
}
