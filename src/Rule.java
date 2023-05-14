import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Rule1 extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/SpaceGameRule1.png")).getImage().getScaledInstance(1920, 1080, 0);;

    // 다음 버튼
    private final Image NextButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Arrow.png")).getImage();
    private Image NextButton1 = NextButtonImage.getScaledInstance(167, 125, 0);
    private ImageIcon NextButton = new ImageIcon(NextButton1);

    private JButton NextJButton = new JButton(NextButton);

    // 처음으로 버튼
    private final Image HomeButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Home.png")).getImage();
    private Image HomeButton1 = HomeButtonImage.getScaledInstance(270, 79, 0);
    private ImageIcon HomeButton = new ImageIcon(HomeButton1);

    private JButton HomeJButton = new JButton(HomeButton);

    public Rule1() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        // 다음 버튼 생성
        NextJButton.setBounds(1691, 925, 167, 125);
        NextJButton.setBorderPainted(false);
        NextJButton.setContentAreaFilled(false);
        NextJButton.setFocusPainted(false);
        NextJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                new Rule2();    // 다음 화면으로 넘어가기
                setVisible(false);
            }
        });
        add(NextJButton);

        // 처음으로 버튼 생성
        HomeJButton.setBounds(62, 945, 270, 79);
        HomeJButton.setBorderPainted(false);
        HomeJButton.setContentAreaFilled(false);
        HomeJButton.setFocusPainted(false);
        HomeJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                new ToTheMoon();    // 홈 화면으로 넘어가기
                setVisible(false);
            }
        });
        add(HomeJButton);

    }

    // 그리는 함수
    public void paint(Graphics g) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
        screenImage = createImage(1920, 1080);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);	// background를 그려줌
    }

    public void screenDraw(Graphics g) {
        g.drawImage(background , 0, 0, null);
        paintComponents(g);
        this.repaint();	// paint 함수로 돌아감
    }
}

class Rule2 extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/SpaceGameRule2.png")).getImage().getScaledInstance(1920, 1080, 0);;

    // 다음 버튼
    private final Image NextButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Arrow.png")).getImage();
    private Image NextButton1 = NextButtonImage.getScaledInstance(167, 125, 0);
    private ImageIcon NextButton = new ImageIcon(NextButton1);

    private JButton NextJButton = new JButton(NextButton);

    // 이전 버튼
    private final Image PrevButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Arrow_reverse.png")).getImage();
    private Image PrevButton1 = PrevButtonImage.getScaledInstance(167, 125, 0);
    private ImageIcon PrevButton = new ImageIcon(PrevButton1);

    private JButton PrevJButton = new JButton(PrevButton);

    public Rule2() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        // 다음 버튼 생성
        NextJButton.setBounds(1691, 925, 167, 125);
        NextJButton.setBorderPainted(false);
        NextJButton.setContentAreaFilled(false);
        NextJButton.setFocusPainted(false);
        NextJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                new Rule3();
                setVisible(false);
            }
        });
        add(NextJButton);

        // 이전 버튼 생성
        PrevJButton.setBounds(62, 925, 167, 125);
        PrevJButton.setBorderPainted(false);
        PrevJButton.setContentAreaFilled(false);
        PrevJButton.setFocusPainted(false);
        PrevJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                new Rule1();
                setVisible(false);
            }
        });
        add(PrevJButton);
    }

    // 그리는 함수
    public void paint(Graphics g) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
        screenImage = createImage(1920, 1080);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);	// background를 그려줌
    }

    public void screenDraw(Graphics g) {
        g.drawImage(background , 0, 0, null);
        paintComponents(g);
        this.repaint();	// paint 함수로 돌아감
    }
}

class Rule3 extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/SpaceGameRule3.png")).getImage().getScaledInstance(1920, 1080, 0);;

    // 게임 시작 버튼
    private final Image StartButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/GameStart.png")).getImage();
    private Image StartButton1 = StartButtonImage.getScaledInstance(270, 79, 0);
    private ImageIcon StartButton = new ImageIcon(StartButton1);

    private JButton StartJButton = new JButton(StartButton);

    // 이전 버튼
    private final Image PrevButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Arrow_reverse.png")).getImage();
    private Image PrevButton1 = PrevButtonImage.getScaledInstance(167, 125, 0);
    private ImageIcon PrevButton = new ImageIcon(PrevButton1);

    private JButton PrevJButton = new JButton(PrevButton);

    public Rule3() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        // 게임 시작 버튼 생성
        StartJButton.setBounds(1560, 945, 270, 79);
        StartJButton.setBorderPainted(false);
        StartJButton.setContentAreaFilled(false);
        StartJButton.setFocusPainted(false);
        StartJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
//                new EarthPart();    // 게임 시작
//                setVisible(true);
            }
        });
        add(StartJButton);

        // 이전 버튼 생성
        PrevJButton.setBounds(62, 925, 167, 125);
        PrevJButton.setBorderPainted(false);
        PrevJButton.setContentAreaFilled(false);
        PrevJButton.setFocusPainted(false);
        PrevJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                new Rule2();
                setVisible(false);
            }
        });
        add(PrevJButton);
    }

    // 그리는 함수
    public void paint(Graphics g) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
        screenImage = createImage(1920, 1080);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);	// background를 그려줌
    }

    public void screenDraw(Graphics g) {
        g.drawImage(background , 0, 0, null);
        paintComponents(g);
        this.repaint();	// paint 함수로 돌아감
    }
}

public class Rule {
    public static void main(String[] args) {
        new Rule1();
    }
}