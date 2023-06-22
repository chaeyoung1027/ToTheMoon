import javax.swing.*;
import java.awt.*;

public class Ranking extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    private Image background = new ImageIcon(getClass().getResource("img/RankingBackground.png")).getImage();
    private String rankingText = ""; // 랭킹 정보를 저장할 변수

    public static int score;

    public Ranking(int score) {
        this.score = score;
    }

    public Ranking() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        setLayout(null);
    }

    // 그리는 함수
    public void paint(Graphics g) {
        screenImage = createImage(1920, 1080);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);    // 배경을 그려줌
    }

    public void screenDraw(Graphics g) {
        g.drawImage(background , 0, 0, null);
        int[] x = new int[]{675, 830, 1150};
        int i = 0;

        // 랭킹 정보를 그림
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 46));
        String[] rankingLines = rankingText.split("\n");
        int y = 320; // 시작 y 좌표
        for (String line : rankingLines) {
            String[] a = line.split("\t");
            if (a.length >= 3) {
                for (String print : a) {
                    if (i < x.length) { // Check if the index is within bounds of the x array
                        g.drawString(print, x[i], y); // 위치 및 스타일을 조정하여 적절히 표시
                        i++; // x 좌표 간격을 200씩 증가
                    } else {
                        break; // Stop drawing if there are no more elements in the x array
                    }
                }
                y += 80; // y 좌표 간격을 80씩 증가
                i = 0; // Reset the x array index for the next line
            }
        }

        paintComponents(g);
        this.repaint();    // paint 함수로 돌아감
    }

    public static void main(String[] args) {
        ToTheMoonDB.loadRanking(); // DB 내용을 가져옴
    }

    // DB 내용을 랭킹 텍스트로 설정하는 메서드
    public void setRankingText(String text) {
        rankingText = text != null ? text : ""; // text가 null인 경우 빈 문자열로 설정
    }

    // Getter for rankingText
    public String getRankingText() {
        return rankingText;
    }
}
