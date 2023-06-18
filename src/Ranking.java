import javax.swing.*;
import java.awt.*;

public class Ranking extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    private Image background = new ImageIcon(getClass().getResource("img/RankingBackground.png")).getImage();
    private String rankingText = ""; // 랭킹 정보를 저장할 변수

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

        // 랭킹 정보를 그림
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 46));
        g.drawString(rankingText, 675, 320); // 위치 및 스타일을 조정하여 적절히 표시

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
}
