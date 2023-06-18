import java.sql.*;

public class ToTheMoonDB {
    public static void loadRanking() {
        String url = "jdbc:mysql://localhost:3306/ToTheMoon";
        String userName = "root";
        String password = "mirim";

        String rankingText = ""; // 랭킹 정보를 저장할 변수

        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ranking");

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

        // Ranking 객체 생성 및 랭킹 정보 설정
        Ranking ranking = new Ranking();
        ranking.setRankingText(rankingText);
        ranking.setVisible(true);
    }
}
