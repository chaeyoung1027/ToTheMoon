import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EarthPart extends JFrame implements KeyListener {
    private Image earthBackground;
    private ImageIcon[] rabbit = new ImageIcon[2];
    private boolean isJumping = false;
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
        setFocusable(true); // Set focusable to receive key events

        earthBackground = new ImageIcon(getClass().getResource("img/EarthBackground.png")).getImage();
        ImageIcon rabbitBefore = new ImageIcon(getClass().getResource("img/rabbit.png"));
        ImageIcon rabbitJumpBefore = new ImageIcon(getClass().getResource("img/rabbitJump.png"));

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
                rabbitY -= 2;
                if (rabbitY <= 400) {
                    isJumping = false;
                }
            } else {
                rabbitY += 2;
                if (rabbitY >= 500) {
                    rabbitY = 500;
                    timer.stop();
                }
            }

            if (elapsedTime >= 16) { // Adjust the interval if needed for smoother movement
                double movementDelta = rabbitMoveSpeed * (elapsedTime / 1000.0); // Calculate the position change based on elapsed time

                if (KeyState.isRightPressed) {
                    rabbitX += movementDelta;
                    if (rabbitX >= 1420) { // Adjust the right boundary if needed
                        rabbitX = 1420;
                    }
                } else if (KeyState.isLeftPressed) {
                    rabbitX -= movementDelta;
                    if (rabbitX <= 20) { // Adjust the left boundary if needed
                        rabbitX = 20;
                    }
                }
                contentPane.repaint();
                lastKeyPressTime = currentTime;
            }
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
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            KeyState.isRightPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            KeyState.isLeftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            KeyState.isRightPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            KeyState.isLeftPressed = false;
        }
    }

    private static class KeyState {
        private static boolean isRightPressed = false;
        private static boolean isLeftPressed = false;
    }

    public static void main(String[] args) {
        new EarthPart();
    }
}
