import java.sql.*;

public class ToTheMoonDB {
    public static void loadRanking() {
        String url = "jdbc:mysql://localhost:3306/ToTheMoon";
        String userName = "root";
        String password = "mirim";

        String rankingText = "";

        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ranking ORDER BY score DESC");

            int rank = 1;
            while(resultSet.next()){
                String score = resultSet.getString("score");
                String playerID = resultSet.getString("playerID");
                rankingText += rank + "\t" + playerID + "\t" + score + "\n";
                rank++;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Ranking ranking = new Ranking();
        ranking.setRankingText(rankingText);
        ranking.setVisible(true);
    }

    public static void insertRanking(String name, String score) {
        String url = "jdbc:mysql://localhost:3306/ToTheMoon";
        String userName = "root";
        String password = "mirim";

        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            String query = "INSERT INTO ranking (playerID, score) VALUES ('" + name + "', '" + score + "')";
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
