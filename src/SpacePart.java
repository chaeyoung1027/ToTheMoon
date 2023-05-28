import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpacePart extends JFrame implements KeyListener {
    private Image screenImage;
    private Graphics screenGraphic;

    // 이동 여부
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/SpaceBackground.png")).getImage();

    // 우주선
    private Image spaceship = new ImageIcon(ToTheMoon.class.getResource("img/Spaceship_fire.png")).getImage();
    private int spaceshipX = 810; // 초기 X 좌표
    private int spaceshipY = 750; // 초기 Y 좌표

    // 장애물(인공위성)
    private Image junk1 = new ImageIcon(ToTheMoon.class.getResource("img/SpaceJunk1.png")).getImage();
    private Image junk2 = new ImageIcon(ToTheMoon.class.getResource("img/SpaceJunk2.png")).getImage();

    private int junkX;
    private int junkY;

    // 현재 장애물(인공위성) 이미지
    private Image currentJunkImage;

    private Timer junkTimer;
    private int junkDelay;
    private int junkState = 1;

    // 장애물(운석)


    public SpacePart() {
        setUndecorated(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(true);
        setVisible(true);

        // KeyListener 등록
        addKeyListener(this);

        // 인공위성 설정
        currentJunkImage = junk1;
        junkX = (int) (Math.random() * (1920 - 490));   // 화면 내에서 랜덤 X 좌표를 가짐
        junkY = -352;   // 화면 밖에서 생성
        junkDelay = (int) (Math.random() * 2000) + 3000;    // 3초~5초 딜레이 주기

        junkTimer = new Timer(100, new JunkActionListener());  // 0.1초마다 JunkActionListener 호출
        junkTimer.start();  // 타이머 시작

        // 운석 설정

    }

    private class JunkActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            junkY += 50;
            if (junkY > 1080) {
                junkY = -352;
                junkX = (int) (Math.random() * (1920 - 490));
                junkDelay = (int) (Math.random() * 2000) + 3000;
                junkState = junkState == 1 ? 2 : 1;
                currentJunkImage = junkState == 1 ? junk1 : junk2;
            }
            repaint();
        }
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
        super.paintComponents(g);
        // 캐릭터 이동 로직
        if (moveUp && spaceshipY > 0) {
            spaceshipY -= 6;
        } else if (moveDown && spaceshipY < 1080 - 254) {
            spaceshipY += 6;
        }
        if (moveLeft && spaceshipX > 0) {
            spaceshipX -= 6;
        } else if (moveRight && spaceshipX < 1920 - 150) {
            spaceshipX += 6;
        }


        g.drawImage(background , 0, 0, null);
        g.drawImage(spaceship, spaceshipX, spaceshipY, null);
        g.drawImage(currentJunkImage, junkX, junkY, null);
        this.repaint();	// paint 함수로 돌아감
    }

    //키보드 이벤트
    @Override
    public void keyTyped(KeyEvent e) { }    // 사용 안 함

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            moveUp = true;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            moveDown = true;
        } if (keyCode == KeyEvent.VK_LEFT) {
            moveLeft = true;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            moveRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            moveUp = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            moveDown = false;
        } if (keyCode == KeyEvent.VK_LEFT) {
            moveLeft = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            moveRight = false;
        }
    }

    public static void main(String[] args) { new SpacePart(); }
}
