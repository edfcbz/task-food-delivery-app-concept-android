package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import bean.LittleCar;
import bean.Message;
import bean.NetworkUtils;
import bean.Order;
import bean.OrderItem;
import bean.Product;
import bean.Store;

public class StoreActivity extends Activity {

    private Button buttonBackStore;
    private Button buttonOrderStore;
    private ListView listViewProduct;
    private Intent intent;
    private Order order;
    private AlertDialog.Builder alertDialogSaveOrder;
    public ArrayList<OrderItem> orderItemList = new ArrayList<>();
    public static String restaurantId = "";
    public static String url = "";
    private MyAdapterProduct myAdapter;
    private ArrayList<Store> storeList;
    public MyAdapterProduct adapter;
    public String storeUrl;
    public String storeAddress;
    public String storeName;

    //private int storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        System.out.println("antes");

        init();

        buttonBackStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(StoreActivity.this, MainActivity.class);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("url", url);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("storeUrl", storeUrl);
                intent.putExtra("storeName", storeName);
                startActivity(intent);
            }
        });

        buttonOrderStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sortOrderItemList();

                    Store store = new Store();
                    store.setLogo(orderItemList.get(0).getStoreUrl());
                    store.setName(orderItemList.get(0).getStoreName());

                    intent = new Intent(StoreActivity.this, OrderActivity.class);
                    intent.putExtra("orderItemList", orderItemList);
                    intent.putExtra("restaurantId", restaurantId);
                    intent.putExtra("store", store);
                    intent.putExtra("activity", "store");
                    intent.putExtra("url", url);
                    intent.putExtra("storeUrl", storeUrl);
                    intent.putExtra("storeName", storeName);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("ERRO!", e.getLocalizedMessage());
                }
            }
        });

        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int index = position;
                alertDialogSaveOrder = new AlertDialog.Builder(StoreActivity.this);
                alertDialogSaveOrder.setTitle("Confirm Item");
                alertDialogSaveOrder.setMessage("Do want to add this item inside Order?");
                alertDialogSaveOrder.setCancelable(false);

                alertDialogSaveOrder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter = null;
                            }
                        });

                alertDialogSaveOrder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Product product = myAdapter.getProductList().get(index);
                                addItemInLittleCar(product);

                                //addItemInsideOrder(index);
                            }
                        });

                alertDialogSaveOrder.create();
                alertDialogSaveOrder.show();
            }
        });

    }

    public void init() {
        buttonBackStore = findViewById(R.id.buttonBackSoreId);
        buttonOrderStore = findViewById(R.id.buttonOrderStoreId);
        listViewProduct = findViewById(R.id.listViewProduct);

        order = new Order();
        orderItemList = new ArrayList<>();

//        Bundle extra = getIntent().getExtras();
//        if ( !extra.getString("restaurantId").equals("") ){
        Bundle extra = getIntent().getExtras();
        String s = extra.getString("restaurantId");
        if (s.length() > 0) {
            restaurantId = extra.getString("restaurantId").toString();
            orderItemList = (ArrayList<OrderItem>) getIntent().getSerializableExtra("orderItemList");
            storeUrl = extra.getString("storeUrl").toString();
            storeName = extra.getString("storeName").toString();
            //storeName = extra.getString("storeName").toString();

            url             = extra.getString("url").toString();
            if (orderItemList == null) {
                orderItemList = new ArrayList<>();
            }
        }

        if( orderItemList == null || orderItemList.size() == 0 ){
            buttonOrderStore.setEnabled(false);
        }else
        {
            buttonOrderStore.setEnabled(true);
        }


        myAdapter = new MyAdapterProduct(new ArrayList<Product>(), StoreActivity.this);
        listViewProduct.setAdapter(myAdapter);

        new Http().execute();

    }

    private class Http extends AsyncTask<Void, Void, Boolean> {

        private String result;


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (Boolean.TRUE.equals(aBoolean)) {
                Gson gson = new Gson();
                Type storeListType = new TypeToken<ArrayList<Product>>() {
                }.getType();
                ArrayList<Product> items = gson.fromJson(result, storeListType);
                myAdapter.setProductList(items);
                myAdapter.notifyDataSetChanged();
            } else {
                //myAdapter.setDataList(new ArrayList<Store>());
                System.out.println("Buxo");
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                //result = NetworkUtils.getJSONFromAPI("GET", "http://10.182.253.192:8080/services/product/store/" + restaurantId);
                result = NetworkUtils.getJSONFromAPI("GET", "http://"+url+":8080/services/product/store/" + restaurantId);
                return Boolean.TRUE;
            } catch (Throwable t) {
                t.printStackTrace();
                return Boolean.FALSE;
            }
        }
    }



    public void addItemInLittleCar(Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setStoreId(product.getStoreId());
        orderItem.setDescription(product.getDescription());
        orderItem.setQuantity(1);
        orderItem.setProductId(product.getId());
        orderItem.setPrice(product.getPrice());
        orderItem.setProduct(product);

        // Gambiarra para pegar a url e nome da loja do pedido...
        String storeUrl = getIntent().getStringExtra("storeUrl");
        String storeName = getIntent().getStringExtra("storeName");


//        if ( storeUrl.equals("") ){
//            storeUrl = this.storeUrl;
//            storeName = this.storeName;
//        }

        orderItem.setStoreUrl(storeUrl);
        orderItem.setStoreName(storeName);

        buttonOrderStore.setEnabled(true);
        orderItemList.add(orderItem);
    }

    public ArrayList<Store> findStoreById(int storeId) {
        new HttpStore(storeId + "").execute();
        return storeList;
    }

    class HttpStore extends AsyncTask<Void, Void, Boolean> {

        private String result;
        private String storeId;


        public HttpStore(String storeId) {
            this.storeId = storeId;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (Boolean.TRUE.equals(aBoolean)) {
                Gson gson = new Gson();
                Type storeListType = new TypeToken<ArrayList<Store>>() {
                }.getType();
                storeList = gson.fromJson(result, storeListType);
            } else {
                //myAdapter.setDataList(new ArrayList<Store>());
                System.out.println("Buxo");
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                result = NetworkUtils.getJSONFromAPI("GET", "http://"+url+":8080/services/store/" + storeId);
                return Boolean.TRUE;
            } catch (Throwable t) {
                t.printStackTrace();
                return Boolean.FALSE;
            }
        }
    }

    public void sortOrderItemList() {
        Collections.sort(orderItemList, new Comparator() {
            public int compare(Object o1, Object o2) {
                OrderItem orderItem1 = (OrderItem) o1;
                OrderItem orderItem2 = (OrderItem) o2;
                return orderItem1.getStoreId() < orderItem2.getStoreId() ? -1 : (orderItem1.getStoreId() > orderItem2.getStoreId() ? +1 : 0);
            }
        });
    }

}

