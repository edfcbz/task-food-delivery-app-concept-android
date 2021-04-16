/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;

/**
 *
 * @author Eduardo
 */
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer orderId;
    private Integer quantity;
    private Double price;

    // TODO: Refatorar, pois existe uma desnormalização de dado normalizado. Ideal é termos uma Store (apenas)...
    private Integer storeId; // Remover
    private String storeName; // Remover
    private String storeAddress; // Remover
    private String storeUrl; // Remover
    // private Store store; // Adicionar

    // TODO: Refatorar, pois existe uma desnormalização de dado normalizado. Ideal é termos um Product (apenas)...
    private String description; // Remover
    private Integer productId; // Remover
    private Product product; // Manter

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }



    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public OrderItem() {
    }

    public OrderItem(Integer id, Integer orderId, Integer productId, Integer quantity, Double price, String description) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
