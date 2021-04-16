/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import android.location.Address;

import java.io.Serializable;

/**
 *
 * @author Eduardo
 */
public class Store  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String logo;
    private Double deliveryfee;
    private Double deliveryfeefreeover;
    private Integer addressid;
    private String address;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Store() {
    }

    public Store(Integer id, String name, String logo, Double deliveryfee, Double deliveryfeefreeover, Integer addressid, String address) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.deliveryfee = deliveryfee;
        this.deliveryfeefreeover = deliveryfeefreeover;
        this.addressid = addressid;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Double getDeliveryfee() {
        return deliveryfee;
    }

    public void setDeliveryfee(Double deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    public Double getDeliveryfeefreeover() {
        return deliveryfeefreeover;
    }

    public void setDeliveryfeefreeover(Double deliveryfeefreeover) {
        this.deliveryfeefreeover = deliveryfeefreeover;
    }

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }
}
