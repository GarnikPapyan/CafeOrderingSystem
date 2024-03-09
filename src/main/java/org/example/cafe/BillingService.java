package org.example.cafe;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BillingService {

    public void generateBill(Session session) {
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT oi FROM OrderItem oi";
        List<OrderItem> orderItemList = session.createQuery(sql, OrderItem.class).getResultList();

        for (OrderItem orderItem : orderItemList) {

            Query query = session.createQuery("SELECT b FROM Billing b WHERE b.order = :orderItem");
            query.setParameter("orderItem", orderItem.getOrder());
            List<Billing> existingBillings = query.getResultList();

            if (existingBillings.isEmpty()) {
                Double fee = orderItem.getPrice_at_time() * 0.1;
                Double tax = orderItem.getPrice_at_time() * 0.2;
                Double tip = orderItem.getPrice_at_time() * 0.1;
                Double total = orderItem.getPrice_at_time() + fee + tax + tip;
                Billing billing = new Billing(orderItem.getOrder(), fee, tax, tip, total);
                session.persist(billing);
            }
        }

        transaction.commit();
    }

   public void printCheck(Session session) {
       Transaction transaction = session.beginTransaction();
       Scanner scanner = new Scanner(System.in);
       System.out.println("Enter Bill ID which check you want to see ");
       boolean out = false;
       while(!out) {
           try {
               Long billId = scanner.nextLong();
               Billing billing = session.get(Billing.class,billId);
               if(billing!=null) {
                   String fee = String.format("%.3f",billing.getServiceFee());
                   String tax = String.format("%.3f",billing.getTax());
                   String tip = String.format("%.3f",billing.getTip());
                   String total = String.format("%.3f",billing.getTotal());
                   Long orderId = billing.getOrder().getOrderId();
                   OrderItem orderItem = session.get(OrderItem.class,orderId);
                   String name = orderItem.getMenuItem().getName();
                   Double priceOne = orderItem.getMenuItem().getPrice();
                   Integer quantity = orderItem.getQuantity();
                   System.out.println("Product Name  |   Quantity  |   Price fro One  |" );
                   System.out.println("______________|_____________|__________________|");
                   System.out.println(name + "       |     "+ quantity + "       |  " + priceOne + "$           |");
                   System.out.println("______________|_____________|__________________|");
                   System.out.println("Service Fee:      "  + fee +"$");
                   System.out.println("Tax:              " + tax +"$");
                   System.out.println("Tip:              " + tip +"$");
                   System.out.println("Total:            " + total +"$");
                   out = true;
                   transaction.commit();
               } else{
                   System.out.println("Not valid ID try again! ");
               }
           } catch (InputMismatchException e) {
               System.out.println("Enter only NUMBER from bill ID!");
               scanner.next();
           }
       }
   }
}
