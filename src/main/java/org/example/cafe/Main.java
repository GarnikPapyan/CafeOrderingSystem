package org.example.cafe;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory =HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        MenuItemCRUD menuItemCRUD = new MenuItemCRUD();
        OrderItemCRUD orderItemCRUD = new OrderItemCRUD();
        BillingService billingService = new BillingService();
        boolean out = false;
        while (!out) {
            System.out.println("You can choose from these three options what to do ");
            System.out.print("1. Menu operations \n");
            System.out.print("2. Order operation \n");
            System.out.print("3. See checks for purchases made \n");
            System.out.print("4. Exit program! \n");
            Scanner scanner = new Scanner(System.in);
            String str =  scanner.nextLine();
            switch (str) {
                case "1" -> {
                    boolean out2 = false;
                    while (!out2) {
                        System.out.print("\n");
                        System.out.println("Here you can add, update or remove new products to the menu ");
                        System.out.print("1. Add product to Menu \n");
                        System.out.print("2. Update product in Menu \n");
                        System.out.print("3. Remove product from menu \n");
                        System.out.print("4. Out(Back one step ) \n");
                        System.out.print("5. Exit Program \n");
                        String prod = scanner.nextLine();
                        switch (prod) {
                            case "1" -> menuItemCRUD.createMenuItem(session);
                            case "2" -> menuItemCRUD.updateMenuItem(session);
                            case "3" -> menuItemCRUD.deleteMenuItem(session);
                            case "4" -> out2 = true;
                            case "5" -> {
                                out = true;
                                out2 = true;
                            }
                            default -> System.out.println("Input 1,2,3,4 or 5 !!!");
                        }
                    }
                }
                case "2" -> {
                    boolean out3 = false;
                    while (!out3) {
                        System.out.print("\n");
                        System.out.println("Here you can add a new order or cancel it, as well " +
                                "as mark those orders that were placed to the uploader and see " +
                                "who ordered what and how much this order costs at this moment ");
                        System.out.print("1. Create order \n");
                        System.out.print("2. Cancel order \n");
                        System.out.print("3. Mark that the order is ready \n");
                        System.out.print("4. Current orders \n");
                        System.out.print("5. Out(Back one step) \n");
                        System.out.print("6. Exit Program \n");
                        String orders = scanner.nextLine();
                        switch (orders) {
                            case "1" -> {
                                orderItemCRUD.createOrder(session);
                                billingService.generateBill(session);
                            }
                            case "2" -> {
                                orderItemCRUD.deleteOrder(session);
                                billingService.generateBill(session);
                            }
                            case "3" -> orderItemCRUD.orderReady(session);
                            case "4" -> orderItemCRUD.currentOrders(session);
                            case "5" -> out3 = true;
                            case "6" -> {
                                out3 = true;
                                out = true;
                            }
                            default -> System.out.println("Input 1,2,3,4,5 or 6 ");
                        }
                    }
                }
                case "3" -> {
                    boolean out4 = false;
                    while (!out4) {
                        System.out.print("\n");
                        System.out.println("Here you can use your bill ID to see the purchase receipt, " +
                                "taking into account the service fee (10%), tax (20%), and tip (10%).");
                        System.out.print("1. See checks from Bill ID \n");
                        System.out.print("2. Out(Back one step) \n");
                        System.out.print("3. Exit program \n");
                        String sell = scanner.nextLine();
                        switch (sell) {
                            case "1" -> billingService.printCheck(session);
                            case "2" -> out4 = true;
                            case "3" -> {
                                out4 = true;
                                out = true;
                            }
                            default -> System.out.println("Input 1,2 or 3 ");
                        }
                    }
                }
                case "4" -> out = true;
                default -> System.out.println("Enter only 1 , 2 or 3 ");
            }
        }
        session.close();
    }
}