import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EarthPart extends JFrame implements KeyListener {
    private Image earthBackground;
    private ImageIcon[] rabbit = new ImageIcon[2];
    private boolean isJumping = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private int rabbitX = 500;
    private int rabbitY = 500;
    private int rabbitMoveSpeed = 200; // Rabbit movement speed (pixels per second)
    private Timer timer;
    private long lastKeyPressTime = 0;

    public EarthPart() {
        setUndecorated(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(true);

        earthBackground = new ImageIcon(getClass().getResource("img/EarthBackground.png")).getImage();
        ImageIcon rabbitBefore = new ImageIcon(getClass().getResource("img/rabbit.png"));
        ImageIcon rabbitJumpBefore = new ImageIcon(getClass().getResource("img/rabbitJump.png"));

        int rabbitWidth = rabbitBefore.getIconWidth();
        int rabbitHeight = rabbitBefore.getIconHeight();

        int scaledWidth = (int) (rabbitWidth * 0.3);
        int scaledHeight = (int) (rabbitHeight * 0.3);

        rabbit[0] = new ImageIcon(rabbitBefore.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        rabbit[1] = new ImageIcon(rabbitJumpBefore.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(earthBackground, 0, 0, getWidth(), getHeight(), null);
                g.drawImage(rabbit[isJumping ? 1 : 0].getImage(), rabbitX, rabbitY, null);
            }
        };

        contentPane.setLayout(null);
        setContentPane(contentPane);
        setVisible(true);
        addKeyListener(this);

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

            contentPane.repaint();
            lastKeyPressTime = currentTime;
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
            isJumping = true;
            timer.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            isMovingRight = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            isMovingLeft = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            isMovingRight = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            isMovingLeft = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EarthPart());
    }
}
