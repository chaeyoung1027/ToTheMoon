import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EarthPart extends JFrame implements KeyListener {
    private Image[] earthBackground = new Image[2];
    private ImageIcon[] rabbit = new ImageIcon[2];
    private ImageIcon[] object = new ImageIcon[5];

    private boolean isJumping = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private int rabbitX = 500;
    private int rabbitY = 500;
    private int rabbitMoveSpeed = 200;
    private Timer timer;
    private long lastKeyPressTime = 0;

    private List<Point> objectPositions = new ArrayList<>(); // 객체의 위치를 저장하는 리스트
    private List<Integer> objectTypes = new ArrayList<>(); // 객체의 종류를 저장하는 리스트

    private int currentObjectX = -1; // 현재 토끼가 올라간 객체의 x 좌표
    private int currentObjectY = -1; // 현재 토끼가 올라간 객체의 y 좌표

    public EarthPart() {
        setUndecorated(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(true);

        earthBackground[0] = new ImageIcon(getClass().getResource("img/EarthBackground.png")).getImage();
        earthBackground[1] = new ImageIcon(getClass().getResource("img/EarthBackground2.png")).getImage();
        ImageIcon rabbitBefore = new ImageIcon(getClass().getResource("img/rabbit.png"));
        ImageIcon rabbitJumpBefore = new ImageIcon(getClass().getResource("img/rabbitJump.png"));

        int rabbitWidth = rabbitBefore.getIconWidth();
        int rabbitHeight = rabbitBefore.getIconHeight();

        int scaledWidth = (int) (rabbitWidth * 0.3);
        int scaledHeight = (int) (rabbitHeight * 0.3);

        rabbit[0] = new ImageIcon(rabbitBefore.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        rabbit[1] = new ImageIcon(rabbitJumpBefore.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        object[0] = new ImageIcon(getClass().getResource("img/BlackCloud.png"));
        object[1] = new ImageIcon(getClass().getResource("img/Cloud2.png"));
        object[2] = new ImageIcon(getClass().getResource("img/Cloud3.png"));
        object[3] = new ImageIcon(getClass().getResource("img/빨간새.png"));
        object[4] = new ImageIcon(getClass().getResource("img/파란새.png"));

        // 배경이 자연스럽게 지어지는 코드
        JPanel contentPane = new JPanel() {
            private int backgroundOffset = 0; // 배경 이미지의 Y축 오프셋 값

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 배경 이미지 그리기
                g.drawImage(earthBackground[0], 0, backgroundOffset, getWidth(), getHeight(), null);
                g.drawImage(earthBackground[1], 0, backgroundOffset - getHeight(), getWidth(), getHeight(), null);
                // 토끼 그리기
                g.drawImage(rabbit[isJumping ? 1 : 0].getImage(), rabbitX, rabbitY, null);
                // object 그리기
                drawObjects(g);
            }

            private void drawObjects(Graphics g) {
                for (int i = 0; i < objectPositions.size(); i++) {
                    Point position = objectPositions.get(i);
                    int x = position.x;
                    int y = position.y;

                    int objectType = objectTypes.get(i);

                    g.drawImage(object[objectType].getImage(), x, y, null);

                    // 객체에 올라갔을 때의 처리
                    if (currentObjectX == x && currentObjectY == y) {
                        // 올라간 객체에 대한 추가 동작을 수행
                        // 예: currentObjectX와 currentObjectY를 사용하여 특정 동작을 수행하거나 상태를 변경
                    }
                }
            }
        };

        contentPane.setLayout(null);
        setContentPane(contentPane);
        setVisible(true);
        addKeyListener(this);

        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && !isJumping) {
                    isJumping = true;
                    timer.start();
                }
            }
        });

        timer = new Timer(16, e -> {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastKeyPressTime;

            if (isJumping) {
                rabbitY -= 5;
                if (rabbitY <= 400) {
                    isJumping = false;
                }
            } else {
                rabbitY += 5;
                if (rabbitY >= 500) {
                    rabbitY = 500;
                }
            }

            double movementDelta = rabbitMoveSpeed * (elapsedTime / 1000.0);

            if (isMovingRight) {
                rabbitX += movementDelta;
                if (rabbitX >= 1700) {
                    rabbitX = 1700;
                }
            } else if (isMovingLeft) {
                rabbitX -= movementDelta;
                if (rabbitX <= 10) {
                    rabbitX = 10;
                }
            }

            // 객체와 충돌 감지
            checkCollision();

            contentPane.repaint();
            lastKeyPressTime = currentTime;
        });

        generateObjectPositions(); // 객체의 초기 위치 생성
    }

    private void generateObjectPositions() {
        int objectCount = 5; // 객체의 개수
        int minSpacing = 200; // 객체 사이의 최소 간격
        int maxSpacing = 400; // 객체 사이의 최대 간격
        int maxY = 800; // 객체의 y 좌표 최댓값

        int currentX = 0;

        Random random = new Random();

        for (int i = 0; i < objectCount; i++) {
            int spacing = random.nextInt(maxSpacing - minSpacing + 1) + minSpacing; // 랜덤한 간격 계산
            currentX += spacing;
            int y = random.nextInt(maxY + 1); // 랜덤한 y 좌표 계산

            objectPositions.add(new Point(currentX, y));
            objectTypes.add(random.nextInt(object.length));
        }
    }

    private void checkCollision() {
        Rectangle rabbitRect = new Rectangle(rabbitX, rabbitY + rabbit[0].getIconHeight(), rabbit[0].getIconWidth(), 1);

        for (int i = 0; i < objectPositions.size(); i++) {
            Point position = objectPositions.get(i);
            int objectType = objectTypes.get(i);
            int objectWidth = object[objectType].getIconWidth();
            int objectHeight = object[objectType].getIconHeight();

            Rectangle objectRect = new Rectangle(position.x, position.y, objectWidth, objectHeight);

            if (rabbitRect.intersects(objectRect)) {
                rabbitY = objectRect.y - rabbit[0].getIconHeight();
                currentObjectX = position.x;
                currentObjectY = position.y;
                break;
            } else {
                currentObjectX = -1;
                currentObjectY = -1;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            isMovingLeft = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            isMovingRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            isMovingLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            isMovingRight = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EarthPart earthPart = new EarthPart();
            earthPart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
