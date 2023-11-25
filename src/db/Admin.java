package db;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

// This class contains the four operations for administrator
public class Admin {
    
    private Connection conn; // for database connection
        
    public Admin(Connection con) {
        conn = con;
    }
    
    void CreateTable() {
        
        try {
            Statement stmt = conn.createStatement();

            String createCategory = 
                "CREATE TABLE Category(" + 
                "cID NUMBER(10) PRIMARY KEY" + 
                "cName CHAR(30)" +
                ")";

            String createManufacturer = 
                "CREATE TABLE Manufacturer(" + 
                "mID NUMBER(10) PRIMARY KEY" + 
                "mName CHAR(30)" +
                "mAddress CHAR(30)" + 
                "mPhoneNumber NUMBER(10)" + 
                ")";

            String createPart = 
                "CREATE TABLE Part(" + 
                "pID NUMBER(10) PRIMARY KEY," + 
                "pName CHAR(30)" + 
                "pPrice NUMBER(10)" + 
                "FOREIGN KEY mID NUMBER(10) REFERENCES Manufacturer(mID)" +
                "FOREIGN KEY cID NUMBER(10) REFERENCES Category(cID)" + 
                "pWarrantyPeriod NUMBER(10)" + 
                "pAvailableQuantity NUMBER(10)" +
                ")";

            String createSalesperson = 
                "CREATE TABLE Salesoerson(" + 
                "sID NUMBER(10) PRIMARY KEY" + 
                "sName CHAR(30)" +
                "sAddress CHAR(30)" + 
                "sPhoneNumber NUMBER(10)" + 
                "sExperience NUMBER(10)" +
                ")";

            String createTransaction = 
                "CREATE TABLE Transaction(" + 
                "tID NUMBER(10) PRIMARY KEY" +
                "tDate DATE" +
                "FOREIGN KEY pID NUMBER(10) REFERENCES Part(pID)" +
                "FOREIGN KEY sID NUMBER(10) REFERENCES Salesperson(sID)" +
                ")";

            stmt.executeUpdate(createCategory);
            stmt.executeUpdate(createManufacturer);
            stmt.executeUpdate(createPart);
            stmt.executeUpdate(createSalesperson);
            stmt.executeUpdate(createTransaction);
            
            System.out.print("Processing...Done! Database is initialized");
                
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Create Table Failed");
            System.exit(1);
        }  
        
    }
    
    void DeleteTable() {
        
        try {
            Statement stmt = conn.createStatement();

            String deleteCategory = "DROP TABLE Category";
            String deleteManufacturer = "DROP TABLE Manufacturer";
            String deletePart = "DROP TABLE Part";
            String deleteSalesperson = "DROP TABLE Salesperson";
            String deleteTransaction = "DROP TABLE Transaction";

            stmt.executeUpdate(deleteCategory);
            stmt.executeUpdate(deleteManufacturer);
            stmt.executeUpdate(deletePart);
            stmt.executeUpdate(deleteSalesperson);
            stmt.executeUpdate(deleteTransaction);

            System.out.print("Processing...Done! Database is removed!");

        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Delete Table Failed");
            System.exit(1);
        }
        
    }
    
    void LoadData() {
        
    }
    
    void ShowTable(String tableName) {
        
        try {
            Statement stmt = conn.createStatement();
            if(tableName.equalsIgnoreCase("category")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Category");
                System.out.println("Content of table category:\n| cID | cName |");
                while(rs.next()) {
                    String cID = rs.getString(1);
                    String cName = rs.getString(2);
                    System.out.println("| " + cID + " | " + cName + " |");
                }
            } else if(tableName.equalsIgnoreCase("maufacturer")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Manufacturer");
                System.out.println("Content of table manufacturer:\n| mID | mName | mAddress | mPhoneNumber |");
                while(rs.next()) {
                    String mID = rs.getString(1);
                    String mName = rs.getString(2);
                    String mAddress = rs.getString(3);
                    String mPhoneNumber = rs.getString(4);
                    System.out.println("| " + mID + " | " + mName + " | " + mAddress + " | " + mPhoneNumber + " |");
                }
            } else if(tableName.equalsIgnoreCase("part")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Part");
                System.out.println("Content of table part:\n| p_id | p_Name | p_price | m_id | p_quantity | p_warranty |");
                while(rs.next()) {
                    String pID = rs.getString(1);
                    String pName = rs.getString(2);
                    String pPrice = rs.getString(3);
                    String mID = rs.getString(4);
                    String cID = rs.getString(5);
                    String pWarrantyPeriod = rs.getString(6);
                    String pAvailableQuantity = rs.getString(7);
                    System.out.println("| " + pID + " | " + pName + " | " + pPrice + " | " + mID + " | " + cID + " | " + pWarrantyPeriod + " |");
                }
            } else if(tableName.equalsIgnoreCase("salesperson")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Salesperson");
                System.out.println("Content of table salesperson:\n| sID | sName | sAddress | sPhoneNumber | sExperience |");
                while(rs.next()) {
                    String sID = rs.getString(1);
                    String sName = rs.getString(2);
                    String sAddress = rs.getString(3);
                    String sPhoneNumber = rs.getString(4);
                    String sExperience = rs.getString(5);
                    System.out.println("| " + sID + " | " + sName + " | " + sAddress + " | " + sPhoneNumber + " | " + sExperience + " |");
                }
            } else if(tableName.equalsIgnoreCase("transaction")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Transaction");
                System.out.println("Content of table transaction:\n| tID | tDate | pID | sID |");
                while(rs.next()) {
                    String tID = rs.getString(1);
                    String tDate = rs.getString(2);
                    String pID = rs.getString(3);
                    String sID = rs.getString(4);
                    System.out.println("| " + tID + " | " + tDate + " | " + pID + " | " + sID + " |");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Show Table Failed");
            System.exit(1);
        }
    }
    
}
