import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;

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
    private Image rabbit_face;
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
    // 충돌 상태 기록 변수
    private boolean isCollisionDetected = false;
    // 충돌 후 2초간 충돌 인식 안 되도록 하는 타이머
    Timer collisionTimer;
    // 게임 타이머
    private Timer gameTimer;
    private int elapsedTime; // 경과 시간 변수
    // 충돌 시 토끼 흔들림 변수
    private boolean isRabbitShaking;
    private int rabbitShakeCount;
    private final int rabbitShakeDuration = 200; // 토끼 흔들림 지속 시간(ms)
    // 위치바
    private Image route;
    private Timer routeTimer;
    private int locX = 670;
    private boolean collisionDetectionEnabled = true;   // 충돌 처리 활성화 유무
    //점수 초기화
    private int score = 100000;
    //배경 음악
    private static Clip clip;
    private static String bgmFilePath = "audio/EarthPartMusic.wav";

    public EarthPart() {
        setUndecorated(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(true);
        setVisible(true);
        init();
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
        Obstacle[0] = new ImageIcon(getClass().getResource("img/hurdle.png")).getImage();
        Obstacle[1] = new ImageIcon(getClass().getResource("img/plant2.png")).getImage();
        Obstacle[2] = new ImageIcon(getClass().getResource("img/hurdle.png")).getImage();
        Obstacle[3] = new ImageIcon(getClass().getResource("img/plant.png")).getImage();
        Obstacle[4] = new ImageIcon(getClass().getResource("img/plant2.png")).getImage();
        heart = new ImageIcon(getClass().getResource("img/heart.png")).getImage();
        emptyHeart = new ImageIcon(getClass().getResource("empty_heart.png")).getImage();
        route = new ImageIcon(getClass().getResource("img/route2.png")).getImage();
        rabbit_face = new ImageIcon(getClass().getResource("img/rabbit_face.png")).getImage();
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

        playBackgroundMusic(bgmFilePath);


//        gameTimer = new Timer(10, new ActionListener() {
//            private int elapsedTime = 0; // 경과 시간(밀리초)
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                repaint();
//
//                elapsedTime += 10; // 10밀리초마다 경과 시간 증가
//                score -= 1;
//
//                // 경과 시간에 따른 게임 클리어 처리
//                if (elapsedTime >= 16500) {
//                    collisionDetectionEnabled = false;
//                    gameTimer.stop();
//                    // 게임 클리어 처리 - - -
//                    System.out.println("클리어");
//                    SpacePart.score = score+(playerHeart*50);
//                    new SpaceRule();
//                    setVisible(false);
//                }
//            }
//        });
//
//        gameTimer.start();

        // 위치바 이동 타이머 설정
        routeTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이미지의 위치를 업데이트
                locX += 2;

                // 화면을 다시 그리기
                repaint();
            }
        });

    }
    private void init(){
        // 게임 타이머가 이미 등록되었는지 확인
        if (gameTimer == null || !gameTimer.isRunning()) {
            // 게임 타이머 생성 및 등록
            gameTimer = new Timer(10, new ActionListener() {
                private int elapsedTime = 0; // 경과 시간(밀리초)
                @Override
                public void actionPerformed(ActionEvent e) {
                    repaint();
                    try {
                        update();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    //게임오버 조건 : 범위 벗어남, 목숨 0
                    int rabbitLeftEdge = rabbitX - 10;
                    int rabbitRightEdge = rabbitX + scaledWidth;
                    if (rabbitLeftEdge + 200 < 0 || rabbitRightEdge > getWidth() || playerHeart == 0) {
                        System.out.println("게임 오버");
                        collisionDetectionEnabled = false;
                        gameTimer.stop();
                        routeTimer.stop();
                        // 음악 멈추기
                        stopBackgroundMusic();
                        new EarthGameOver();
                        setVisible(false);
                    }

                    elapsedTime += 10; // 10밀리초마다 경과 시간 증가
                    score -= 1;

                    // 경과 시간에 따른 게임 클리어 처리
                    if (elapsedTime >= 16500) {
                        collisionDetectionEnabled = false;
                        gameTimer.stop();
                        // 게임 클리어 처리 - - -
                        System.out.println("클리어");
                        // 음악 멈추기
                        stopBackgroundMusic();
                        SpacePart.score = score+(playerHeart*50);
                        new SpaceRule();
                        setVisible(false);
                    }
                }
            });
            gameTimer.start();
        }
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setVisible(true);
        // 주기적으로 업데이트 메서드 호출
//        Timer timer = new Timer(15, e -> {
//            try {
//                update();
//            } catch (InterruptedException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//        timer.start();

    }

    private class MyPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int bgWidth = earthBackground.getWidth(null);
            int panelWidth = getWidth();
            backgroundX -= backgroundSpeed; // 배경의 위치 업데이트
            if (backgroundX < -bgWidth){
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
                if(randnum==0){ //허들
                    y = 720;
                }else if(randnum==1) {  //선인장
                    y = 700;
                }else if(randnum==2){   //허들
                    y=720;
                }else{      //선인장
                    y=700;
                }
                numbers.add(new int[]{randnum, 1550+i*400, y});
            }
            //토끼 슬라이딩 위치 수정
            Image currentRabbitImage = getCurrentRabbitImage();
            int rabbitWidth = currentRabbitImage.getWidth(null);
            int rabbitHeight = currentRabbitImage.getHeight(null);
            int slidingOffsetY = 80; // 슬라이딩 이미지의 Y 좌표 조절 값
            if (isRabbitShaking) {
                if (rabbitShakeCount % 20 < 10) {
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
                }
            } else {
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

            g.drawImage(route, 700,870, null);
            g.drawImage(rabbit_face, locX, 950, null);

            //점수 띄우기 - 10 단위로 보이게
            g.setFont(new Font("맑은고딕", Font.BOLD, 46));
            g.setColor(Color.WHITE);
            //g.drawString();
            g.drawString("점수 : "+Integer.toString(score/10*10), 1550, 100);


        }
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
        int jumpHeight = 310; // 점프 높이
        int jumpSpeed = 8; // 점프 속도
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
    public void update() throws InterruptedException {
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
        frameCount++;
        if (frameCount % frameDelay == 0 && isMoving) { // 오른쪽 또는 왼쪽으로 이동 중에만 이미지 변경
            frameCount = 0;
            toggleRabbitImage();
        }
        for(int[] number : numbers){
            number[1]-=backgroundSpeed;
        }
        checkCollision();
        // 충돌 시 토끼 흔들리기
        if (isRabbitShaking) {
            rabbitShakeCount += 10;
            if (rabbitShakeCount >= rabbitShakeDuration) {
                isRabbitShaking = false;
                rabbitShakeCount = 0;
            }
        }
        routeTimer.start();
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

    // 장애물 충돌 체크
    private void checkCollision() throws InterruptedException {
        if(!collisionDetectionEnabled) {
            return; // 충돌 감지 비활성화 상태면 종료
        }
        // 토끼 이미지 배열
        Image[] rabbitImages = getRabbitImages();
        // 점프 및 슬라이딩 이미지
        Image jumpImage = getJumpImage();
        Image slidingImage = getSlidingImage();

        // 토끼의 현재 이미지 가져오기
        Image currentRabbitImage;
        if (isJumping) {
            currentRabbitImage = jumpImage;
        } else if (isSliding) {
            currentRabbitImage = slidingImage;
        } else {
            int frameIndex = frameCount / frameDelay % rabbitImages.length;
            currentRabbitImage = rabbitImages[frameIndex];
        }

        int rabbitWidth = currentRabbitImage.getWidth(null);
        int rabbitHeight = currentRabbitImage.getHeight(null);
        Rectangle rabbitRect = new Rectangle(rabbitX, rabbitY+10, rabbitWidth-20, rabbitHeight);

        for (int[] obstacle : numbers) {
            // 장애물 이미지, X좌표, Y좌표
            Image obstacleImage = Obstacle[obstacle[0]];
            int obstacleX = obstacle[1];
            int obstacleY = obstacle[2];
            int obstacleWidth = obstacleImage.getWidth(null);
            int obstacleHeight = obstacleImage.getHeight(null);
            Rectangle obstacleRect = new Rectangle(obstacleX+10, obstacleY+10, obstacleWidth-20, obstacleHeight);

            if (rabbitRect.intersects(obstacleRect) && !isCollisionDetected) {
                // 충돌 발생! 처리 로직을 여기에 작성
                System.out.println("토끼와 장애물이 충돌했습니다!");

                // 충돌 감지
                playerHeart -= 1;
                // 점수 -100
                score -= 100;
                isCollisionDetected = true;
                // 충돌 시 토끼 흔들리도록 설정
                isRabbitShaking = true;

                collisionTimer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        isCollisionDetected = false;
                    }
                });

                collisionTimer.setRepeats(false); // 타이머가 반복 실행되지 않도록 설정합니다.
                collisionTimer.start(); // 타이머를 실행합니다.
            }
        }
    }

    // 토끼 이미지 배열 반환
    private Image[] getRabbitImages() {
        if (isMovingLeft) {
            return leftRabbit;
        } else {
            return rightRabbit;
        }
    }

    // 점프 이미지 반환
    private Image getJumpImage() {
        if (isMovingLeft) {
            return leftRabbitJump;
        } else {
            return rightRabbitJump;
        }
    }

    // 슬라이딩 이미지 반환
    private Image getSlidingImage() {
        if (isMovingLeft) {
            return leftRabbitSliding;
        } else {
            return rightRabbitSliding;
        }
    }

    public static void playBackgroundMusic(String filePath) {
        try {
            // 배경음악 파일 로딩
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    ToTheMoon.class.getResourceAsStream(filePath));

            // 오디오 포맷 가져오기
            AudioFormat audioFormat = audioInputStream.getFormat();

            // 데이터 라인 정보 생성
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);

            // 데이터 라인 생성
            clip = (Clip) AudioSystem.getLine(info);

            // 데이터 라인 열기
            clip.open(audioInputStream);

            // 무한 반복 재생
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // 재생 시작
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EarthPart earthPart = new EarthPart();
            earthPart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            earthPart.setVisible(true);
            //주기적으로 업데이트 메소드 호출
            Timer timer = new Timer(16, e -> {
                try {
                    earthPart.update();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
            timer.start();
        });
    }
}