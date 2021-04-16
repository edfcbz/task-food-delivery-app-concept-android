package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import bean.Product;

public class MyAdapterOrderBody extends BaseAdapter {

    private ArrayList<Product> productList;
    private final Activity activity;
    public MyAdapterViewHolder myAdapterViewHolder;

    public MyAdapterViewHolder getHolder(){
        return myAdapterViewHolder;
    }

    public MyAdapterOrderBody(ArrayList<Product> productList, Activity activity) {
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
            myAdapterViewHolder.tvPrice = convertView.findViewById(R.id.textViewProductPrice);
            convertView.setTag(myAdapterViewHolder);
        } else {
            myAdapterViewHolder = (MyAdapterViewHolder) convertView.getTag();
        }

        if (myAdapterViewHolder != null) {
            Product product = productList.get(position);

            //populando as Views
            myAdapterViewHolder.tvProduct.setText(product.getName());
            myAdapterViewHolder.tvDescription.setText(product.getDescription());
            myAdapterViewHolder.tvPrice.setText(product.getPrice().toString());
        }

        return convertView;
    }

    public class MyAdapterViewHolder {
        public TextView tvProduct;
        public TextView tvDescription;
        public TextView tvPrice;
    }
}