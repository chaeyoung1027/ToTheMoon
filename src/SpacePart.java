import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SpacePart extends JFrame implements KeyListener {
    private Image screenImage;
    private Graphics screenGraphic;
    private Timer gameTimer;
    private Random random = new Random();

    // 이동 여부
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/SpaceBackground.png")).getImage();
    private int yPos1; // 첫 번째 배경 이미지의 y 좌표
    private int yPos2; // 두 번째 배경 이미지의 y 좌표

    Timer imageTimer;   // 배경 이미지 변경 타이머

    // 우주선
    private Image[] spaceshipImages = {
            new ImageIcon(ToTheMoon.class.getResource("spaceship_fire.png")).getImage(),
            new ImageIcon(ToTheMoon.class.getResource("spaceship_fire2.png")).getImage()
    };
    private int spaceshipImageIndex = 0;

    private ImageIcon ship1 = new ImageIcon(spaceshipImages[0]);
    private ImageIcon ship2 = new ImageIcon(spaceshipImages[1]);

    private int spaceshipX = 810; // 초기 X 좌표
    private int spaceshipY = 750; // 초기 Y 좌표

    private JLabel imageLabel;
    private int currentIndex = 0;

    // 체력바
    private int hp = 250;
    private Image life = new ImageIcon(ToTheMoon.class.getResource("img/life.png")).getImage();
    Timer hpDecreaseTimer; // HP 감소 타이머

    // 장애물 리스트
    private ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
    private Obstacle obstacle;

    Timer obstacleTimer;
    Timer shakeTimer;   // 충돌 모션 타이머

    private int shakeCount = 0;

    // 아이템 리스트
    private ArrayList<Item> itemList = new ArrayList<Item>();
    private Item item;

    Timer itemTimer;

    public SpacePart() {
        setUndecorated(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(true);
        setVisible(true);

        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });

        // KeyListener 등록
        addKeyListener(this);

        // --- 배경 이미지 흐르게 ---
        // 배경 이미지 설정
        yPos1 = 0;
        yPos2 = -background.getHeight(null); // 두 번째 배경 이미지는 첫 번째 배경 이미지의 위쪽에 위치

        // --- 우주선 애니메이션 ---
        // 우주선 이미지 설정
        spaceshipImageIndex = 0;

        // 이미지 라벨 생성
        imageLabel = new JLabel();
        imageLabel.setBounds(spaceshipX, spaceshipY, spaceshipImages[0].getWidth(null), spaceshipImages[0].getHeight(null));
        imageLabel.setIcon(ship1);
        add(imageLabel);

        // 이미지 변경 타이머 설정
        imageTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceshipImageIndex = (spaceshipImageIndex + 1) % spaceshipImages.length;
                Image currentSpaceshipImage = spaceshipImages[spaceshipImageIndex];
                ship1.setImage(currentSpaceshipImage);
                imageLabel.setIcon(ship1);
            }
        });
        imageTimer.start();

        // HP 감소 타이머 설정
        hpDecreaseTimer = new Timer(1000, new ActionListener() {    // 1초 단위
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseHP();
            }
        });
        hpDecreaseTimer.start();

        // 충돌 시 우주선 모션
        shakeTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (shakeCount % 2 == 0) {
                    spaceshipX -= 10; // 좌로 흔들림
                } else {
                    spaceshipX += 10; // 우로 흔들림
                }

                shakeCount++;

                if (shakeCount >= 200 / 30) {
                    stopShakeAnimation();
                }

                repaint();
            }
        });
    }

    public void startGame() {
        gameTimer.start();
        generateObstacles();
        generateItems();
    }

    private void update() {
        moveObstacles();
        moveItems();
        checkCollision();
    }

    private void decreaseHP() {
        hp -= 5; // 1초에 5씩 감소

        if (hp <= 0) {
            gameOver();
        }
    }

    private void moveObstacles() {
        for(int i=obstacleList.size()-1; i>=0; i--) {
            Obstacle obstacle = obstacleList.get(i);

            obstacle.y += obstacle.getSpeed();   // 장애물 이동 속도
            if(obstacle.y > 1080) {
                obstacleList.remove(i);
            }
        }
    }

    private void moveItems() {
        for(int i=itemList.size()-1; i>=0; i--) {
            Item item = itemList.get(i);
            item.y += 10;    // 아이템 이동 속도
            if(item.y > 1080) {
                itemList.remove(i);
            }
        }
    }

    private void startShakeAnimation() {
        if (!shakeTimer.isRunning()) {
            shakeTimer.start(); // 모션 타이머 시작
        } else {
            shakeCount = 0; // 모션 타이머가 이미 실행 중인 경우 초기화
        }
    }

    private void stopShakeAnimation() {
        int originalSpaceshipX = spaceshipX;
        if (shakeTimer.isRunning()) {
            shakeTimer.stop(); // 모션 타이머 중지
            spaceshipX = originalSpaceshipX; // 우주선 위치 초기화
            shakeCount = 0;
            repaint(); // 다시 그리기
        }
    }

    private void checkCollision() {
        Rectangle shipRect = new Rectangle(spaceshipX, spaceshipY, 96, 205);
        //장애물 충돌 검사
        for(int i=obstacleList.size()-1; i>=0; i--) {
            Obstacle obstacle = obstacleList.get(i);
            Rectangle obstacleRect = new Rectangle(obstacle.x, obstacle.y, obstacle.getWidth()-20, obstacle.getHeight()-30);
            if(shipRect.intersects(obstacleRect)) {
                hp -= obstacle.getHPDecrease();
                obstacleList.remove(i);
                if(hp <= 0) {
                    gameOver();
                }
                shakeTimer.start();
            }
        }

        //아이템 충돌 검사
        for (int i = itemList.size() - 1; i >= 0; i--) {
            Item item = itemList.get(i);
            Rectangle itemRect = new Rectangle(item.x, item.y, item.getWidth(), item.getHeight());
            if (shipRect.intersects(itemRect)) {
                if(hp > 250) {
                    hp += 0;
                }
                else {
                    hp += item.getHPIncrease();
                }
                itemList.remove(i);
            }
        }
    }



    private void gameOver() {
        gameTimer.stop();
        imageTimer.stop();
        imageTimer.stop();
        obstacleTimer.stop();
        itemTimer.stop();
        hpDecreaseTimer.stop();
        new GameOver();
        setVisible(false);
    }

    private void generateObstacles() {
        int obstacleTypeCount = 4;
        int obstacleSpawnInterval = 1000; // 1 second
        obstacleTimer = new Timer(obstacleSpawnInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int obstacleType = random.nextInt(obstacleTypeCount);
                int obstacleX = random.nextInt(1920 - 200);
                obstacleList.add(new Obstacle(obstacleX, -200, obstacleType));
            }
        });
        obstacleTimer.start();
    }

    private void generateItems() {
        int itemSpawnInterval = 1000;
        itemTimer = new Timer(itemSpawnInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (random.nextDouble() < 0.2) {
                    int itemType = random.nextInt(2);
                    int itemX = random.nextInt(1920 - 100);
                    itemList.add(new Item(itemX, -200, itemType));
                }
            }
        });
        itemTimer.start();
    }

    private class Obstacle {
        private Image obstacleImage;
        int x, y, type;

        public Obstacle(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;

            // 장애물 타입에 따라 이미지 로드
            if (type == 0) {
                obstacleImage = new ImageIcon(ToTheMoon.class.getResource("img/Meteor1.png")).getImage();
            } else if (type == 1) {
                obstacleImage = new ImageIcon(ToTheMoon.class.getResource("img/Meteor2.png")).getImage();
            } else if (type == 2) {
                obstacleImage = new ImageIcon(ToTheMoon.class.getResource("img/Meteor3.png")).getImage();
            } else if (type == 3) {
                obstacleImage = new ImageIcon(ToTheMoon.class.getResource("img/Meteor4.png")).getImage();
            } else if (type == 4) {
                obstacleImage = new ImageIcon(ToTheMoon.class.getResource("img/Meteor5.png")).getImage();
            }
        }

        public int getWidth() {
            if (obstacleImage != null) {
                return obstacleImage.getWidth(null);
            }
            return 0;
        }

        public int getHeight() {
            if (obstacleImage != null) {
                return obstacleImage.getHeight(null);
            }
            return 0;
        }

        public int getHPDecrease() {
            int hpDecrease = 0;
            // 장애물 종류에 따라 다른 HP 감소량 설정
            if (type == 0 || type == 1) {
                hpDecrease = 30;
            } else if (type == 2 || type == 3) {
                hpDecrease = 50;
            }

            return hpDecrease;
        }

        public int getSpeed() {
            if(type == 2) {
                return 20;
            } else {
                return 15;
            }
        }
    }

    public class Item {
        private Image itemImage;
        int x, y, type;

        public Item(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;

            // 장애물 타입에 따라 이미지 로드
            if (type == 0) {
                itemImage = new ImageIcon(ToTheMoon.class.getResource("img/Fuel.png")).getImage();
            } else if (type == 1) {
                itemImage = new ImageIcon(ToTheMoon.class.getResource("img/FuelTank.png")).getImage();
            }
        }

        public int getWidth() {
            if(itemImage != null) {
                return itemImage.getWidth(null);
            }
            return 0;
        }

        public int getHeight() {
            if(itemImage != null) {
                return itemImage.getHeight(null);
            }
            return 0;
        }

        public int getHPIncrease() {
            int hpIncrease = 0;
            // 아이템 종류에 따라 다른 hp 증가량 설정
            if (type == 0) {
                hpIncrease = 10;
            } else if (type == 1) {
                hpIncrease = 30;
            }
            return hpIncrease;
        }
    }

    // 그리는 함수
    public void paint(Graphics g) {
        super.paint(g);
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

        //우주선 그리기
        g.drawImage(spaceshipImages[spaceshipImageIndex], spaceshipX, spaceshipY, null);

        //체력바 그리기
        g.setColor(Color.GREEN);
        g.fillRect(1605, 980, hp, 20);
        g.drawImage(life, 1548, 957, null);

        //장애물 그리기
        for(Obstacle obstacle : obstacleList) {
            g.drawImage(obstacle.obstacleImage, obstacle.x, obstacle.y, null);
        }

        //아이템 그리기
        for(Item item : itemList) {
            g.drawImage(item.itemImage, item.x, item.y, null);
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

    public static void main(String[] args) {
        SpacePart game = new SpacePart();
        game.startGame();
    }
}