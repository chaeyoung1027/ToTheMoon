import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ToTheMoon extends JFrame {
    private Image mainBackground;
    private ImageIcon originalStartButtonIcon;
    private ImageIcon scaledStartButtonIcon;
    private JButton startButton;

    public ToTheMoon() {
        mainBackground = new ImageIcon(getClass().getResource("img/MainBackground.png")).getImage();
        originalStartButtonIcon = new ImageIcon(getClass().getResource("img/StartButton.png"));

        // 초기 이미지 크기 설정
        int buttonWidth = originalStartButtonIcon.getIconWidth();
        int buttonHeight = originalStartButtonIcon.getIconHeight();

        // 이미지 크기를 90%로 축소
        int scaledWidth = (int) (buttonWidth * 0.6);
        int scaledHeight = (int) (buttonHeight * 0.6);

        // 이미지 크기 조정
        scaledStartButtonIcon = new ImageIcon(originalStartButtonIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        startButton = new JButton(scaledStartButtonIcon);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        startButton.setBounds(782, 320, scaledWidth, scaledHeight);

        //마우스
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 이미지 크기를 90%로 축소
                int pressedWidth = (int) (scaledWidth * 0.9);
                int pressedHeight = (int) (scaledHeight * 0.9);

                // 이미지 크기 조정
                scaledStartButtonIcon = new ImageIcon(originalStartButtonIcon.getImage().getScaledInstance(pressedWidth, pressedHeight, Image.SCALE_SMOOTH));

                startButton.setIcon(scaledStartButtonIcon);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                scaledStartButtonIcon = new ImageIcon(originalStartButtonIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

                startButton.setIcon(scaledStartButtonIcon);
            }
        });

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(mainBackground, 0, 0, getWidth(), getHeight(), null);
            }
        };
        contentPane.setLayout(null);
        contentPane.setOpaque(false);
        contentPane.add(startButton);

        setCursor(Cursor.getDefaultCursor());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("To The Moon");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(contentPane);
        setVisible(true);
    }



    public static void main(String[] args) {
        new ToTheMoon();
    }
}
