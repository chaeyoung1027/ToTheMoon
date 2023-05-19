import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ToTheMoon extends JFrame {
    private Image mainBackground;

    private ImageIcon originalStartButtonIcon;
    private ImageIcon originalRuleButtonIcon;
    private ImageIcon originalExitButtonIcon;

    private ImageIcon scaledStartButtonIcon;
    private ImageIcon scaledRuleButtonIcon;
    private ImageIcon scaledExitButtonIcon;

    private JButton StartButton;
    private JButton RuleButton;
    private JButton ExitButton;

    public ToTheMoon() {
        setUndecorated(true);                           //프레임 바 제거
        setVisible(true);                               //프레임 표기
        setSize(1920, 1080);                //프레임 크기 지정
        setLocationRelativeTo(null);                    //화면 중앙에 위치하도록 설정
        setBackground(new Color(0, 0, 0, 0));//배경을 투명으로 지정
        setLayout(null);                                //컴포넌트를 수동으로 배치하기 위한 레이아웃 매니저 null

        //이미지아이콘 추가
        mainBackground = new ImageIcon(getClass().getResource("img/MainBackground.png")).getImage();
        originalStartButtonIcon = new ImageIcon(getClass().getResource("img/StartButton.png"));
        originalRuleButtonIcon = new ImageIcon(getClass().getResource("img/RuleButton.png"));
        originalExitButtonIcon = new ImageIcon(getClass().getResource("img/ExitButton.png"));

        // 초기 이미지 크기 설정
        int buttonWidth = originalStartButtonIcon.getIconWidth();
        int buttonHeight = originalStartButtonIcon.getIconHeight();

        // 이미지 크기를 원본의 60%로 축소
        int scaledWidth = (int) (buttonWidth * 0.5);
        int scaledHeight = (int) (buttonHeight * 0.5);

        // 이미지 크기 조정
        scaledStartButtonIcon = new ImageIcon(originalStartButtonIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        scaledRuleButtonIcon = new ImageIcon(originalRuleButtonIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        scaledExitButtonIcon = new ImageIcon(originalExitButtonIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        //startButton 설정
        StartButton = new JButton(scaledStartButtonIcon);
        StartButton.setBorderPainted(false);
        StartButton.setContentAreaFilled(false);
        StartButton.setFocusPainted(false);
        StartButton.setOpaque(false);
        StartButton.setBackground(new Color(0, 0, 0, 0)); // 배경을 투명하게 설정
        StartButton.setBounds(811, 320, scaledWidth, scaledHeight);

        //startButton 마우스 리스너
        StartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 이미지 크기를 90%로 축소
                int pressedWidth = (int) (scaledWidth * 0.9);
                int pressedHeight = (int) (scaledHeight * 0.9);

                // 이미지 크기 조정
                scaledStartButtonIcon = new ImageIcon(originalStartButtonIcon.getImage().getScaledInstance(pressedWidth, pressedHeight, Image.SCALE_SMOOTH));
                StartButton.setIcon(scaledStartButtonIcon);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                scaledStartButtonIcon = new ImageIcon(originalStartButtonIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
                StartButton.setIcon(scaledStartButtonIcon);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        //RuleButton 설정
        RuleButton = new JButton(scaledRuleButtonIcon);
        RuleButton.setBorderPainted(false);
        RuleButton.setContentAreaFilled(false);
        RuleButton.setFocusPainted(false);
        RuleButton.setOpaque(false);
        RuleButton.setBackground(new Color(0, 0, 0, 0)); // 배경을 투명하게 설정
        RuleButton.setBounds(811, 500, scaledWidth, scaledHeight);

        //RuleButton 마우스 리스너
        RuleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 이미지 크기를 90%로 축소
                int pressedWidth = (int) (scaledWidth * 0.9);
                int pressedHeight = (int) (scaledHeight * 0.9);

                // 이미지 크기 조정
                scaledRuleButtonIcon = new ImageIcon(originalRuleButtonIcon.getImage().getScaledInstance(pressedWidth, pressedHeight, Image.SCALE_SMOOTH));
                RuleButton.setIcon(scaledRuleButtonIcon);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                scaledRuleButtonIcon = new ImageIcon(originalRuleButtonIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
                RuleButton.setIcon(scaledRuleButtonIcon);
                new Rule();
                setVisible(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        //ExitButton 설정
        ExitButton = new JButton(scaledExitButtonIcon);
        ExitButton.setBorderPainted(false);
        ExitButton.setContentAreaFilled(false);
        ExitButton.setFocusPainted(false);
        ExitButton.setOpaque(false);
        ExitButton.setBackground(new Color(0, 0, 0, 0)); // 배경을 투명하게 설정
        ExitButton.setBounds(811, 680, scaledWidth, scaledHeight);
        //ExitButton 마우스 리스너
        ExitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 이미지 크기를 90%로 축소
                int pressedWidth = (int) (scaledWidth * 0.9);
                int pressedHeight = (int) (scaledHeight * 0.9);

                // 이미지 크기 조정
                scaledExitButtonIcon = new ImageIcon(scaledExitButtonIcon.getImage().getScaledInstance(pressedWidth, pressedHeight, Image.SCALE_SMOOTH));
                ExitButton.setIcon(scaledExitButtonIcon);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(mainBackground, 0, 0, getWidth(), getHeight(), null);
            }
        };
        contentPane.setLayout(null);    //배경 위치 지정을 위한 코드

        //버튼 추가
        contentPane.add(StartButton);
        contentPane.add(RuleButton);
        contentPane.add(ExitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("To The Moon");
        setContentPane(contentPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ToTheMoon();
    }
}
