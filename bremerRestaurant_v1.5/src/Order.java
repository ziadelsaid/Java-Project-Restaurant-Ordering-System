import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int orderID;
    private final List<OrderItem> items;
    private double totalPrice;
    private String orderType;
    private String orderAddress;
    private String paymentMethod;
    private double promoCodeDiscount;

    public Order(int orderID) {
        this.orderID = orderID;
        this.items = new ArrayList<>();
        promoCodeDiscount = 0;
    }

    public int getOrderID() {
        return orderID;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPromoCodeDiscount(double promoCodeDiscount) {
        this.promoCodeDiscount = promoCodeDiscount;
    }

    public void addOrderItem(MenuItem menuItem, int quantity) {
        if (quantity > 0) {
            this.items.add(new OrderItem(menuItem, quantity));
        } else{
            System.out.println("Invalid quantity");
        }
    }
    public void clearOrder() {
        this.items.clear();
    }

    public void editQuantity(OrderItem orderItem, int quantity){
        if (quantity > 0){
          orderItem.setQuantity(quantity);
        } else {
            System.out.println("Please enter valid quantity");
        }
    }

    public void removeItem(OrderItem orderItem){
        this.items.remove(orderItem);
    }

    public void displayOrderSummary() {

            if (items.isEmpty()){
                System.out.println("""
                    --------------------------------
                    Start adding items to your order
                    --------------------------------""");
            } else {
                System.out.println("""
                        -------------
                        Order Summary
                        -------------""");
                int decimalPlaces = 2;
                DecimalFormat df = new DecimalFormat("#." + "0".repeat(decimalPlaces));
                double foodTotal = 0.0;
                int i = 1;
                for(OrderItem item : this.items){
                    System.out.println("[" + i + "]  " + item.getQuantity() + "x " + item.getMenuItem().name() + " | " + item.getMenuItem().price() +
                            " | " + item.getMenuItem().price()*item.getQuantity());
                    i++;
                    foodTotal += item.getMenuItem().price()*item.getQuantity();
                }

                double VAT = foodTotal * 0.14;

                if(promoCodeDiscount > 0){
                    System.out.println("Promo code is applied! Discount " + promoCodeDiscount);
                    foodTotal = foodTotal - promoCodeDiscount;
                }

                setTotalPrice(foodTotal + VAT);
                System.out.println("----------------------\n" + "VAT(14%) = " + df.format(VAT) + " EGP" +
                        "\nTotal = " + df.format(getTotalPrice()) + " EGP" + "\n----------------------");
                System.out.println();
            }

    }
    public void displayOrder(){
        if (getOrderType().equals("Delivery")){
            System.out.println("\nOrder ID: " + getOrderID()  + "\nOrder Type: " + getOrderType() + "\nDelivery Address: " + getOrderAddress());
        } else {
            System.out.println("\nOrder ID: " + getOrderID()  + "\nOrder Type: " + getOrderType());
        }
        displayOrderSummary();
        System.out.println("Payment Method: " + getPaymentMethod());
    }
}