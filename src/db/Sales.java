package db;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

// This class contains the two operations for salesperson
public class Sales {
    
    private Connection conn; // for database connection
    
    public Sales(Connection con) {
        conn = con;
    }
    
    void SearchParts(int crit, String keyword, int order) {
        try {
            Statement stmt = conn.createStatement();   
            ResultSet rs = stmt.executeQuery("SELECT P.pID, P.pName, M.mName, C.cName, P.pAvailableQuantity, P.pWarrantyPeriod, P.pPrice "
                                            + "FROM Part P, Manufacturer M, Category C "
                                            + "WHERE P.mID=M.mID AND P.cID=C.cID AND "
                                            + (crit==1?"P.pName=":"M.mName=")
                                            + keyword + " ORDER BY P.pPrice "
                                            + (order==1?"ASC":"DESC"));
            System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
            while(rs.next()) {
            String pID = rs.getString(1);
            String pName = rs.getString(2);
            String mName = rs.getString(3);
            String cName = rs.getString(4);
            String pAvailableQuantity = rs.getString(5);
            String pWarrantyPeriod = rs.getString(6);
            String pPrice = rs.getString(7);
            System.out.println("| " + pID + " | " + pName + " | " + mName + " | " + cName + " | " + pAvailableQuantity + " | " + pWarrantyPeriod + " | " + pPrice + " |");
            }           
            
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Search Parts Failed");
            System.exit(1);
        }
        
    }
    
    void Transaction(int pID, int sID) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pName, pAvailableQuantity FROM Part WHERE pID=" + pID);
            int quant = Integer.parseInt(rs.getString(2));
            if(quant < 1) {
                System.out.println("[ERROR] Out of Stock!");
                return;
            }
            stmt.executeUpdate("Update Part SET pAvailableQuantity=" + (quant-1) + "WHERE pID=" + pID);
            System.out.println("Product: " + rs.getString("pName")
                                + "(id: " + pID + ") "
                                + "Remaining Quantity: " + (quant-1));
            
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Transaction Failed");
            System.exit(1);
        }
    }
    
}
