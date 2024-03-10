package org.example.cafe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderId;
    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @Column(name = "price_at_time",nullable = false)
    private Double price_at_time;

    public Double getPrice_at_time() {
        return price_at_time;
    }

    public void setPrice_at_time(Double price_at_time) {
        this.price_at_time = price_at_time;
    }

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "item_id",referencedColumnName = "item_id")
    private MenuItem menuItem;

    public OrderItem() {
    }

    public OrderItem(Integer quantity, MenuItem menuItem) {
        this.quantity = quantity;
        this.menuItem = menuItem;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
}
