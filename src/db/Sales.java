package db;

import java.sql.*;
import java.util.Date;
import java.text.*;

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
                int pID = rs.getInt(1);
                String pName = rs.getString(2);
                String mName = rs.getString(3);
                String cName = rs.getString(4);
                int pAvailableQuantity = rs.getInt(5);
                String pWarrantyPeriod = rs.getString(6);
                int pPrice = rs.getInt(7);
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
            // Check if out of stock
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pName, pAvailableQuantity FROM Part WHERE pID=" + pID);
            int quant = rs.getInt("pAvailableQuantity");
            if(quant < 1) {
                System.out.println("[ERROR] Out of Stock!");
                return;
            }
            
            // Get the transaction ID
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Transaction");
            int tID = rs.getInt(1) + 1;
            
            // Insert a record to Transaction table
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String dnow = df.format(date);
            stmt.executeUpdate("INSERT INTO Transaction(tID, tDate, pID, sID) "
                             + "VALUES(" + tID + dnow + pID + sID + ")");
            
            // Update the quantity in Part table
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
