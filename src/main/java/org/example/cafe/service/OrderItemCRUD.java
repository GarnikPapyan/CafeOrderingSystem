package org.example.cafe.service;

import org.example.cafe.entity.OrderItem;
import org.example.cafe.entity.MenuItem;
import org.example.cafe.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
@Service
public class OrderItemCRUD {
    public void createOrder(Session session) {
        Transaction transaction = session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        boolean out = false;
        MenuItemCRUD menuItemCRUD = new MenuItemCRUD();
        menuItemCRUD.readMenuItem(session);
        System.out.println();
        System.out.println("Enter Menu item Id ");
        while (!out){
            try {
                Long item_id = scanner.nextLong();
                MenuItem menuItem = session.get(MenuItem.class,item_id);
                if(menuItem!=null) {
                    System.out.println("enter quantity order ");
                    while (!out) {
                        try {
                            Integer quantity = scanner.nextInt();
                            Order order = new Order();
                            System.out.println("Enter Table Number");
                            Integer tableNumber = scanner.nextInt();
                            System.out.println("Enter Waiter ID ");
                            Integer waiter_id = scanner.nextInt();
                            order.setWaiterId(waiter_id);
                            order.setTableNumber(tableNumber);
                            session.persist(order);
                            Double price_at_time = quantity*menuItem.getPrice();
                            OrderItem orderItem = new OrderItem(quantity,menuItem);
                            orderItem.setOrder(order);
                            orderItem.setPrice_at_time(price_at_time);
                            session.persist(orderItem);
                            out = true;
                            System.out.println("Created was successful");
                        } catch (InputMismatchException e) {
                            System.out.println("Enter only NUMBER !!!");
                            scanner.next();
                        }
                    }
                } else {
                    System.out.println("Enter the valid ID from menu ");
                }
            } catch (InputMismatchException e){
                System.out.println("Enter only number from ID! ");
                scanner.next();
            }
        }
        transaction.commit();
    }

    public void orderReady(Session session) {
        Transaction transaction = session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        boolean out = false;
        System.out.println("Enter order Id when is ready ");
        while (!out){
            try {
                Long order_id = scanner.nextLong();
                Order order = session.get(Order.class,order_id);
                if(order!=null){
                    order.setFinalized(true);
                    out = true;
                } else {
                    System.out.println("Enter valid ID ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter only NUMBER from ID");
                scanner.next();
            }

        }
        transaction.commit();
    }


    public void  deleteOrder(Session session) {
        Transaction transaction = session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        boolean out = false;
        System.out.println("Enter order ID when you want to delete");
        while (!out){
            try{
                Long orderId = scanner.nextLong();
                Order order = session.get(Order.class,orderId);
                if(order!=null){
                    session.remove(order);
                    String hql = "DELETE FROM OrderItem WHERE order = :order";
                    String sql = "DELETE FROM Billing WHERE order.orderId = :orderId";
                    session.createQuery(hql).setParameter("order", order).executeUpdate();
                    session.createQuery(sql).setParameter("orderId",orderId).executeUpdate();
                    out = true;
                } else {
                    System.out.println("Enter valid ID");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter Only NUMBER from ID ");
                scanner.next();
            }
        }
        transaction.commit();
    }

    public void currentOrders(Session session){
        Transaction transaction =  session.beginTransaction();
        String sql = "SELECT oi FROM OrderItem oi WHERE oi.order.isFinalized = false";
        List<OrderItem> lists = session.createQuery(sql,OrderItem.class).getResultList();
        for(OrderItem list: lists) {
            System.out.println("Order ID: " + list.getOrderId() + " | " + "Item name: " + list.getMenuItem().getName()
                    + " | " + "Quantity: " + list.getQuantity() + " | " + "Price at time: " +  list.getPrice_at_time()
                    + "$   |" ) ;
            System.out.println("____________|__________________|_____________|________________________|");
        }
        transaction.commit();
    }
}
