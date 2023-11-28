package db;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

// This class contains the four operations for manager
public class Manager {
    
    private Connection conn; // for database connection
    
    public Manager(Connection con) {
        conn = con;
    }
    
    void ListSalespersons(int order) { // value of order: 1 for ascendent, 2 for decendent
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Salesperson OEDER BY sExperience " + (order==1?"ASC":"DESC"));
            System.out.println("| sID | sName | sAddress | sPhoneNumber | sExperience |");
            while(rs.next()) {
                String sID = rs.getString(1);
                String sName = rs.getString(2);
                String sAddress = rs.getString(3);
                String sPhoneNumber = rs.getString(4);
                String sExperience = rs.getString(5);
                System.out.println("| " + sID + " | " + sName + " | " + sAddress + " | " + sPhoneNumber + " | " + sExperience + " |");
            }
            
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("List Salespersons Failed");
            System.exit(1);
        }
    }
    
    void CountSalesperson(int lb, int ub) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT S.sID, S.sName, S.sExperience, COUNT(*) AS noOfTransaction "
                                           + "FROM Salesperson S, Transaction T "
                                           + "WHERE S.sID=T.sID "
                                           + "GROUP BY S.sID "
                                           + "ORDER BY S.sID DESC");
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Count Salesperson");
        }
    }
    
    void SortListSalesValue() {
        
    }
    
    void ShowNPopular(int N) {
        
    }
    
}
