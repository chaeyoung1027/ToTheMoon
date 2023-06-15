import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class EarthPart extends JFrame {
    //이미지
    private Image earthBackground;
    private Image[] rightRabbit = new Image[3];
    private Image[] leftRabbit = new Image[3];
    private Image rightRabbitJump;
    private Image leftRabbitJump;
    private Image rightRabbitSliding;
    private Image leftRabbitSliding;
    private Image heart;
    private Image emptyHeart;

    //장애물
    private Image[] Obstacle = new Image[5];

    //이동 확인
    private boolean isJumping = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isSliding = false;
    private int rabbitX = 500;
    private int rabbitY = 630;

    //배경의 위치
    private int backgroundX = 0;

    private int scaledWidth;

    // 토끼 이미지 변경 속도
    private int frameCount = 0;
    private int frameDelay = 5;

    // 배경 속도, 토끼 이동 속도 : 숫자가 높을수록 빠름
    private int rabbitMoveSpeed = 1;
    private int backgroundSpeed = 8;

    //장애물을 위한 랜덤
    private int randnum; //랜덤숫자
    private Random random;

    //장애물 좌표를 위한 변수
    private int i=0;

    //장애물의 y좌표
    private int y;

    //플레이어 목숨
    private int playerHeart = 5;

    //장애물 위치 저장 list
    private ArrayList<int[]> numbers = new ArrayList<>();

    public EarthPart() {
        setUndecorated(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(true);
        setVisible(true);

        //이미지 설정
        earthBackground = new ImageIcon(getClass().getResource("img/EarthBackground.png")).getImage();

        Image rightRabbitRun1 = new ImageIcon(getClass().getResource("img/rabbit_run1.png")).getImage();
        Image rabbitBetween = new ImageIcon(getClass().getResource("img/rabbit_between.png")).getImage();
        Image rightRabbitRun2 = new ImageIcon(getClass().getResource("img/rabbit_run2.png")).getImage();
        rightRabbitJump = new ImageIcon(getClass().getResource("img/rabbit_jump.png")).getImage();
        rightRabbitSliding = new ImageIcon(getClass().getResource("img/rabbit_sliding.png")).getImage();

        Image leftRabbitRun1 = new ImageIcon(getClass().getResource("img/left_rabbit_run1.png")).getImage();
        Image leftRabbitBetween = new ImageIcon(getClass().getResource("img/left_rabbit_between.png")).getImage();
        Image leftRabbitRun2 = new ImageIcon(getClass().getResource("img/left_rabbit_run2.png")).getImage();
        leftRabbitJump = new ImageIcon(getClass().getResource("img/left_rabbit_jump.png")).getImage();
        leftRabbitSliding = new ImageIcon(getClass().getResource("img/left_rabbit_sliding.png")).getImage();

        Obstacle[0] = new ImageIcon(getClass().getResource("img/Cloud.png")).getImage();
        Obstacle[1] = new ImageIcon(getClass().getResource("img/Cloud2.png")).getImage();
        Obstacle[2] = new ImageIcon(getClass().getResource("img/hurdle.png")).getImage();
        Obstacle[3] = new ImageIcon(getClass().getResource("img/plant.png")).getImage();
        Obstacle[4] = new ImageIcon(getClass().getResource("img/plant2.png")).getImage();

        heart = new ImageIcon(getClass().getResource("img/heart.png")).getImage();
        emptyHeart = new ImageIcon(getClass().getResource("img/empty_heart.png")).getImage();

        //이미지 크기 조절을 위한 변수
        int rabbitWidth = rightRabbitRun1.getWidth(null);
        int rabbitHeight = rightRabbitRun1.getHeight(null);

        int scaledWidth = (int) (rabbitWidth * 0.5);
        int scaledHeight = (int) (rabbitHeight * 0.5);

        int jumpWidth = leftRabbitJump.getWidth(null);
        int jumpHeight = leftRabbitJump.getHeight(null);

        int jumpScaledWidth = (int) (jumpWidth * 0.5);
        int jumpScaledHeight = (int) (jumpHeight * 0.5);

        int slideWidth = leftRabbitSliding.getWidth(null);
        int slideHeight = leftRabbitSliding.getHeight(null);

        int slideScaledWidth = (int) (slideWidth * 0.5);
        int slideScaledHeight = (int) (slideHeight * 0.5);

        //이미지 배열
        rightRabbit[0] = rightRabbitRun1.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        rightRabbit[1] = rabbitBetween.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        rightRabbit[2] = rightRabbitRun2.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        leftRabbit[0] = leftRabbitRun1.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        leftRabbit[1] = leftRabbitBetween.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        leftRabbit[2] = leftRabbitRun2.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        //이미지 크기 조절
        rightRabbitJump = rightRabbitJump.getScaledInstance(jumpScaledWidth, jumpScaledHeight, Image.SCALE_SMOOTH);
        leftRabbitJump = leftRabbitJump.getScaledInstance(jumpScaledWidth, jumpScaledHeight, Image.SCALE_SMOOTH);

        rightRabbitSliding = rightRabbitSliding.getScaledInstance(slideScaledWidth, slideScaledHeight, Image.SCALE_SMOOTH);
        leftRabbitSliding = leftRabbitSliding.getScaledInstance(slideScaledWidth, slideScaledHeight, Image.SCALE_SMOOTH);

        addKeyListener(new MyKeyListener());
        setContentPane(new MyPanel());
    }

    private class MyPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int bgWidth = earthBackground.getWidth(null);
            int panelWidth = getWidth();

            backgroundX -= backgroundSpeed; // 배경의 위치 업데이트

            if (backgroundX < -bgWidth) {
                backgroundX = 0; // 배경이 화면 밖으로 나가면 위치 초기화
            }

            g.drawImage(earthBackground, backgroundX, 0, null);
            if (backgroundX < panelWidth - bgWidth) {
                g.drawImage(earthBackground, backgroundX + bgWidth, 0, null); // 다른 배경 이미지를 그립니다.
            }

            //400마다 장애물 저장, 장애물별로 y좌표 부여
            if (backgroundX % 400 == 0) {
                i++;
                randnum = (int) (Math.random() * 5);
                if(randnum==0||randnum==1){
                    y = 630;
                }else{
                    y=760;
                }
                numbers.add(new int[]{randnum, 1700+i*300, y});
            }

            //토끼 슬라이딩 위치 수정
            Image currentRabbitImage = getCurrentRabbitImage();
            int rabbitWidth = currentRabbitImage.getWidth(null);
            int rabbitHeight = currentRabbitImage.getHeight(null);

            int slidingOffsetY = 80; // 슬라이딩 이미지의 Y 좌표 조절 값

            if(isSliding&&isJumping){
                g.drawImage(currentRabbitImage, rabbitX, rabbitY,  null);
            }
            else if (isSliding) {
                int slidingX = rabbitX + (rabbitWidth / 2) - (rabbitWidth / 4);
                int slidingY = rabbitY + (rabbitHeight / 2) - (rabbitHeight / 4) + slidingOffsetY;
                g.drawImage(currentRabbitImage, slidingX, slidingY, null);
            }
            else {
                g.drawImage(currentRabbitImage, rabbitX, rabbitY, null);
            }
            //플레이어 생명
            for (int i = 0; i < playerHeart; i++) { //하트 그리기
                g.drawImage(heart, 40 + i * 85, 40, this);
            }
            //빈 하트 그리기
            for (int i = 0; i < 5-playerHeart; i++) { //하트 그리기
                g.drawImage(emptyHeart, 380-i*85, 40, this);
            }
            //장애물을 그리는 for문
            for(int[] number : numbers){
                g.drawImage(Obstacle[number[0]], number[1], number[2], this);
            }
        }
    }

    //충돌처리
    private void chechCollision(){
//        Rectangle Obstacle = new Rectangle()
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // 키 입력 시 처리할 내용을 여기에 작성
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //키가 눌렸을 때 처리
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                isMovingRight = true;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                isMovingLeft = true;
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
                isJumping = true;
                new Thread(() -> jump()).start();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !isJumping) {
                isSliding = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //키가 떼졌을 대 처리
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                isMovingRight = false;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                isMovingLeft = false;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                isSliding = false;
            }
        }
    }

    //점프관련 코드
    public void jump() {
        int jumpHeight = 300; // 점프 높이
        int jumpSpeed = 7; // 점프 속도
        int initialY = rabbitY;

        for (int i = 0; i < jumpHeight; i += jumpSpeed) {
            rabbitY = initialY - i;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = jumpHeight; i > 0; i -= jumpSpeed) {
            rabbitY = initialY - i;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        rabbitY = initialY;
        isJumping = false;
    }

    public void update() {
        boolean isMoving = isMovingRight || isMovingLeft; // 토끼가 이동 중인지 확인

        if (isMoving) {
            if (isMovingRight) {
                rabbitX += rabbitMoveSpeed;
            } else if (isMovingLeft) {
                rabbitX -= rabbitMoveSpeed+10; // 왼쪽으로 이동할 때 토끼의 위치를 왼쪽으로 변경합니다.
            }
        } else {
            rabbitX -= backgroundSpeed;
        }

        //게임오버 조건 : 범위 벗어남, 목숨 0
        int rabbitLeftEdge = rabbitX - 10;
        int rabbitRightEdge = rabbitX + scaledWidth;
        if (rabbitLeftEdge + 200 < 0 || rabbitRightEdge > getWidth()||playerHeart==0) {
            System.out.println("게임 오버");

//            new GameOver();
//            setVisible(false);
        }

        frameCount++;
        if (frameCount % frameDelay == 0 && isMoving) { // 오른쪽 또는 왼쪽으로 이동 중에만 이미지 변경
            frameCount = 0;
            toggleRabbitImage();
        }
        for(int[] number : numbers){
            number[1]-=backgroundSpeed;
        }
        repaint();
    }

    //토끼 달리는 모션 구현!
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

    //토끼 이미지 수정
    private Image getCurrentRabbitImage() {
        if (isMovingLeft) {
            return isJumping ? leftRabbitJump : (isSliding ? leftRabbitSliding : leftRabbit[1]);
        } else if (isMovingRight) {
            return isJumping ? rightRabbitJump : (isSliding ? rightRabbitSliding : rightRabbit[1]);
        } else {
            return isJumping ? rightRabbitJump : (isSliding ? rightRabbitSliding : rightRabbit[1]);
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