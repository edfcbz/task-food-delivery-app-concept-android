package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import bean.Store;

public class MyAdapterStore extends BaseAdapter {

    private ArrayList<Store> storeList;
    private final Activity activity;

    public MyAdapterStore(ArrayList<Store> storeList, Activity activity) {
        this.storeList = storeList;
        this.activity = activity;
    }

    public ArrayList<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(ArrayList<Store> storeList){
        this.storeList = storeList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int position) {
        return storeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return storeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAdapterViewHolder myAdapterViewHolder = null;

        if (convertView == null) {
            myAdapterViewHolder = new MyAdapterViewHolder();

            convertView = activity.getLayoutInflater().inflate(R.layout.my_adapter_store, parent, false);

            //pegando as referências das Views
            myAdapterViewHolder.tvName    = convertView.findViewById(R.id.textViewStoreName);
            myAdapterViewHolder.tvName.setTypeface(null, Typeface.BOLD);
            myAdapterViewHolder.tvAddress = convertView.findViewById(R.id.textViewStoreAddress);
            //myAdapterViewHolder.tvPrice = convertView.findViewById(R.id.textViewStorePrice);
            myAdapterViewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(myAdapterViewHolder);
        } else {
            myAdapterViewHolder = (MyAdapterViewHolder) convertView.getTag();
        }

        if (myAdapterViewHolder != null) {
            Store store = storeList.get(position);

            //populando as Views
            myAdapterViewHolder.tvName.setText(store.getName());
            myAdapterViewHolder.tvAddress.setText(store.getAddress());
            try {
                Glide.with(activity).load(store.getLogo()).into(myAdapterViewHolder.imageView);
            }catch(Throwable t){
                myAdapterViewHolder.imageView.setImageResource(R.drawable.ic_launcher);
            }

        }

        return convertView;
    }

    private class MyAdapterViewHolder {
        public TextView tvName;
        public TextView tvAddress;
        public TextView tvPrice;
        public ImageView imageView;
    }
}