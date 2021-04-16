package bean;


        import java.io.Serializable;
        import java.sql.Timestamp;
        import java.util.ArrayList;
        import java.util.List;

/**
 *
 * @author Eduardo
 */
public class Order implements Serializable {

    private Integer id;
    private Timestamp date;
    private Integer customerId;
    private String contact;
    private Integer addressId;
    private List<OrderItem> listItem;

    public List<OrderItem> getListItem() {
        return listItem;
    }

    public void setListItem(List<OrderItem> listItem) {
        this.listItem = listItem;
    }

    public Order() {
        this.listItem = new ArrayList<>();
        this.listItem.add(new OrderItem());
    }

    public Order(Integer id, Timestamp date, Integer customerId, String contact, Integer addressId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.contact = contact;
        this.addressId = addressId;
        this.listItem = new ArrayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    public Double getTotal() {
        Double result = 0.0;
        for (OrderItem orderItem : listItem) {
            result += orderItem.getPrice() * orderItem.getQuantity();
        }
        return result;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
