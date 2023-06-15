import java.sql.*;
public class ToTheMoonDB {
    public static void main(String args[]) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/ToTheMoon";
        String userName = "root";
        String password = "mirim";

        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from ranking");

        while(resultSet.next()){
            String score = resultSet.getString("score");
            String playerID = resultSet.getString("playerID");
            System.out.println(score+" "+playerID);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}
