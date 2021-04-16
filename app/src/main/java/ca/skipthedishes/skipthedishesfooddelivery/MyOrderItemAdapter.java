package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import bean.OrderItem;
import bean.Product;

public class MyOrderItemAdapter extends BaseAdapter {

    private static final int HEADER = 0;
    private static final int BODY = 1;

    private ArrayList<Object> dataList;
    private final Activity activity;

    public MyOrderItemAdapter(ArrayList<Object> dataList, Activity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    public ArrayList<Object> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Object> dataList) {
        this.dataList = dataList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = dataList.get(position);

        if (object instanceof OrderHeader) {
            return HEADER;
        } else {
            return BODY;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyBaseAdapterViewHolder myBaseAdapterViewHolder = null;

        int itemViewType = getItemViewType(position);

        if (convertView == null) {
            switch (itemViewType) {
                case HEADER:
                    convertView = activity.getLayoutInflater().inflate(R.layout.my_adapter_order_head, parent, false);

                    MyHeaderAdapterViewHolder myHeaderAdapterViewHolder = new MyHeaderAdapterViewHolder();

                    myHeaderAdapterViewHolder.imageView = convertView.findViewById(R.id.imageView);

                    myHeaderAdapterViewHolder.textViewStoreName = convertView.findViewById(R.id.textViewStoreName);

                    myBaseAdapterViewHolder = myHeaderAdapterViewHolder;

                    convertView.setTag(myHeaderAdapterViewHolder);

                    break;
                case BODY:
                    convertView = activity.getLayoutInflater().inflate(R.layout.my_adapter_order_body, parent, false);

                    MyBodyAdapterViewHolder myBodyAdapterViewHolder = new MyBodyAdapterViewHolder();

                    myBodyAdapterViewHolder.textViewProductName = convertView.findViewById(R.id.textViewProductName);

                    myBodyAdapterViewHolder.textViewProductDescription = convertView.findViewById(R.id.textViewProductDescription);

                    myBodyAdapterViewHolder.textViewProductPrice = convertView.findViewById(R.id.textViewProductPrice);

                    myBaseAdapterViewHolder = myBodyAdapterViewHolder;

                    convertView.setTag(myBodyAdapterViewHolder);

                    break;
            }


        } else {
            myBaseAdapterViewHolder = (MyBaseAdapterViewHolder) convertView.getTag();
        }

        if (myBaseAdapterViewHolder != null) {
            Object item = getItem(position);

            switch (itemViewType) {
                case HEADER:
                    OrderHeader orderHeader = (OrderHeader) item;

                    MyHeaderAdapterViewHolder myHeaderAdapterViewHolder = (MyHeaderAdapterViewHolder) myBaseAdapterViewHolder;

                    Glide.with(activity).load(orderHeader.getUrl()).into(myHeaderAdapterViewHolder.imageView);

                    //String s = orderHeader.getStoreName().toString();

                    myHeaderAdapterViewHolder.textViewStoreName.setText(orderHeader.getStoreName());

                    break;
                case BODY:
                    OrderItem orderItem = (OrderItem) item;

                    MyBodyAdapterViewHolder myBodyAdapterViewHolder = (MyBodyAdapterViewHolder) myBaseAdapterViewHolder;

                    Product product = orderItem.getProduct();

                    if (product != null) {
                        myBodyAdapterViewHolder.textViewProductName.setText(product.getName());

                        myBodyAdapterViewHolder.textViewProductDescription.setText(product.getDescription());

                        myBodyAdapterViewHolder.textViewProductPrice.setText(String.format(Locale.CANADA, "%.2f", product.getPrice()));
                    } else {
                        myBodyAdapterViewHolder.textViewProductName.setText("-");

                        myBodyAdapterViewHolder.textViewProductDescription.setText("-");

                        myBodyAdapterViewHolder.textViewProductPrice.setText("-");
                    }

                    break;
            }
        }

        return convertView;
    }

    private interface MyBaseAdapterViewHolder {
    }

    private class MyHeaderAdapterViewHolder implements MyBaseAdapterViewHolder {

        public ImageView imageView;
        public TextView textViewStoreName;

    }

    private class MyBodyAdapterViewHolder implements MyBaseAdapterViewHolder {

        public TextView textViewProductName;
        public TextView textViewProductDescription;
        public TextView textViewProductPrice;

    }

}