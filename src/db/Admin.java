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
                "cID INTEGER(1) PRIMARY KEY UNIQUE," + 
                "cName VARCHAR(20) NOT NULL UNIQUE" +
                ")";

            String createManufacturer = 
                "CREATE TABLE Manufacturer(" + 
                "mID INTEGER(2) PRIMARY KEY UNIQUE," + 
                "mName VARCHAR(20) NOT NULL," +
                "mAddress CHAR(50) NOT NULL," + 
                "mPhoneNumber INTEGER(8) NOT NULL" + 
                ")";

            String createPart = 
                "CREATE TABLE Part(" + 
                "pID INTEGER(3) PRIMARY KEY UNIQUE," + 
                "pName VARCHAR(20) NOT NULL," + 
                "pPrice INTEGER(5) NOT NULL," + 
                "mID INTEGER," +
                "cID INTEGER," + 
                "pWarrantyPeriod INTEGER(2) NOT NULL," + 
                "pAvailableQuantity INTEGER(2) NOT NULL," +
                "FOREIGN KEY (mID) REFERENCES Manufacturer(mID)," +
                "FOREIGN KEY (cID) REFERENCES Category(cID)" +
                ")";

            String createSalesperson = 
                "CREATE TABLE Salesperson(" + 
                "sID INTEGER(2) PRIMARY KEY UNIQUE," + 
                "sName VARCHAR(20) NOT NULL," +
                "sAddress VARCHAR(50) NOT NULL," + 
                "sPhoneNumber INTEGER(8) NOT NULL," + 
                "sExperience INTEGER(1) NOT NULL" +
                ")";

            String createTransaction = 
                "CREATE TABLE Transaction(" + 
                "tID INTEGER(4) PRIMARY KEY UNIQUE," +
                "tDate DATE," +
                "pID INTEGER," +
                "sID INTEGER," +
                "FOREIGN KEY (pID) REFERENCES Part(pID)," +
                "FOREIGN KEY (sID) REFERENCES Salesperson(sID)" +
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

            stmt.executeUpdate(deleteTransaction);
            stmt.executeUpdate(deletePart);
            stmt.executeUpdate(deleteCategory);
            stmt.executeUpdate(deleteManufacturer);
            stmt.executeUpdate(deleteSalesperson);


            System.out.print("Processing...Done! Database is removed!");

        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Delete Table Failed");
            System.exit(1);
        }
        
    }
    
    void LoadData() {
        try{
            Statement stmt = conn.createStatement();
            String thisLine;
            Scanner input = new Scanner(System.in);
            System.out.println("Type in the Source Data Folder Path: ");
            String folderName = input.nextLine();

            BufferedReader br = null;
            br = new BufferedReader(new FileReader(folderName+"/category.txt"));
            while((thisLine = br.readLine()) != null){
                String[] splited = null;
                splited = thisLine.split("\t",10);
                String a = splited[0];
                String b = splited[1];
                stmt.executeUpdate("INSERT INTO Category VALUES(" + a + ",'" + b + "')");

            }
            BufferedReader br1 = null;
            br1 = new BufferedReader(new FileReader(folderName+"/manufacturer.txt"));
            while((thisLine = br1.readLine()) != null){
                String[] splited = null;
                splited = thisLine.split("\t",10);
                String a = splited[0];
                String b = splited[1];
                String c = splited[2];
                String d = splited[3];
                stmt.executeUpdate("INSERT INTO Manufacturer VALUES(" + a + ",'" + b + "','" + c + "'," + d + ")");
            }
            BufferedReader br2 = null;
            br2 = new BufferedReader(new FileReader(folderName+"/part.txt"));
            while((thisLine = br2.readLine()) != null){
                String[] splited = null;
                splited = thisLine.split("\t",10);
                String a = splited[0];
                String b = splited[1];
                String c = splited[2];
                String d = splited[3];
                String e = splited[4];
                String f = splited[5];
                String g = splited[6];
                stmt.executeUpdate("INSERT INTO Part VALUES(" + a + ",'" + b + "'," + c + "," + d + "," + e + "," + f + "," + g + ")");
            }
            BufferedReader br3 = null;
            br3 = new BufferedReader(new FileReader(folderName+"/salesperson.txt"));
            while((thisLine = br3.readLine()) != null){
                String[] splited = null;
                splited = thisLine.split("\t",10);
                String a = splited[0];
                String b = splited[1];
                String c = splited[2];
                String d = splited[3];
                String e = splited[4];
                stmt.executeUpdate("INSERT INTO Salesperson VALUES(" + a + ",'" + b + "','" + c + "'," + d + "," + e + ")");
            }
            BufferedReader br4 = null;
            br4 = new BufferedReader(new FileReader(folderName+"/transaction.txt"));
            while((thisLine = br4.readLine()) != null){
                String[] splited = null;
                splited = thisLine.split("\t",10);
                String a = splited[0];
                String b = splited[1];
                String c = splited[2];
                String d = splited[3];

                String[] spd = null;
                spd = d.split("/", 4);
                String day = spd[0];
                String month = spd[1];
                String year = spd[2];
                String dt = "'" +  year + "-" + month + "-" + day + "'";

                stmt.executeUpdate("INSERT INTO Transaction VALUES(" + a + "," + dt + "," + b + "," + c + ")");
            }
            System.out.print("Processing...Done! Data is inputted to the database!");
        } 
        catch(Exception e) {
            System.err.println(e);
            System.err.println("Load data Failed");
            System.exit(1);
        }        

    }
    
    void ShowTable(String tableName) {
        
        try {
            Statement stmt = conn.createStatement();
            if(tableName.equalsIgnoreCase("category")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Category ORDER BY cID");
                System.out.println("Content of table category:\n| cID | cName |");
                while(rs.next()) {
                    String cID = rs.getString(1);
                    String cName = rs.getString(2);
                    System.out.println("| " + cID + " | " + cName + " |");
                }
            } else if(tableName.equalsIgnoreCase("manufacturer")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Manufacturer ORDER BY mID");
                System.out.println("Content of table manufacturer:\n| mID | mName | mAddress | mPhoneNumber |");
                while(rs.next()) {
                    String mID = rs.getString(1);
                    String mName = rs.getString(2);
                    String mAddress = rs.getString(3);
                    String mPhoneNumber = rs.getString(4);
                    System.out.println("| " + mID + " | " + mName + " | " + mAddress + " | " + mPhoneNumber + " |");
                }
            } else if(tableName.equalsIgnoreCase("part")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Part ORDER BY pID");
                System.out.println("Content of table part:\n| p_id | p_Name | p_price | m_id | p_quantity | p_warranty |");
                while(rs.next()) {
                    String pID = rs.getString(1);
                    String pName = rs.getString(2);
                    String pPrice = rs.getString(3);
                    String mID = rs.getString(4);
                    String cID = rs.getString(5);
                    String pWarrantyPeriod = rs.getString(6);
                    String pAvailableQuantity = rs.getString(7);
                    System.out.println("| " + pID + " | " + pName + " | " + pPrice + " | " + mID + " | " + cID + " | " + pAvailableQuantity + " | " + pWarrantyPeriod + " |");
                }
            } else if(tableName.equalsIgnoreCase("salesperson")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Salesperson ORDER BY sID");
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
                ResultSet rs = stmt.executeQuery("SELECT * FROM Transaction ORDER BY tID");
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
