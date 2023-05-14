import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Rule extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/SpaceGameRule1.png")).getImage().getScaledInstance(1920, 1080, 0);;

    // 다음 버튼
    private final Image NextButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Arrow.png")).getImage();
    private Image NextButton1 = NextButtonImage.getScaledInstance(167, 125, 0);
    private ImageIcon NextButton = new ImageIcon(NextButton1);

    private JButton NextJButton = new JButton(NextButton);

    public Rule() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        // 다음 버튼 생성
        NextJButton.setBounds(1691, 925, 167, 125);
        NextJButton.setBorderPainted(false);
        NextJButton.setContentAreaFilled(false);
        NextJButton.setFocusPainted(false);
        NextJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
//                new Rule2();
            }
        });
        add(NextJButton);
    }

    // 그리는 함수
    public void paint(Graphics g) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
        screenImage = createImage(1920, 1080);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);	// background를 그려줌
    }

    public void screenDraw(Graphics g) {
        g.drawImage(background , 0, 0, null);
        paintComponents(g);
        this.repaint();	// paint 함수로 돌아감
    }

    public static void main(String[] args) {
        new Rule();
    }
}