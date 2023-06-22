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

    public static void insertOrUpdateScore(String name, int score) {
        String url = "jdbc:mysql://localhost:3306/tothemoon";
        String userName = "root";
        String password = "mirim";

        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();

            // 이름이 이미 존재하는지 확인
            String selectQuery = "SELECT * FROM ranking WHERE playerID = '" + name + "'";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            if (resultSet.next()) {
                // 이미 존재하는 이름인 경우 update
                String updateQuery = "UPDATE ranking SET score = " + score + " WHERE playerID = '" + name + "'";
                statement.executeUpdate(updateQuery);
            } else {
                // 존재하지 않는 이름인 경우 insert
                String insertQuery = "INSERT INTO ranking (playerID, score) VALUES ('" + name + "', " + score + ")";
                statement.executeUpdate(insertQuery);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}