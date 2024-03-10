package org.example.cafe.service;

import org.example.cafe.entity.MenuItem;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class MenuItemCRUD {
    public void createMenuItem(Session session) {
        Transaction transaction = session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new menu item category ");
        String category = scanner.nextLine();
        System.out.println("Enter new menu item name ");
        String name = scanner.nextLine();
        System.out.println("Enter new menu item description ");
        String description = scanner.nextLine();
        boolean out = false;
        double price = 0.0;
        while (!out){
            try {
                System.out.println("Enter new menu item price ");
                price = scanner.nextDouble();
                out = true;
            } catch (InputMismatchException e) {
                System.out.println("Enter only number from menu item price!!! ");
                scanner.next();
            }
        }
        MenuItem menuItem = new MenuItem(name,description,category,price);
        session.persist(menuItem);
        transaction.commit();
        System.out.println("Created was successful");
    }

    public void updateMenuItem(Session session) {
        Transaction transaction = session.beginTransaction();
        Scanner scanner =  new Scanner(System.in);
        boolean out = false;
        System.out.println("Enter the ID from the menu that you want to update ");
        while (!out) {
            try{
                Long item_id = scanner.nextLong();
                scanner.nextLine();
                MenuItem menuItem = session.get(MenuItem.class,item_id);
                if(menuItem!=null) {
                    System.out.println("Now you can update the item name");
                    String name = scanner.nextLine();
                    System.out.println("Now you can update the item description ");
                    String description = scanner.nextLine();
                    System.out.println("Now you can update the item category ");
                    String category = scanner.nextLine();
                    System.out.println("Now you can update the item price ");
                    while (!out){
                        try {
                            Double price = scanner.nextDouble();
                            menuItem.setName(name);
                            menuItem.setDescription(description);
                            menuItem.setCategory(category);
                            menuItem.setPrice(price);
                            session.persist(menuItem);
                            out = true;
                            System.out.println("Updated was successful");
                        } catch (InputMismatchException e) {
                            System.out.println("Enter only NUMBER from menu item price!!! ");
                            scanner.next();
                        }
                    }
                } else {
                    System.out.println("This is not valid ID plz try again! \n " +
                            "Enter the valid ID ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter only NUMBER from ID!!! ");
                scanner.next();
            }
        }
        transaction.commit();
    }
    public void deleteMenuItem(Session session) {
        Transaction transaction = session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        boolean out = false;
        System.out.println("Enter item ID when you want to deleted ");
        while (!out) {
            try {
                Long item_id = scanner.nextLong();
                MenuItem menuItem = session.get(MenuItem.class,item_id);
                if(menuItem!=null) {
                    session.remove(menuItem);
                    System.out.println("Deleted was successful");
                    out = true;
                } else {
                    System.out.println("This is not valid ID plz try again! \n" +
                            "Enter the valid ID ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter only NUMBER from item ID!!! ");
                scanner.next();
            }
        }
        transaction.commit();
    }
    public void readMenuItem(Session session) {
        String hqlQuery = "FROM MenuItem WHERE category = :category";

        List<String> categories = session.createQuery("SELECT DISTINCT category FROM MenuItem", String.class).getResultList();

        for (String category : categories) {
            System.out.println("Category: " + category);

            List<MenuItem> menuItems = session.createQuery(hqlQuery, MenuItem.class)
                    .setParameter("category", category)
                    .getResultList();

            for (MenuItem menuItem : menuItems) {
                System.out.println("  Item ID: " + menuItem.getItemId() + ", Name: " + menuItem.getName() + ", Description: " + menuItem.getDescription() + ", Price: " + menuItem.getPrice());
            }
        }
    }
}
