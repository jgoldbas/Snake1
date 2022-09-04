package snakeframe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInit
 {
	 
 public static void main(String[] args) throws ClassNotFoundException
  {
   // load the sqlite-JDBC driver using the current class loader
   Class.forName("org.sqlite.JDBC");
   Connection connection = null;

   
   try
   {
      // create a database connection
	   connection = DriverManager.getConnection("jdbc:sqlite:snakegame.db");

      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.


      statement.executeUpdate("DROP TABLE IF EXISTS snakeScore");
      statement.executeUpdate("CREATE TABLE snakeScore (id INTEGER PRIMARY KEY, name STRING, score INTEGER)");

      System.out.println("Finish");
       }

  catch(SQLException e){  System.err.println(e.getMessage()); }       
   finally {         
         try {
               if(connection != null)
                  connection.close();
               }
         catch(SQLException e) {  // Use SQLException class instead.          
            System.err.println(e); 
          }
   }
}
}