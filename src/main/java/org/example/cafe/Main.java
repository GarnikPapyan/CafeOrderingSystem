package org.example.cafe;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory =HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();

        MenuItemCRUD menuItemCRUD = new MenuItemCRUD();
        OrderCRUD orderCRUD = new OrderCRUD();
        OrderItemCRUD orderItemCRUD = new OrderItemCRUD();
        BillingService billingService = new BillingService();





        // menuItemCRUD.createMenuItem(session);
       // menuItemCRUD.updateMenuItem(session);
       // menuItemCRUD.deleteMenuItem(session);
       // menuItemCRUD.readMenuItem(session);
        //orderCRUD.createOrder(session);
      //  orderItemCRUD.createOrder(session);
       // orderItemCRUD.deleteOrder(session);
        //orderItemCRUD.currentOrders(session);

       // billingService.generateBill(session);
        billingService.printCheck(session);


        session.close();

    }
}