package com.app.coinally.in.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.app.coinally.in.Bittrex.activities.GetBalancesActivity;
import com.app.coinally.in.Bittrex.adapters.GetBalancesAdapter;
import com.app.coinally.in.Bittrex.settings.ApplicationSettings;
import com.app.coinally.in.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A placeholder fragment containing a simple view.
 */
public class WalletFragment extends Fragment implements View.OnClickListener {
    View view;
    Context mcontext;

    private static final String TAG = GetBalancesActivity.class.getSimpleName();

    @BindView(R.id.hidezerobalances_checkbox)
    CheckedTextView _hideZeroBalancesCheckbox;
    @BindView(R.id.recyclerview)
    RecyclerView _recyclerView;

    private GetBalancesAdapter _adapter;
    private LinearLayoutManager _layoutManager;


    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
        return fragment;
    }

    public WalletFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int j;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.frag_wallet_layout, container, false);
        mcontext = getActivity();

        _recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        _hideZeroBalancesCheckbox = view.findViewById(R.id.hidezerobalances_checkbox);
        _adapter = new GetBalancesAdapter(mcontext);
        _adapter.setHideZeroBalances(ApplicationSettings.instance().getHideZeroBalances());

        _layoutManager = new LinearLayoutManager(mcontext);

        _recyclerView.setLayoutManager(_layoutManager);
        _recyclerView.setAdapter(_adapter);

        _hideZeroBalancesCheckbox.setChecked(ApplicationSettings.instance().getHideZeroBalances());
        _hideZeroBalancesCheckbox.setOnClickListener(this);

        _adapter.loadItems();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

        final int id = view.getId();

        switch (id) {
            case R.id.hidezerobalances_checkbox:
                _hideZeroBalancesCheckbox.setChecked(!_hideZeroBalancesCheckbox.isChecked());
                ApplicationSettings.instance().setHideZeroBalances(_hideZeroBalancesCheckbox.isChecked());

                _adapter.setHideZeroBalances(_hideZeroBalancesCheckbox.isChecked());
        }

    }
}