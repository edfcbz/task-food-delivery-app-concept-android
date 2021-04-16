package bean;

import java.util.ArrayList;

public class LittleCar {

    public ArrayList<Order> orderList;

    public LittleCar(){
        this.orderList = new ArrayList<>();
        this.orderList.add(new Order());
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }
}