//import java.awt.Color;
//import java.awt.Cursor;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.GraphicsEnvironment;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//public class ToTheMoon extends JFrame {
//    private Image mainBackground;
//    private JButton startButton;
//
//    public ToTheMoon() {
//        mainBackground = new ImageIcon(getClass().getResource("img/MainBackground.png")).getImage();
//        ImageIcon startButtonIcon = new ImageIcon(getClass().getResource("img/StartButton.png"));
//        startButton = new JButton(startButtonIcon);
//        startButton.setBorderPainted(false);
//        startButton.setContentAreaFilled(false);
//        startButton.setFocusPainted(false);
//        startButton.setOpaque(false);
//
//        JPanel contentPane = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                g.drawImage(mainBackground, 0, 0, getWidth(), getHeight(), null);
//            }
//        };
//        contentPane.setLayout(null);
//        contentPane.setOpaque(false);
//
//        contentPane.add(startButton);
//
//        setCursor(Cursor.getDefaultCursor());
//        setResizable(false);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setTitle("To The Moon");
//        setContentPane(contentPane);
//
//        // 프레임이 화면에 꽉 차도록 설정
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setBounds(0, 0, screenSize.width, screenSize.height);
//
//        // 버튼의 초기 위치 설정
//        int buttonWidth = startButton.getIcon().getIconWidth();
//        int buttonHeight = startButton.getIcon().getIconHeight();
//        int buttonX = (getWidth() - buttonWidth) / 2;
//        int buttonY = (getHeight() - buttonHeight) / 2;
//        startButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
//
//        startButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                // 버튼이 작아진 크기 설정
//                int pressedWidth = (int) (buttonWidth * 0.9);
//                int pressedHeight = (int) (buttonHeight * 0.9);
//                int pressedX = buttonX + (buttonWidth - pressedWidth) / 2;
//                int pressedY = buttonY + (buttonHeight - pressedHeight) / 2;
//                startButton.setBounds(pressedX, pressedY, pressedWidth, pressedHeight);
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                // 버튼 크기를 원래 크기로 설정
//                startButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
//            }
//        });
//
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        new ToTheMoon();
//    }
//}
