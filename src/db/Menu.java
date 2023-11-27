package db;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

// This class contains the menus for different users
public class Menu {
    
    static void adminMenu(Admin admin) {
        
        System.out.print("-----Operations for administrator menu-----\n"
                       + "What kinds of operation would you like to perform?\n"
                       + "1. Create all tables\n"
                       + "2. Delete all tables\n"
                       + "3. Load from datafile\n"
                       + "4. Show content of a table\n"
                       + "5. Return to the main menu\n");
        System.out.print("Enter Your Choice: ");       
        Scanner input = new Scanner(System.in);
        switch(input.nextInt()) {
            case 1:
                admin.CreateTable();
                System.out.println();
                adminMenu(admin);
                break;
            case 2:
                admin.DeleteTable();
                System.out.println();
                adminMenu(admin);
                break;
            case 3:
                admin.LoadData();
                System.out.println();
                adminMenu(admin);
                break;
            case 4:
                System.out.print("Which table would you like to show: ");
                admin.ShowTable(input.nextLine());
                System.out.println("End of Query\n");
                adminMenu(admin);
                break;
        }
        
    }
    
    static void salesMenu(Sales sales) {
        
        System.out.println("-----Operations for salesperson menu-----\n"
                         + "What kinds of operation would you like to perform?");
        System.out.println("1. Search for parts\n"
                         + "2. Sell a part\n"
                         + "3. Return to the main menu");
        System.out.print("Enter Your Choice: ");
        Scanner input = new Scanner(System.in);
        switch(input.nextInt()) {
            case 1:
                System.out.println("");
                System.out.println("Choose the Search criterion:\n"
                                 + "1. Part Name\n"
                                 + "2. Manufacturer Name");
                System.out.print("Choose the search criterion: ");
                int crit = input.nextInt();
                System.out.print("Type the Search Keyword: ");
                String keyword = input.nextLine();
                System.out.println("Choose ordering:\n"
                                 + "1. By price, ascending order\n"
                                 + "2. By price, descending order");
                System.out.print("Choose the search criterion: ");
                int order = input.nextInt();
                sales.SearchParts(crit, keyword, order);
                System.out.println("End of Query\n");
                salesMenu(sales);
                break;
            case 2:
                System.out.println("");
                System.out.print("Enter the Part ID: ");
                int pID = input.nextInt();
                System.out.print("Enter the Salesperson ID: ");
                int sID = input.nextInt();
                sales.Transaction(pID, sID);
                System.out.println("End of Query\n");
                salesMenu(sales);
                break;
        }
        
    }
    
    static void managerMenu(Manager manager) {
        
        System.out.println("-----Operations for manager menu-----\n"
                         + "What kinds of operation would you like to perform?");
        System.out.println("1. List all salespersons\n"
                         + "2. Count the no. of sales record of each salesperson under a specific range on years of experience\n"
                         + "3. Show the total sales value of each manufacturer\n"
                         + "4. Show the N most popular part\n"
                         + "5. Return to the main menu");
        Scanner input = new Scanner(System.in);
        switch(input.nextInt()) {
            case 1:
                System.out.println("");
                System.out.println("Choose ordering:\n"
                                    + "1. By ascending order\n"
                                    + "2. By descending order");
                System.out.print("Choose the list ordering: ");
                int order = input.nextInt();
                manager.ListSalespersons(order);
                System.out.println("End of Query\n");
                managerMenu(manager);
                break;
            case 2:
                System.out.println("");
                System.out.println("Type in the lower bound for years of experience: ");
                int lb = input.nextInt();
                System.out.print("Type in the upper bound for years of experience: ");
                int ub = input.nextInt();
                manager.CountSalespersons(lb, ub);
                System.out.println("End of Query\n");
                managerMenu(manager);
                break;
            case 3:
                manager.SortListSalesValue();
                System.out.println();
                System.out.println("End of Query\n");
                managerMenu(manager);
                break;
            case 4:
                System.out.println("");
                System.out.println("Type in the number of parts: ");
                int N = input.nextInt();
                manager.ShowNPopular(N);
                System.out.println("End of Query\n");
                managerMenu(manager);
                break;
        }
        
    }
    
}
