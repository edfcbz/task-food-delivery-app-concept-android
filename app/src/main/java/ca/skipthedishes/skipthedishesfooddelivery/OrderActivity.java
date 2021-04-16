package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import bean.NetworkUtils;
import bean.Order;
import bean.OrderItem;
import bean.Store;

public class OrderActivity extends Activity {

    private Button buttonBack;
    private Button buttonPayment;
    private AlertDialog.Builder alertDialogSaveOrder;
    private AlertDialog.Builder alertDialogCancelItem;
    private ListView listViewOrder;
    private int index;
    //    private  ArrayAdapter<String> adapter;
    private MyOrderItemAdapter myOrderItemAdapter;
    private Intent intent;

    //    private ArrayList<OrderItem> itensList;
    //private ArrayList arrayList = new ArrayList<>();
    private ArrayList<String> productList = new ArrayList<>();
    private Order order;
    public ArrayList<OrderItem> orderItemList;
    private String restaurantId = "";
    public String url = "";
    private TextView textViewValueTotalId;
    public String storeUrl;
    public String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = getIntent().getStringExtra("activity");
                if (text.contains("main"))
                    intent = new Intent(OrderActivity.this, MainActivity.class);
                else
                    intent = new Intent(OrderActivity.this, StoreActivity.class);

                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("storeUrl", storeUrl);
                intent.putExtra("storeName", storeName);

                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Requesting webservices to persist Order in database
                if ( orderItemList.size() > 0 ) {

                    alertDialogSaveOrder = new AlertDialog.Builder(OrderActivity.this);
                    alertDialogSaveOrder.setTitle("Save Order");
                    alertDialogSaveOrder.setMessage("Do want to close and to do the payment?");
                    alertDialogSaveOrder.setCancelable(false);

                    alertDialogSaveOrder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    alertDialogSaveOrder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    saveOrder();
                                }
                            });
                    alertDialogSaveOrder.create();
                    alertDialogSaveOrder.show();
                }


            }
        });


        listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                index = position;

                try{
                    final

                    OrderItem orderItem = (OrderItem)listViewOrder.getAdapter().getItem(index);

                    if ( orderItem != null ){
                        System.out.println("É um objeto da lista");
                        alertDialogCancelItem = new AlertDialog.Builder(OrderActivity.this);
                        alertDialogCancelItem.setTitle("Remove Item");
                        alertDialogCancelItem.setMessage("Do want remove this item from Order?");
                        alertDialogCancelItem.setCancelable(false);

                        alertDialogCancelItem.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });

                        alertDialogCancelItem.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //OrderItem orderItem = (OrderItem)listViewOrder.getAdapter().getItem(i);
                                        removeItemFromOrder(orderItem);
                                    }
                                });

                        alertDialogCancelItem.create();
                        alertDialogCancelItem.show();
                    }

                }
                catch(Throwable t){
                }
            }
        });

    }


    public void init() {
        buttonBack = findViewById(R.id.buttonOrderBackId);
        buttonPayment = findViewById(R.id.buttonOrderPaymentId);
        listViewOrder = findViewById(R.id.listViewOrder);
        textViewValueTotalId = findViewById(R.id.textViewValueTotalId);

        orderItemList = new ArrayList<>();

        Bundle extra = getIntent().getExtras();
        String s = extra.getString("restaurantId");
        if (s.length() > 0) {
            restaurantId = extra.getString("restaurantId").toString();
            orderItemList = (ArrayList<OrderItem>) getIntent().getSerializableExtra("orderItemList");
            storeName = extra.getString("storeName").toString();
            storeUrl = extra.getString("storeUrl").toString();
            url             = extra.getString("url").toString();
            if (orderItemList == null) {
                orderItemList = new ArrayList<>();
            }
        }

//        itensList = new String[orderItemList.size()];
//        for (int i = 0; i < orderItemList.size(); i++)
//            itensList[i] = orderItemList.get(i);

//        adapter =  new ArrayAdapter<String>(
//                getApplicationContext(),
//                android.R.layout.simple_list_item_1,
//                android.R.id.text1,
//                itensList);
//        listViewOrder.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        ArrayList<Object> items = new ArrayList<>();

        Integer lastSection = null;

        for (OrderItem orderItem : orderItemList) {
            Integer currentSection = orderItem.getStoreId();

            if (!currentSection.equals(lastSection)) {
                lastSection = currentSection;

                OrderHeader orderHeader = new OrderHeader();

                orderHeader.setUrl(orderItem.getStoreUrl());
                orderHeader.setStoreName(orderItem.getStoreName());

                items.add(orderHeader);
            }

            items.add(orderItem);
        }

        myOrderItemAdapter = new MyOrderItemAdapter(items, OrderActivity.this);

        listViewOrder.setAdapter(myOrderItemAdapter);

        myOrderItemAdapter.notifyDataSetChanged();

        textViewValueTotalId.setText("CAD "+sumOrder());

    }

    public String sumOrder(){
        float totalValue = 0f;

        for( int i = 0; i < orderItemList.size(); i++ ){
            totalValue += orderItemList.get(i).getPrice() * orderItemList.get(i).getQuantity();
        }
        String value = String.format(Locale.CANADA, "%.2f", totalValue);
        return value;
    }


    public void saveOrder() {

        Order order = new Order();
        order.setListItem(orderItemList);

        String json = convertToGson(order);

//        Type orderRecuperado = new TypeToken<Order>(){}.getType();
//        order = new Gson().fromJson(json, orderRecuperado);

        int result  = httpPostRequest(json);

        if ( result > 0){
            Toast.makeText(getApplicationContext(), "Payment in process...", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Payment Accepted. Thank you!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Wasn't possible to process the Order. Please, contact the skip's team support.", Toast.LENGTH_SHORT).show();
        }


    }

    public void removeItemFromOrder(OrderItem orderItem) {
        orderItemList.remove(orderItem);
        //adapter.notifyDataSetChanged();
        init();
    }


    public int httpPostRequest(String json){
        //TODO Post conection with gson String
        String url = "http://"+this.url+":8080/services/order";
        int result = 0;
        try{
            HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
            request.setDoOutput(true);
            request.setDoInput(true);

            request.setRequestProperty("Content-Type","application-jason");
            request.setRequestMethod("PUT");

            request.connect();

            try {
                OutputStream outputStream = request.getOutputStream();
                outputStream.write(json.getBytes("UTF-8"));
                result = request.getResponseCode();
            }catch(Throwable t){
                System.out.println("Outro buxu");
                request.disconnect();
            }
        }
        catch(Throwable ex){
            Log.getStackTraceString(ex);

        }

        return result;
    }

    public String convertToGson(Object obj){
        return new Gson().toJson(obj);
    }



}
