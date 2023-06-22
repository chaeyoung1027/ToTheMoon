import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RankingInsert extends JFrame {
    private Image screenImage;
    private Graphics screenGraphic;
    private Image background = new ImageIcon(getClass().getResource("img/RankingInsertBackground.png")).getImage();

    private JTextField nameField;
    private JButton okButton;

    public static int score;

    public RankingInsert(int score) {
        this.score = score;
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        setLayout(null);

        nameField = new JTextField();
        nameField.setBorder(null);
        nameField.setBounds(900, 383, 340, 50);
        nameField.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
        nameField.setFont(nameField.getFont().deriveFont(Font.PLAIN));
        add(nameField);

        okButton = new JButton(new ImageIcon(getClass().getResource("img/RankingOKButton.png")));
        okButton.setBounds(885, 630, 150, 88);
        okButton.setBorder(null);
        okButton.setContentAreaFilled(false);
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();

                ToTheMoonDB.insertOrUpdateScore(name, score);

                // Open the Ranking page and close the RankingInsert page
                ToTheMoonDB.loadRanking();  // Refresh the ranking
                dispose();
                setVisible(false);
            }
        });
        add(okButton);
    }

    public void paint(Graphics g) {
        screenImage = createImage(1920, 1080);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
        g.setFont(new Font("맑은 고딕", Font.PLAIN, 50));
        g.setColor(Color.black);
        g.drawString(Integer.toString(score), 930, 570);
    }

    public void screenDraw(Graphics g) {
        g.drawImage(background, 0, 0, null);
        paintComponents(g);
        this.repaint();
    }

    public static void main(String[] args) {
        new RankingInsert(300);
    }
}
