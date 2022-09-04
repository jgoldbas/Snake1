package snakeframe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection
 {
	private Connection  connect_db()
	{
		 String con_url = "jdbc:sqlite:snakegame.db";
		 Connection connection = null;
		 try {
			 connection = DriverManager.getConnection(con_url);
		 }
		 catch (SQLException e)
		 {
			 System.out.println(e.getMessage());
		 }
		 return connection;
	}
	
	
	public void insert_player(String name, int score)
	{
		String statement = "INSERT INTO snakescore(name, score) VALUES(?,?)";
		
		try(Connection conn = this.connect_db();
				PreparedStatement pre_sqlst = conn.prepareStatement(statement)) {
				pre_sqlst.setString(1, name);
				pre_sqlst.setInt(2, score);
				pre_sqlst.executeUpdate();
				System.out.println("Player score inserted successfully!");
				} 
		catch (SQLException e)
		 {
			System.out.println(e.getMessage());
		}
	}
	public Map<Integer, String> select_topscore()
	{
		Map<Integer, String> topScoreMap = new HashMap<>();
		
		String statement = "SELECT id, name, score FROM snakescore ORDER BY score desc LIMIT 10";
		int index=1;
		try(Connection conn = this.connect_db();
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(statement)){
			   System.out.println("Start");
				while(result.next()) {
					  String player_name = result.getString("name");
					  int player_score = result.getInt("score");
					  topScoreMap.put(index, player_name + " score : " + player_score);
					  index++;
					  //System.out.println(player_id + " : "+ player_score+ " : "+ player_name);
			}
				   System.out.println("Finish exec");
			return topScoreMap;	
				} 
		catch (SQLException e)
		 {
			System.out.println(e.getMessage());
			return Collections.<Integer, String>emptyMap();
		}
	}

	//the main method was used strictly for debugging 
 public static void main(String[] args) throws ClassNotFoundException
  {
   DatabaseConnection ourdb = new DatabaseConnection();
   //ourdb.insert_player("Player 2", 20);

   //ourdb.insert_player("Player 6", 300);
   Map<Integer, String> topScoreMap = ourdb.select_topscore();
   System.out.println(topScoreMap);
 
  }
}