# CSCI3170_Project

## Group 52
Members:  
1155176381 Wong Tsz Shan  
1155147304 Hans Junoes  
1155176920 Chow Man Fung  

## List of Files
/src contains the source code  
There are five .java files in the db package.  
Main.java is the main class of the programme. It connects to the database and displays the main menu.  
Menu.java contains three methods for displaying the menus for different users (admin, sales and manager).  
Admin.java, Sales.java and Manager.java contains the operations for respective users.  

## How to Compile and Run
1. Move to the /src directory
2. Run `javac db/*.java`
3. Run `java -cp ./mysql-jdbc.jar:./ db.Main`
