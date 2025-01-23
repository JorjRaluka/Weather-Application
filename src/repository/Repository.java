package repository;

import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static final String JDBC_URL = "jdbc:sqlite:C:\\Users\\Raluka\\Desktop\\work\\mockrepo-practicalexam2025-JorjRaluka\\data\\test_db.db";

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null)
            openConnection();
        return conn;
    }

    private static void openConnection()
    {
        try
        {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection()
    {
        try
        {
            conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public List<weatherInterval>getAllWeatherIntervals(){
        List<weatherInterval>intervals=new ArrayList<>();
        Connection connection = getConnection();
        try(
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT * FROM weatherInterval ORDER BY startHour ASC")){
            while(resultSet.next()){
                intervals.add(new weatherInterval(
                        resultSet.getInt("startHour"),
                        resultSet.getInt("endHour"),
                        resultSet.getInt("temperature"),
                        resultSet.getInt("precipProb"),
                        resultSet.getString("description")
                ));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return intervals;
    }
    public void updateDescription(int startH,int endH,String newDescription){
        try(Connection connection=getConnection();
            PreparedStatement statement=connection.prepareStatement("UPDATE weatherInterval SET description = ? WHERE startH = ? AND endH = ?")){
            statement.setInt(1,startH);
            statement.setInt(2,endH);
            statement.setString(3,newDescription);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
