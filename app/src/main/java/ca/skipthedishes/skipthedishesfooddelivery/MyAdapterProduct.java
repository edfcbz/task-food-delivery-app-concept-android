package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import bean.Product;
import bean.Store;

public class MyAdapterProduct extends BaseAdapter {

    private ArrayList<Product> productList;
    public MyAdapterViewHolder myAdapterViewHolder;
    private final Activity activity;

    public MyAdapterProduct(ArrayList<Product> productList, Activity activity) {
        this.productList = productList;
        this.activity = activity;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        myAdapterViewHolder = null;

        if (convertView == null) {

            myAdapterViewHolder = new MyAdapterViewHolder();

            convertView = activity.getLayoutInflater().inflate(R.layout.my_adapter_product, parent, false);

            //pegando as referências das Views
            myAdapterViewHolder.tvProduct    = convertView.findViewById(R.id.textViewProductName);
            myAdapterViewHolder.tvProduct.setTypeface(null, Typeface.BOLD);
            myAdapterViewHolder.tvDescription = convertView.findViewById(R.id.textViewProductDescription);
            myAdapterViewHolder.imageView = convertView.findViewById(R.id.imageView);
            myAdapterViewHolder.textViewProductPrice = convertView.findViewById(R.id.textViewProductPrice);

            convertView.setTag(myAdapterViewHolder);
        } else {
            myAdapterViewHolder = (MyAdapterViewHolder) convertView.getTag();
        }

        if (myAdapterViewHolder != null) {
            Product product = productList.get(position);

            //populando as Views
            myAdapterViewHolder.tvProduct.setText(product.getName());
            myAdapterViewHolder.tvDescription.setText(product.getDescription());
            myAdapterViewHolder.textViewProductPrice.setText("CAD: "+String.format(Locale.CANADA, "%.2f", product.getPrice()));

            try {
                Glide.with(activity).load(product.getProductImage()).into(myAdapterViewHolder.imageView);
            }catch(Throwable t){
                myAdapterViewHolder.imageView.setImageResource(R.drawable.ic_launcher);
            }
        }

        return convertView;
    }

    public class MyAdapterViewHolder {
        public TextView  tvProduct;
        public TextView  tvDescription;
        public ImageView imageView;
        public TextView  textViewProductPrice;
   }
}