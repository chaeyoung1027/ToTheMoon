import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EarthPart extends JFrame {
    private Image earthBackground;
    private Image[] rightRabbit = new Image[3];
    private Image[] leftRabbit = new Image[3];
    private Image rabbitBetween;
    private Image leftRabbitBetween;

    private boolean isJumping = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private int rabbitX = 500;
    private int rabbitY = 630;
    private int rabbitMoveSpeed = 5;

    private int scaledWidth;
    private int scaledHeight;

    private int backgroundX = 0; // 배경의 위치

    private int frameCount = 0;
    private int frameDelay = 5; // 토끼 이미지 변경 속도

    private int backgroundSpeed = 4; // 배경 이동 속도 : 숫자가 높을수록 빠름

    public EarthPart() {
        setUndecorated(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(true);
        setVisible(true);

        earthBackground = new ImageIcon(getClass().getResource("img/EarthBackground.png")).getImage();

        Image rightRabbitRun1 = new ImageIcon(getClass().getResource("img/rabbit_run1.png")).getImage();
        rabbitBetween = new ImageIcon(getClass().getResource("img/rabbit_between.png")).getImage();
        Image rightRabbitRun2 = new ImageIcon(getClass().getResource("img/rabbit_run2.png")).getImage();
        Image rightRabbitJump = new ImageIcon(getClass().getResource("img/rabbit_jump.png")).getImage();

        Image leftRabbitRun1 = new ImageIcon(getClass().getResource("img/left_rabbit_run1.png")).getImage();
        leftRabbitBetween = new ImageIcon(getClass().getResource("img/left_rabbit_between.png")).getImage();
        Image leftRabbitRun2 = new ImageIcon(getClass().getResource("img/left_rabbit_run2.png")).getImage();
        Image leftRabbitJump = new ImageIcon(getClass().getResource("img/left_rabbit_jump.png")).getImage();

        int rabbitWidth = rightRabbitRun1.getWidth(null);
        int rabbitHeight = rightRabbitRun1.getHeight(null);

        scaledWidth = (int) (rabbitWidth * 0.5);
        scaledHeight = (int) (rabbitHeight * 0.5);

        rightRabbit[0] = rightRabbitRun1.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        rightRabbit[1] = rabbitBetween.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        rightRabbit[2] = rightRabbitRun2.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        leftRabbit[0] = leftRabbitRun1.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        leftRabbit[1] = leftRabbitBetween.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        leftRabbit[2] = leftRabbitRun2.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        addKeyListener(new MyKeyListener());
        setContentPane(new MyPanel());
    }

    private class MyPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int bgWidth = earthBackground.getWidth(null);
            int bgHeight = earthBackground.getHeight(null);
            int panelWidth = getWidth();

            backgroundX -= backgroundSpeed; // 배경의 위치 업데이트

            if (backgroundX < -bgWidth) {
                backgroundX = 0; // 배경이 화면 밖으로 나가면 위치를 초기화합니다.
            }

            g.drawImage(earthBackground, backgroundX, 0, null);
            if (backgroundX < panelWidth - bgWidth) {
                g.drawImage(earthBackground, backgroundX + bgWidth, 0, null); // 다른 배경 이미지를 그립니다.
            }

            g.drawImage(getCurrentRabbitImage(), rabbitX, rabbitY, null);
        }
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // 키 입력 시 처리할 내용을 여기에 작성
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // 키 눌림 이벤트 처리
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                isMovingRight = true;
                isMovingLeft = false; // 오른쪽으로 이동할 때 왼쪽 이동 상태를 해제
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                isMovingLeft = true; //왼쪽으로 이동할 때 왼쪽 이동 상태를 설정합니다.
                isMovingRight = false; // 왼쪽으로 이동할 때 오른쪽 이동 상태를 해제
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 키 놓임 이벤트 처리
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                isMovingRight = false;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                isMovingLeft = false;
            }
        }
    }

    public void update() {
        boolean isMoving = isMovingRight || isMovingLeft; // 토끼가 이동 중인지 확인

        if (isMoving) {
            if (isMovingRight) {
                rabbitX += rabbitMoveSpeed;
            } else if (isMovingLeft) {
                rabbitX -= rabbitMoveSpeed; // 왼쪽으로 이동할 때 토끼의 위치를 왼쪽으로 변경합니다.
            }
        } else {
            rabbitX -= backgroundSpeed;
        }

        //토끼가 화면을 벗어나는지 확인하고 게임 오버 처리
        int rabbitLeftEdge = rabbitX - 10;
        int rabbitRightEdge = rabbitX + scaledWidth;
        if (rabbitLeftEdge + 200 < 0 || rabbitRightEdge > getWidth()) {
            System.out.println("게임 오버");
        }

        frameCount++;
        if (frameCount % frameDelay == 0 && isMoving) { // 오른쪽 또는 왼쪽으로 이동 중에만 이미지 변경
            frameCount = 0;
            toggleRabbitImage();
        }

        repaint();
    }

    private void toggleRabbitImage() {
        if (isMovingRight) {
            Image temp = rightRabbit[0];
            rightRabbit[0] = rightRabbit[1];
            rightRabbit[1] = rightRabbit[2];
            rightRabbit[2] = temp;
        } else if (isMovingLeft) {
            Image temp = leftRabbit[0];
            leftRabbit[0] = leftRabbit[1];
            leftRabbit[1] = leftRabbit[2];
            leftRabbit[2] = temp;
        }
    }

    private Image getCurrentRabbitImage() {
        if (isMovingLeft) {
            return leftRabbit[1];
        } else if (isMovingRight) {
            return rightRabbit[1];
        } else {
            return rightRabbit[1];
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EarthPart earthPart = new EarthPart();
            earthPart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            earthPart.setVisible(true);

            //주기적으로 업데이트 메소드 호출
            Timer timer = new Timer(16, e -> earthPart.update());
            timer.start();
        });
    }
}
