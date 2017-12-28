package com.app.coinally.in.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.app.coinally.in.Adapters.ColumnFilterAdapter;
import com.app.coinally.in.Adapters.FixedAdapter;
import com.app.coinally.in.Adapters.FixedColumnAdapter;
import com.app.coinally.in.MainApplication;
import com.app.coinally.in.R;
import com.app.coinally.in.Utils.AppConstants;
import com.app.coinally.in.Utils.CryptoCoin;
import com.app.coinally.in.Utils.Webservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.socket.emitter.Emitter;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    View view;
    private ArrayList<String> coinNameList;
    private ArrayList<CryptoCoin> cryptoCoinList;
    private RecyclerView recyclerViewMain, fixedRecyclerView;
    private FixedAdapter adapter;
    private FixedColumnAdapter colAdapter;
    private io.socket.client.Socket mSocket;
    private Context mcontext;
    private View viewHeader1, viewHeader;
    private ImageView[] imageViewSortCols;

    private int[] headerColIDList;
    private ImageView imageViewFilter;
    private String invisibleColumnList = "";
    private Boolean isConnected = true;
    private boolean veryFirstTime = true;

    private PopupWindow popupWindow;
    private String QueryText = "";
    private LottieAnimationView lottieAnimationView;

    // here getting data from socket in json format ---------------------------------------------------------
    private Emitter.Listener trades = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //JSONObject data = (JSONObject) args[0].toString();
//
                    String username = args[0].toString();
                    Log.v("wasim", "data=" + username);
                    System.out.println("NEWWW " + username); // getting coin socket data here ------------------
                    try {

                        JSONObject jObject = new JSONObject(username);
                        String coin = jObject.getString("coin"); //parsing coin name here ----------
                        String coinNewPrice = jObject.getJSONObject("message").getJSONObject("msg").getString("price");
                        int coinIndex = coinNameList.indexOf(coin);
                        if (coinIndex < 0) {
                            Log.v("wasim", "coin=" + coin + " not found in list.");
                            return;
                        }
                        String oldPrice = cryptoCoinList.get(coinIndex).getApp_price();

                        if (!TextUtils.isEmpty(coinNewPrice) && !coinNewPrice.equals(oldPrice)) {
                            cryptoCoinList.get(coinIndex).setApp_price(coinNewPrice);
                            cryptoCoinList.get(coinIndex).setApp_price_last(oldPrice);
                            adapter.notifyItemChanged(coinIndex,
                                    Double.parseDouble(coinNewPrice) > Double.parseDouble(oldPrice) ? AppConstants.PRICE_UP : AppConstants.PRICE_DOWN);
                            colAdapter.notifyItemChanged(coinIndex,
                                    Double.parseDouble(coinNewPrice) > Double.parseDouble(oldPrice) ? AppConstants.PRICE_UP : AppConstants.PRICE_DOWN);
                            Log.v("wasim", "price changes of " + coinNameList.get(coinIndex));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v("wasim", "error:" + e.toString());
                    }
                }
            });
        }
    };
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("onConnect................................");
                    if (!isConnected) {
//                        if(null!=mUsername)
//                            mSocket.emit("add user", mUsername);
                        Toast.makeText(mcontext,
                                "Connected", Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("onDisConnect................................");
                    // Log.i(TAG, "diconnected");
                    isConnected = false;
                    Toast.makeText(mcontext,
                            "Disconected", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("onConnectError................................");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Log.e(TAG, "Error connecting");
                    Toast.makeText(getActivity().getApplicationContext(),
                            "error_connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    private RecyclerView.OnScrollListener scrollListenerFixed = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            recyclerViewMain.removeOnScrollListener(scrollListenerMain);
            recyclerViewMain.scrollBy(recyclerView.getScrollX(), dy);
            recyclerViewMain.addOnScrollListener(scrollListenerMain);
        }
    };
    private RecyclerView.OnScrollListener scrollListenerMain = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            fixedRecyclerView.removeOnScrollListener(scrollListenerFixed);
            fixedRecyclerView.scrollBy(recyclerView.getScrollX(), dy);
            fixedRecyclerView.addOnScrollListener(scrollListenerFixed);
        }
    };



    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static ArrayList<ArrayList<String>> biDemArrList = new ArrayList<ArrayList<String>>();

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int j;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.content_fixed_tab, container, false);
        mcontext = getActivity();

        lottieAnimationView = view.findViewById(R.id.animation_view);
        imageViewFilter = view.findViewById(R.id.imageViewFilter);
        viewHeader1 = view.findViewById(R.id.layHeader1);
        viewHeader = view.findViewById(R.id.lay_row);
        viewHeader.setVisibility(View.GONE);
        viewHeader1.setVisibility(View.GONE);
        headerColIDList = new int[]{R.id.Lay2, R.id.Lay3, R.id.Lay4, R.id.Lay5,
                R.id.Lay6, R.id.Lay7, R.id.Lay8, R.id.Lay9,
                R.id.Lay10, R.id.Lay11};
        imageViewSortCols = new ImageView[]{view.findViewById(R.id.imageView2), view.findViewById(R.id.imageView3), view.findViewById(R.id.imageView4),
                view.findViewById(R.id.imageView5), view.findViewById(R.id.imageView6), view.findViewById(R.id.imageView7),
                view.findViewById(R.id.imageView8), view.findViewById(R.id.imageView9), view.findViewById(R.id.imageView10),
                view.findViewById(R.id.imageView11)};
        recyclerViewMain = view.findViewById(R.id.recyclerView);
        fixedRecyclerView = view.findViewById(R.id.fixedRecyclerView);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(mcontext));
        fixedRecyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerViewMain.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);
        fixedRecyclerView.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);
        recyclerViewMain.addOnScrollListener(scrollListenerMain);
        fixedRecyclerView.addOnScrollListener(scrollListenerFixed);

        //getting 1st data from API and adding to recyclerView
        new OfferAsyncHttpTask().execute();

        MainApplication app = (MainApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on(io.socket.client.Socket.EVENT_CONNECT, onConnect);
        mSocket.on(io.socket.client.Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(io.socket.client.Socket.EVENT_CONNECT_ERROR, onConnectError);
        //mSocket.on("message", message);//working old code
        mSocket.on("trades", trades); //new code testing
        mSocket.connect();
        imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog(v);
            }
        });
        for (int id : headerColIDList) {
            view.findViewById(id).setOnClickListener(this);
        }


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


    public void filterList(String query) {
        ArrayList<CryptoCoin> newList = new ArrayList<>();
        if (query.length() > 0) {
            query = query.toLowerCase();
            for (CryptoCoin item : cryptoCoinList) {
                if (item.getApp_name().toLowerCase().contains(query)) {
                    newList.add(item);
                }
            }
            cryptoCoinList = newList;
            adapter = new FixedAdapter(mcontext, cryptoCoinList, invisibleColumnList);
            colAdapter = new FixedColumnAdapter(mcontext, cryptoCoinList);
            recyclerViewMain.removeOnScrollListener(scrollListenerMain);
            fixedRecyclerView.removeOnScrollListener(scrollListenerFixed);
            recyclerViewMain.setAdapter(adapter);
            fixedRecyclerView.setAdapter(colAdapter);
            recyclerViewMain.addOnScrollListener(scrollListenerMain);
            fixedRecyclerView.addOnScrollListener(scrollListenerFixed);

        } else {
            //getting 1st data from API and adding to recyclerView
            new OfferAsyncHttpTask().execute();
        }
    }

    public void applyColumnFilters() {
        Log.v("wasim", "applyColumnFilters");
        for (int colID : headerColIDList) {
            view.findViewById(colID).setVisibility(View.VISIBLE);
        }
        if (invisibleColumnList.length() > 0) {
            String[] cols = invisibleColumnList.split(",");
            for (String col : cols) {
                int pos = Integer.parseInt(col);
                view.findViewById(headerColIDList[pos]).setVisibility(View.GONE);
            }
        }
        adapter = new FixedAdapter(mcontext, cryptoCoinList, invisibleColumnList);
        colAdapter = new FixedColumnAdapter(mcontext, cryptoCoinList);
        recyclerViewMain.removeOnScrollListener(scrollListenerMain);
        fixedRecyclerView.removeOnScrollListener(scrollListenerFixed);
        recyclerViewMain.setAdapter(adapter);
        fixedRecyclerView.setAdapter(colAdapter);
        recyclerViewMain.addOnScrollListener(scrollListenerMain);
        fixedRecyclerView.addOnScrollListener(scrollListenerFixed);

    }

    public void sortList(final int colIndex) {
        final boolean isAsc;
        for (ImageView imageView : imageViewSortCols) {
            imageView.setImageResource(R.drawable.ic_sort);
        }
        if (imageViewSortCols[colIndex].getTag() == null) {
            isAsc = true;
        } else {
            isAsc = (boolean) imageViewSortCols[colIndex].getTag();
        }
        if (isAsc) {
            imageViewSortCols[colIndex].setImageResource(R.drawable.ic_sort_down);
        } else {
            imageViewSortCols[colIndex].setImageResource(R.drawable.ic_sort_up);
        }
        Collections.sort(cryptoCoinList, new Comparator<CryptoCoin>() {
            @Override
            public int compare(CryptoCoin o1, CryptoCoin o2) {
                String p1 = "";
                String p2 = "";
                switch (colIndex) {
                    case 0:
                        p1 = o1.getApp_price();
                        p2 = o2.getApp_price();
                        break;
                    case 1:
                        p1 = o1.getApp_24h();
                        p2 = o2.getApp_24h();
                        break;
                    case 2:
                        p1 = o1.getMarket_cap_usd();
                        p2 = o2.getMarket_cap_usd();
                        break;

                    case 3:
                        p1 = o1.getApp_price_last();
                        p2 = o2.getApp_price_last();
                        break;
                    case 4:
                        p1 = o1.getCurrency_time_diff_price_15_min();
                        p2 = o2.getCurrency_time_diff_price_15_min();
                        break;
                    case 5:
                        p1 = o1.getCurrency_time_diff_price_30_min();
                        p2 = o2.getCurrency_time_diff_price_30_min();
                        break;
                    case 6:
                        p1 = o1.getCurrency_time_diff_price_1hr();
                        p2 = o2.getCurrency_time_diff_price_1hr();
                        break;
                    case 7:
                        p1 = o1.getCurrency_time_diff_price_3hr();
                        p2 = o2.getCurrency_time_diff_price_3hr();
                        break;
                    case 8:
                        p1 = o1.getPercent_change_24h();
                        p2 = o2.getPercent_change_24h();
                        break;
                    case 9:
                        p1 = o1.getPercent_change_7d();
                        p2 = o2.getPercent_change_7d();
                        break;

                }
                /*if (TextUtils.isEmpty(p1) || TextUtils.isEmpty(p2) || p1.equals("null") || p2.equals("null")) {

					return AppConstants.INT_MAX_VALUE;
				}*/
                if (TextUtils.isEmpty(p1) || p1.equals("null")) {
                    if (isAsc) {
                        p1 = "" + AppConstants.INT_MAX_VALUE;
                    } else {
                        p1 = "0";
                    }
                }
                if (TextUtils.isEmpty(p2) || p2.equals("null")) {
                    if (isAsc) {
                        p2 = "" + AppConstants.INT_MAX_VALUE;
                    } else {
                        p2 = "0";
                    }
                }
                try {
                    if (isAsc) {
                        return Double.valueOf(p1).compareTo(Double.valueOf(p2));
                    } else {
                        return Double.valueOf(p2).compareTo(Double.valueOf(p1));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return AppConstants.INT_MAX_VALUE;
            }
        });
        imageViewSortCols[colIndex].setTag(!isAsc);
        adapter = new FixedAdapter(mcontext, cryptoCoinList, invisibleColumnList);
        colAdapter = new FixedColumnAdapter(mcontext, cryptoCoinList);
        recyclerViewMain.removeOnScrollListener(scrollListenerMain);
        fixedRecyclerView.removeOnScrollListener(scrollListenerFixed);
        recyclerViewMain.setAdapter(adapter);
        fixedRecyclerView.setAdapter(colAdapter);
        recyclerViewMain.addOnScrollListener(scrollListenerMain);
        fixedRecyclerView.addOnScrollListener(scrollListenerFixed);
    }

    public void showFilterDialog(View anchor) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.dialog_column_filter, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        String[] cols = getResources().getStringArray(R.array.column_names);
        boolean[] filterColumns = new boolean[]{true, true, true, true, true, true, true, true, true, true, true};
        if (invisibleColumnList.length() > 0) {
            String[] hideCols = invisibleColumnList.split(",");
            for (String col : hideCols) {
                int pos = Integer.parseInt(col);
                filterColumns[pos] = false;
            }
        }
        final ColumnFilterAdapter columnFilterAdapter = new ColumnFilterAdapter(mcontext, cols, filterColumns);
        recyclerView.setAdapter(columnFilterAdapter);
        Button buttonApply = view.findViewById(R.id.buttonApply);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleColumnList = "";
                boolean[] cols = columnFilterAdapter.getFilterColumns();
                for (int i = 0; i < cols.length; i++) {
                    if (!cols[i]) {
                        invisibleColumnList += i + ",";
                    }
                }
                if (invisibleColumnList.length() > 0) {
                    invisibleColumnList = invisibleColumnList.substring(0, invisibleColumnList.length() - 1);
                }
                popupWindow.dismiss();

                applyColumnFilters();
            }
        });

        popupWindow = new PopupWindow(view);
        popupWindow.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        // Clear the default translucent background
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        //popupRingtoneSelect.showAsDropDown(SettingActivity.this.findViewById(R.id.lay_main));
        popupWindow.showAsDropDown(anchor);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void parseResult(String result) {

        try {

            cryptoCoinList = new ArrayList<>();
            coinNameList = new ArrayList<>();
            JSONObject response = new JSONObject(result);
            JSONArray jsonarray = response.getJSONArray("data");

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject post = jsonarray.getJSONObject(i);
                Log.v("wasim", "coin" + i + "=" + post.toString());

                CryptoCoin item = new CryptoCoin();
//                if(lang) {
//                    item.setApp_name(post.optString("titlehin"));
//                    item.setApp_desc(post.optString("deschin"));
//
//
//                }else {

                String rawName = post.optString("MarketName");
                Log.v("wasim", "rawName=" + rawName);
                rawName = rawName.substring(4, rawName.length());
                Log.v("wasim", "changed Name=" + rawName);

                item.setApp_name(rawName);
                item.setApp_desc(post.optString("MarketCurrencyLong"));
                item.setApp_image(post.optString("LogoUrl"));
                item.setApp_24h(post.optString("24h_volume_usd"));
                item.setApp_price(post.optString("Last"));
                item.setMarket_cap_usd(post.optString("market_cap_usd"));
//
                item.setCurrency_time_diff_price_15_min(post.optString("currency_time_diff_price_15_min"));
                item.setCurrency_time_diff_price_30_min(post.optString("currency_time_diff_price_15_min"));
                item.setCurrency_time_diff_price_1hr(post.optString("currency_time_diff_price_1hr"));
                item.setCurrency_time_diff_price_3hr(post.optString("currency_time_diff_price_3hr"));

                item.setPercent_change_24h(post.optString("percent_change_24h"));
                item.setPercent_change_7d(post.optString("percent_change_7d"));

                coinNameList.add(item.getApp_name());
                cryptoCoinList.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.Lay2:
                sortList(0);
                break;
            case R.id.Lay3:
                sortList(1);
                break;
            case R.id.Lay4:
                sortList(2);
                break;
            case R.id.Lay5:
                sortList(3);
                break;
            case R.id.Lay6:
                sortList(4);
                break;
            case R.id.Lay7:
                sortList(5);
                break;
            case R.id.Lay8:
                sortList(6);
                break;
            case R.id.Lay9:
                sortList(7);
                break;
            case R.id.Lay10:
                sortList(8);
                break;

            case R.id.Lay11:
                sortList(9);
                break;

        }
    }

    @Override
    public void onDestroy() {
        System.out.println("onDistroy................................");
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off(io.socket.client.Socket.EVENT_CONNECT, onConnect);
        mSocket.off(io.socket.client.Socket.EVENT_DISCONNECT, onDisconnect);
    }

    // fething offers Task--------------------------------------------------------------------------------------------
    public class OfferAsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            // UtilMethod.ShowProgressDialogue(getContext(), "Fetching Offers");
            //swipeContainer.setRefreshing(true);
            if (cryptoCoinList == null || cryptoCoinList.size() == 0)
                lottieAnimationView.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Integer doInBackground(String... params) {
            try {

                URL url = new URL(Webservices.CRYPT_WEBSERVICE);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

//                ApiCrypter apicry = new ApiCrypter();
//                String data_web = "";

                String encryptedRequest = "";
//                try {
//                    encryptedRequest = apicry.encrypt(data_web);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                String urlParameters = encryptedRequest;

                //  Log.d("BOOKING_params", urlParameters);

                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                // int responseCode = connection.getResponseCode();
                final StringBuilder output = new StringBuilder();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(responseOutput.toString());


                String s = output.toString();


//                try {
//                    apicry = new ApiCrypter();
//                    String res = ApiCrypter.decrypt(s);
//                    s = URLDecoder.decode(res, "UTF-8");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                Log.d("crytoData", s);


                parseResult(s);


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            adapter = new FixedAdapter(mcontext, cryptoCoinList, invisibleColumnList);
            colAdapter = new FixedColumnAdapter(mcontext, cryptoCoinList);
            Log.v("wasim", "main arr size=" + cryptoCoinList.size() + "  fixed arr size=" + coinNameList.size());
            recyclerViewMain.setAdapter(adapter);
            fixedRecyclerView.setAdapter(colAdapter);
            viewHeader.setVisibility(View.VISIBLE);
            viewHeader1.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
        }
    }

}