package org.example.cafe;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.InputMismatchException;
import java.util.Scanner;

@Repository
public class OrderCRUD {
    public void createOrder(Session session) {
        Transaction transaction = session.beginTransaction();
        Scanner scanner =  new Scanner(System.in);
        boolean out = false;
        System.out.println("Enter waiter Id");
        while (!out){
            try {
                Integer waiter_id = scanner.nextInt();
                System.out.println("Enter table number ");
                Integer table_number = scanner.nextInt();
                Order order = new Order(waiter_id,table_number);
                session.persist(order);
                out = true;
            } catch (InputMismatchException e) {
                System.out.println("Enter only NUMBER from ID and Table number");
                scanner.next();
            }
        }
        transaction.commit();
    }
    public void deleteOrder(Session session) {
        Transaction transaction = session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        boolean out = false;
        System.out.println("Enter the ID when you want to deleted ");
        while (!out) {
            try {
                Long order_id = scanner.nextLong();
                Order order = session.get(Order.class,order_id);
                if(order!=null) {
                    session.remove(order);
                    out = true;
                } else {
                    System.out.println("Enter the valid order ID ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter Number from ID ");
                scanner.next();
            }
        }
        transaction.commit();
    }
}
