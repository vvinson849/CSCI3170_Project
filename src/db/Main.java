package db;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    
    public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db52?autoReconnect=true&useSSL=false";
    public static String dbUsername = "Group52";
    public static String dbPassword = "CSCI3170";
    
    // Method for database connection
    public static Connection connectToMySQL(){
        
        Connection con = null;
        try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        } 
        catch (ClassNotFoundException e){
                System.out.println("[Error]: Java MySQL DB Driver not found!!");
                System.exit(0);
        } 
        catch (SQLException e){
                System.out.println(e);
        }
        return con;
        
    }
    
    public static void main(String[] args) {
        
        // Connect to the database
        Connection con = connectToMySQL();
        
        Admin admin = new Admin(con);
        Sales sales = new Sales(con);
        Manager manager = new Manager(con);
        
        Scanner input = new Scanner(System.in);
        
        // Main menu and loop of the programme
        System.out.println("Welcome to sales system!\n");
        while(true) {
            System.out.print("-----Main menu-----\n"
                       + "What kind of operation would you like to perform?\n"
                       + "1. Operations for administrator\n2"
                       + "2. Operations for salesperson\n"
                       + "3. Operations for manager\n"
                       + "4. Exit this program\n");
            System.out.print("Enter Your Choice: ");
            switch(input.nextInt()) {
            case 1:
                Menu.adminMenu(admin);
                break;
            case 2:
                Menu.salesMenu(sales);
                break;
            case 3:
                Menu.managerMenu(manager);
                break;
            case 4:
                return;
            }
        }
        
    }
    
}
