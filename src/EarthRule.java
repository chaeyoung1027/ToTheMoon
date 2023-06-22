import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class EarthRule extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/EarthGameRule1.png")).getImage();

    // 다음 버튼
    private final Image NextButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Earth_arrow.png")).getImage();
    private ImageIcon NextButton = new ImageIcon(NextButtonImage);

    private JButton NextJButton = new JButton(NextButton);

    // 처음으로 버튼
    private final Image HomeButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Earth_home.png")).getImage();
    private ImageIcon HomeButton = new ImageIcon(HomeButtonImage);

    private JButton HomeJButton = new JButton(HomeButton);

    public EarthRule() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        setLayout(null);

        // 다음 버튼 생성
        NextJButton.setBounds(1691, 925, 167, 111);
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
                new EarthRule2();    // 다음 화면으로 넘어가기
                setVisible(false);
            }
        });
        add(NextJButton);

        // 처음으로 버튼 생성
        HomeJButton.setBounds(62, 945, 270, 70);
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
                setVisible(false);
                new ToTheMoon();    // 홈 화면으로 넘어가기
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

class EarthRule2 extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/EarthGameRule2.png")).getImage();

    // 다음 버튼
    private final Image NextButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Earth_arrow.png")).getImage();
    private ImageIcon NextButton = new ImageIcon(NextButtonImage);

    private JButton NextJButton = new JButton(NextButton);

    // 이전 버튼
    private final Image PrevButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Earth_arrow_reverse.png")).getImage();
    private ImageIcon PrevButton = new ImageIcon(PrevButtonImage);

    private JButton PrevJButton = new JButton(PrevButton);

    public EarthRule2() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setLocationRelativeTo(null);

        // 다음 버튼 생성
        NextJButton.setBounds(1691, 925, 167, 111);
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
                new EarthRule3();
                setVisible(false);
            }
        });
        add(NextJButton);

        // 이전 버튼 생성
        PrevJButton.setBounds(62, 925, 167, 111);
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
                new EarthRule();
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

class EarthRule3 extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;

    // 배경 이미지
    private Image background = new ImageIcon(ToTheMoon.class.getResource("img/EarthGameRule3.png")).getImage();

    // 게임 시작 버튼
    private final Image StartButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Earth_gameStart.png")).getImage();
    private ImageIcon StartButton = new ImageIcon(StartButtonImage);

    private JButton StartJButton = new JButton(StartButton);

    // 이전 버튼
    private final Image PrevButtonImage = new ImageIcon(ToTheMoon.class.getResource("img/Earth_arrow_reverse.png")).getImage();
    private ImageIcon PrevButton = new ImageIcon(PrevButtonImage);

    private JButton PrevJButton = new JButton(PrevButton);

    public EarthRule3() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setLocationRelativeTo(null);

        // 게임 시작 버튼 생성
        StartJButton.setBounds(1560, 945, 270, 71);
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
                new EarthPart();    // 게임 시작
                setVisible(false);
            }
        });
        add(StartJButton);

        // 이전 버튼 생성
        PrevJButton.setBounds(62, 925, 167, 111);
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
                new EarthRule2();
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
