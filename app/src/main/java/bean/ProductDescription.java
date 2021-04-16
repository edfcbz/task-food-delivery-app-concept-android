package bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDescription implements Serializable {

    private ArrayList<String> listProduct;

    public ProductDescription(){
        listProduct = new ArrayList<>();
    }

    public ArrayList<String> getArrayList() {
        return listProduct;
    }

}