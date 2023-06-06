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
    private int yPos1; // 첫 번째 배경 이미지의 y 좌표
    private int yPos2; // 두 번째 배경 이미지의 y 좌표

    // 우주선
    private Image spaceship1 = new ImageIcon(ToTheMoon.class.getResource("img/Spaceship_fire.png")).getImage();
    private Image spaceship2 = new ImageIcon(ToTheMoon.class.getResource("img/Spaceship.png")).getImage();

    private ImageIcon ship1 = new ImageIcon(spaceship1);
    private ImageIcon ship2 = new ImageIcon(spaceship2);

    private int spaceshipX = 810; // 초기 X 좌표
    private int spaceshipY = 750; // 초기 Y 좌표

    private JLabel imageLabel;
    private ImageIcon[] images;
    private int currentIndex = 0;

    // 장애물(인공위성)
    private Image junk1 = new ImageIcon(ToTheMoon.class.getResource("img/SpaceJunk1.png")).getImage();
    private Image junk2 = new ImageIcon(ToTheMoon.class.getResource("img/SpaceJunk2.png")).getImage();

    private int junkWidth;
    private int junkHeight;

    private int junkX;
    private int junkY;

    // 현재 장애물(인공위성) 이미지
    private Image currentJunkImage;

    private Timer junkTimer;
    private int junkDelay;
    private int junkState = 1;

    // 장애물(운석)
    private Image meteor1 = new ImageIcon(ToTheMoon.class.getResource("img/Meteor1.png")).getImage();
    private Image meteor2 = new ImageIcon(ToTheMoon.class.getResource("img/Meteor2.png")).getImage();

    private int meteorWidth;
    private int meteorHeight;

    private int meteorX;
    private int meteorY;

    private Image currentMeteorImage;

    private Timer meteorTimer;
    private int meteorDelay;
    private int meteorState = 1;


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

        // --- 배경 이미지 흐르게 ---
        // 배경 이미지 설정
        yPos1 = 0;
        yPos2 = -background.getHeight(null); // 두 번째 배경 이미지는 첫 번째 배경 이미지의 위쪽에 위치

        // --- 장애물 생성 ---
        // 인공위성 설정
        currentJunkImage = junk1;
        junkX = (int) (Math.random() * (1920 - 490));   // 화면 내에서 랜덤 X 좌표를 가짐
        junkY = -352;   // 화면 밖에서 생성
        junkDelay = (int) (Math.random() * 5000) + 5000;    // 3초~5초 딜레이 주기

        // 이미지 크기 랜덤 설정
        junkWidth = (int) (Math.random() * 301) + 190;  // 300부터 490 사이의 랜덤 값
        double aspectRatio1 = (double) junk1.getHeight(null) / junk1.getWidth(null);
        junkHeight = (int) (junkWidth * aspectRatio1);
        junk1 = junk1.getScaledInstance(junkWidth, junkHeight, Image.SCALE_SMOOTH);

        junkWidth = (int) (Math.random() * 301) + 190;  // 300부터 490 사이의 랜덤 값
        double aspectRatio2 = (double) junk2.getHeight(null) / junk2.getWidth(null);
        junkHeight = (int) (junkWidth * aspectRatio2);
        junk2 = junk2.getScaledInstance(junkWidth, junkHeight, Image.SCALE_SMOOTH);


        junkTimer = new Timer(100, new JunkActionListener());  // 0.1초마다 JunkActionListener 호출
        junkTimer.start();  // 타이머 시작


        // 운석 설정
        currentMeteorImage = meteor1;
        meteorX = (int) (Math.random() * (1920 - 400));   // 화면 내에서 랜덤 X 좌표를 가짐
        meteorY = -401;   // 화면 밖에서 생성
        meteorDelay = (int) (Math.random() * 3000) + 2000;    // 3초~5초 딜레이 주기

        // 이미지 크기 랜덤 설정
        meteorWidth = (int) (Math.random() * 101) + 100;  // 100부터 400 사이의 랜덤 값
        double aspectRatio3 = (double) meteor1.getHeight(null) / meteor1.getWidth(null);
        meteorHeight = (int) (meteorWidth * aspectRatio3);
        meteor1 = meteor1.getScaledInstance(meteorWidth, meteorHeight, Image.SCALE_SMOOTH);

        meteorWidth = (int) (Math.random() * 101) + 100;  // 100부터 400 사이의 랜덤 값
        double aspectRatio4 = (double) meteor2.getHeight(null) / meteor2.getWidth(null);
        meteorHeight = (int) (meteorWidth * aspectRatio4);
        meteor2 = meteor2.getScaledInstance(meteorWidth, meteorHeight, Image.SCALE_SMOOTH);

        meteorTimer = new Timer(100, new JunkActionListener());  // 0.1초마다 JunkActionListener 호출
        meteorTimer.start();  // 타이머 시작

    }

    private class JunkActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            junkY += 50;
            if (junkY > 1080) {
                junkY = -352;
                junkX = (int) (Math.random() * (1920 - 490));
                junkDelay = (int) (Math.random() * 5000) + 5000;
                junkState = junkState == 1 ? 2 : 1;
                currentJunkImage = junkState == 1 ? junk1 : junk2;
            }

            meteorY += 60;
            if (meteorY > 1080) {
                meteorY = -401;
                meteorX = (int) (Math.random() * (1920 - 400));
                meteorDelay = (int) (Math.random() * 3000) + 2000;
                meteorState = meteorState == 1 ? 2 : 1;
                currentMeteorImage = meteorState == 1 ? meteor1 : meteor2;
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

        // 배경 이미지 스크롤
        yPos1+=5; // 첫 번째 배경 이미지의 y 좌표를 1씩 증가시켜 아래로 이동
        yPos2+=5; // 두 번째 배경 이미지의 y 좌표를 1씩 증가시켜 아래로 이동
        if (yPos1 >= background.getHeight(null)) {
            yPos1 = -background.getHeight(null); // 첫 번째 배경 이미지가 화면 아래로 벗어나면 다시 위로 이동
        }
        if (yPos2 >= background.getHeight(null)) {
            yPos2 = -background.getHeight(null); // 두 번째 배경 이미지가 화면 아래로 벗어나면 다시 위로 이동
        }

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


        g.drawImage(background, 0, yPos1, null); // 첫 번째 배경 이미지 그리기 위치에 yPos1 변수를 사용하여 스크롤 효과 적용
        g.drawImage(background, 0, yPos2, null); // 두 번째 배경 이미지 그리기 위치에 yPos2 변수를 사용하여 스크롤 효과 적용

        g.drawImage(spaceship1, spaceshipX, spaceshipY, null);
        g.drawImage(currentJunkImage, junkX, junkY, junkWidth, junkHeight, null);
        g.drawImage(currentMeteorImage, meteorX, meteorY, null);
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
