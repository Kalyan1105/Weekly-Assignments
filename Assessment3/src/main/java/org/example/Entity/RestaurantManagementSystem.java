package org.example.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class RestaurantManagementSystem {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("1: Add Menu Item");
            System.out.println("2: 2.View All Items");
            System.out.println("3: Update Price");
            System.out.println("4: Delete Item");
            System.out.println("5: Exit");

            System.out.println("Choose what you want to do!");
            int choice = sc.nextInt();
            sc.nextLine();

            Session session = factory.openSession();
            Transaction trx=null;
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("Enter Catagory: ");
                        String catagory = sc.nextLine();
                        System.out.print("Is Available true or false: ");
                        boolean available = sc.nextBoolean();


                        trx = session.beginTransaction();

                        MenuItem menu = new MenuItem(name, price, catagory, available);
                        session.persist(menu);
                        trx.commit();
                        session.close();
                        break;

                    case 2:
                        List<MenuItem> list = session.createQuery("FROM MenuItem", MenuItem.class).list();
                        list.forEach(System.out::println);
                        break;

                    case 3:
                        System.out.println("Enter Id to update");
                        int updateId = sc.nextInt();
                        System.out.println("Enter New Price");
                        double newPrice = sc.nextDouble();

                        trx = session.beginTransaction();
                        MenuItem updateItem = session.get(MenuItem.class, updateId);
                        if (updateItem != null) {
                            updateItem.setPrice(newPrice);
                            trx.commit();
                            System.out.println("Update done");
                        } else {
                            trx.rollback();
                        }
                        break;

                    case 4:
                        System.out.println("Enter Id to delete");
                        int deletes = sc.nextInt();

                        trx = session.beginTransaction();
                        MenuItem itemDelete = session.get(MenuItem.class, deletes);
                        if (itemDelete != null) {
                            session.remove(itemDelete);
                            trx.commit();
                            System.out.println("Delete done");
                        } else {
                            trx.rollback();
                        }
                        break;

                    case 5:
                        session.close();
                        factory.close();
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Enter valid option");
                }
            }catch (Exception e){
                if(trx!=null){
                    trx.rollback();
                }
                e.getMessage();
            }finally {
                session.close();
            }
        }
    }
}
