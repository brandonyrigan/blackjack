package com.pcbuilder.session;

import com.pcbuilder.checkout.Order;
import com.pcbuilder.checkout.ShoppingCart;
import com.pcbuilder.customer.Customer;
import com.pcbuilder.inventory.Component;
import com.pcbuilder.inventory.Inventory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class Session {

    private UUID uuid;
    private LocalDate sessionStart;
    private LocalDate sessionEnd;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private Order order;

    public Session( String[] customerInfo ) {
        setCustomer( customerInfo );
//        setInventory( fetchInventory() );
        setShoppingCart( createNewShoppingCart() );
        setOrder( createNewOrder() );
        setUuid();
        setSessionStartDate();
        setSessionEndDate();
    }


//  Business Logic


//  Helper Methods
    public Order createNewOrder(){
    Order newOrder = new Order();
    return newOrder;
}
    public ShoppingCart createNewShoppingCart(){
        ShoppingCart newShoppingCart = new ShoppingCart();
        return newShoppingCart;
    }
    public Map<String, Collection<Component>> fetchMapOfInventory(){
        Map<String, Collection<Component>> fetchedMapOfInventory = Inventory.mapOfInventory;
        return fetchedMapOfInventory;
    }


//  Accessor Methods
    public UUID getUuid() { return uuid; }
    public void setUuid() {
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid;
    }
    public LocalDate getSessionStartDate() { return this.sessionStart; }
    public void setSessionStartDate() {
        LocalDate start = LocalDate.from(LocalDateTime.now());
        this.sessionStart = start;
    }
    public LocalDate getSessionEndDate() { return sessionEnd; }
    public void setSessionEndDate() {
        LocalDate end = LocalDate.from(LocalDateTime.now());
        this.sessionEnd = end;
    }
    public Customer getCustomer() { return customer;}
    public void setCustomer(String... customerInfo) {
        Customer guestCustomer = new Customer(customerInfo[0],customerInfo[1],customerInfo[2]);
        this.customer = guestCustomer;
    }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = null;}
    public ShoppingCart getShoppingCart() { return shoppingCart; }
    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart;}


}