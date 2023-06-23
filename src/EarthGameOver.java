import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EarthGameOver extends JFrame {
    private Image screenImage;
    private Image background = new ImageIcon(getClass().getResource("img/EarthGameOver.png")).getImage();
    private Graphics screenGraphic;
    //다시하기 버튼
    private final ImageIcon retryButtonImg = new ImageIcon(getClass().getResource("img/retry.png"));
    private JButton retryButton = new JButton(retryButtonImg);
   //처음으로 버튼
    private final ImageIcon GoMainButtonImg = new ImageIcon(getClass().getResource("img/GoMain.png"));
    private JButton GoMainButton = new JButton( GoMainButtonImg);

    //배경 음악
    private static Clip clip;
    private static String bgmFilePath = "audio/GameOverMusic.wav";

    public EarthGameOver() {
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        setLayout(null);

        playBackgroundMusic(bgmFilePath);

        //다시하기 버튼
        retryButton.setBounds(500, 700, 240, 105);
        retryButton.setBorderPainted(false);
        retryButton.setContentAreaFilled(false);
        retryButton.setFocusPainted(false);
        retryButton.addMouseListener(new MouseAdapter() {
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
                stopBackgroundMusic();
                setVisible(false);
                new EarthPart();    // 다시시작
            }
        });
        add(retryButton);
        //처음으로 버튼
        GoMainButton.setBounds(1200, 700, 240, 105);
        GoMainButton.setBorderPainted(false);
        GoMainButton.setContentAreaFilled(false);
        GoMainButton.setFocusPainted(false);
        GoMainButton.addMouseListener(new MouseAdapter() {
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
                stopBackgroundMusic();
                setVisible(false);
                new ToTheMoon();    // 다시시작
            }
        });
        add(GoMainButton);
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

    public static void playBackgroundMusic(String filePath) {
        try {
            // 배경음악 파일 로딩
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    ToTheMoon.class.getResourceAsStream(filePath));

            // 오디오 포맷 가져오기
            AudioFormat audioFormat = audioInputStream.getFormat();

            // 데이터 라인 정보 생성
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);

            // 데이터 라인 생성
            clip = (Clip) AudioSystem.getLine(info);

            // 데이터 라인 열기
            clip.open(audioInputStream);

            // 무한 반복 재생
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // 재생 시작
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public static void main(String[] args) {
        new EarthGameOver();
    }
}
