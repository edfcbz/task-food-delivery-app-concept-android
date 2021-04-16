package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import bean.NetworkUtils;
import bean.OrderItem;
import bean.Store;

public class MainActivity extends Activity {

    private Intent intent;
    private EditText editTextSearchingRestaurant;
    private EditText editTextSearching;
    private Button settingButton;
    private Button orderButton;
    private ListView listViewStore;
    private ArrayList restaurantList = new ArrayList();
    private ArrayList temp;
    private static final String SETTINGS_FILE =  "SettingsFile";
    //private ArrayAdapter<String> adapter;

    private MyAdapterStore myAdapter;
    public static ArrayList<OrderItem> orderItemList;
    public static String restaurantId = "1";
    public static String url;
    public String storeUrl = "";
    public String storeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "192.168.56.1";
        init();

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("activity", "main");
                intent.putExtra("storeUrl", storeUrl);
                intent.putExtra("storeName", storeName);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        listViewStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Store store = (Store) listViewStore.getAdapter().getItem(position);

                restaurantId = (position+1) + "";

                intent = new Intent(MainActivity.this, StoreActivity.class);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("storeUrl", store.getLogo());
                intent.putExtra("storeName", store.getName());
                intent.putExtra("url", url);
                try{
                    startActivity(intent);
                }
                catch(Throwable t){
                    System.out.println(t.getMessage().toString());
                }

            }
        });

    }

    public void init() {
        settingButton = findViewById(R.id.buttonSettingId);
        orderButton = findViewById(R.id.buttonOrderMainId);
        listViewStore = findViewById(R.id.listViewStoreId);
        editTextSearchingRestaurant = findViewById(R.id.editTextSearchingId);

        restaurantId = "0";
        Bundle extra = getIntent().getExtras();
        String s = "";
        if (extra != null) {
            s = extra.getString("restaurantId");
        }
        if (s.length() > 0) {
            restaurantId = extra.getString("restaurantId").toString();
            orderItemList = (ArrayList<OrderItem>) getIntent().getSerializableExtra("orderItemList");
            url             = extra.getString("url").toString();
            storeUrl             = extra.getString("storeUrl").toString();
            storeName             = extra.getString("storeName").toString();
            //storeUrl = extra.getString("url").toString();
            if (orderItemList == null) {
                orderItemList = new ArrayList<>();
            }
        }

//        adapter = new ArrayAdapter<String>(
//                getApplicationContext(),
//                android.R.layout.simple_list_item_1,
//                android.R.id.text1,
//                restaurantList);
//
//        //Chamando apenas para verificar a carga do ArrayList
//        listViewStore.setAdapter(adapter);

        myAdapter = new MyAdapterStore(new ArrayList<Store>(), MainActivity.this);
        listViewStore.setAdapter(myAdapter);


        if (orderItemList == null || orderItemList.size() == 0){
            orderButton.setEnabled(false);
        }else{
            orderButton.setEnabled(true);
        }


        new Http().execute();

    }

    private class Http extends AsyncTask<Void, Void, Boolean> {

        private String result;


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (Boolean.TRUE.equals(aBoolean)) {
                Gson gson = new Gson();
                Type storeListType = new TypeToken<ArrayList<Store>>(){}.getType();
                ArrayList<Store> items = gson.fromJson(result, storeListType);
                myAdapter.setStoreList(items);
                myAdapter.notifyDataSetChanged();
            } else {
                //myAdapter.setDataList(new ArrayList<Store>());
                System.out.println("Buxo");
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                result = NetworkUtils.getJSONFromAPI("GET", "http://"+url+":8080/services/store");
                return Boolean.TRUE;
            } catch (Throwable t) {
                t.printStackTrace();
                return Boolean.FALSE;
            }
        }
    }


}


