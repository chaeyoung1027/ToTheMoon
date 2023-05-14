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

public class ToTheMoon extends JFrame {
    // Background Img
    private Image mainBackground;
    // Start Button
    private Image startButtonImage;
    private JButton startJButton;

    public ToTheMoon() {
        mainBackground = new ImageIcon(getClass().getResource("img/MainBackground.png")).getImage();
        startButtonImage = new ImageIcon(getClass().getResource("img/StartButton.png")).getImage();
        Image startButtonSize = startButtonImage.getScaledInstance(500, 250, Image.SCALE_SMOOTH);
        ImageIcon startButtonIcon = new ImageIcon(startButtonSize);
        startJButton = new JButton(startButtonIcon);

        setCursor(Cursor.getDefaultCursor());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("To The Moon");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        setLayout(null); // 레이아웃 매니저 해제

        // Start Button on Main
        startJButton.setBounds(700, 400, startButtonSize.getWidth(null), startButtonSize.getHeight(null));
        startJButton.setBorderPainted(false);
        startJButton.setContentAreaFilled(false);
        startJButton.setFocusPainted(false);
        startJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                // 버튼 이미지 작게 바뀌게 하기
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                // 버튼 이미지 원래대로 돌아오게 하기
            }
        });

        add(startJButton);
        setVisible(true); // 버튼이 화면에 표시되도록 setVisible(true) 호출
    }

    // 그리는 함수
    @Override
    public void paint(Graphics g) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);

        super.paint(g);
        g.drawImage(mainBackground, 0, 0, getWidth(), getHeight(), null);
    }

    public static void main(String args[]) {
        new ToTheMoon();
    }
}
