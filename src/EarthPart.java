import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EarthPart extends JFrame implements KeyListener {
    private Image earthBackground;
    private ImageIcon[] rabbit = new ImageIcon[2];
    private ImageIcon rabbitBefore;
    private ImageIcon rabbitJumpBefore;
    private boolean isJumping = false; // 토끼가 점프 중인지 여부를 나타내는 플래그

    public EarthPart() {
        setUndecorated(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        // 이미지 지정
        earthBackground = new ImageIcon(getClass().getResource("img/EarthBackground.png")).getImage();
        rabbitBefore = new ImageIcon(getClass().getResource("img/rabbit.png"));
        rabbitJumpBefore = new ImageIcon(getClass().getResource("img/rabbitJump.png"));

        int rabbitWidth = rabbitBefore.getIconWidth();
        int rabbitHeight = rabbitBefore.getIconHeight();

        int scaledWidth = (int) (rabbitWidth * 0.5);
        int scaledHeight = (int) (rabbitHeight * 0.5);

        rabbit[0] = new ImageIcon(rabbitBefore.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        rabbit[1] = new ImageIcon(rabbitJumpBefore.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(earthBackground, 0, 0, getWidth(), getHeight(), null);
                g.drawImage(rabbit[isJumping ? 1 : 0].getImage(), 500, 500, null);  // x, y는 토끼 이미지의 위치입니다.
            }
        };

        contentPane.setLayout(null);
        setContentPane(contentPane);  // JPanel을 프레임의 컨텐트 팬으로 설정
        setVisible(true);
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isJumping = true;
            repaint(); // 화면을 다시 그려줌으로써 변경된 이미지를 표시
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isJumping = false;
            repaint(); // 화면을 다시 그려줌으로써 변경된 이미지를 표시
        }
    }

    public static void main(String[] args) {
        new EarthPart();
    }
}
