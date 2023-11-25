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
        
    }
    
    void Transaction(int pID, int sID) {
        
    }
    
}
