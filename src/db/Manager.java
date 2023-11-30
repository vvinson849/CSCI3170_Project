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
            System.out.println("| sID | sName | Years of Experience | Number of Transaction |");
            while(rs.next()) {
                String sID = rs.getString(1);
                String sName = rs.getString(2);
                String sExperience = rs.getString(3);
                String noT = rs.getString(4);
                System.out.println("| " + sID + " | " + sName + " | " + sExperience + " | " + noT + " | ");
            }
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Count Salesperson Failed");
        }
    }
    
    void SortListSalesValue() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT M.mID, M.mName, SUM(pPrice) AS SRevenue "
                                           + "FROM Manufacturer M "
                                           + "JOIN Part ON Part.mID = M.mID"
                                           + "JOIN Transaction ON Transaction.pID = Part.pID "
                                           + "GROUP BY M.mID, M.mName "
                                           + "ORDER BY SUM(pPrice) DESC");
            System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
            while(rs.next()) {
                String mID = rs.getString(1);
                String mName = rs.getString(2);
                String sValue = rs.getString(3);
                System.out.println("| " + mID + " | " + mName + " | " + sValue + " | ");
            }
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Sort and list manufacturer failed");
        } 
    }
    
    void ShowNPopular(int N) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT P.pID, P.pName, COUNT(tID) "
                                           + "FROM Part P "
                                           + "JOIN Transaction ON Transaction.pID = P.pID"
                                           + "GROUP BY P.pID, P.pName "
                                           + "ORDER BY COUNT(tID) DESC"
                                           + "LIMIT " + N);
            System.out.println("| Part ID | Part Name | No. of Transaction |");
            while(rs.next()) {
                String pID = rs.getString(1);
                String pName = rs.getString(2);
                String noT = rs.getString(3);
                System.out.println("| " + pID + " | " + pName + " | " + noT + " | ");
            }
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Show popular parts failed");
        }
    }
    
}
