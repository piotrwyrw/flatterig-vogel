
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.*;

public class FlatterigVogel extends JPanel
{
    
    public static int BREITE = 1000;
    public static int HOEHE = 700;
    public static final double ANZIEHUNGSKRAFT = 0.4;
    public static final double BESCHLEUNIGUNG_Y = ANZIEHUNGSKRAFT;
    public static final double MAX_GESCHWINDIGKEIT = 5.0;
    public static final double SPRING_GESCHW = -8.0;
    public static final int VOGEL_GROESSE = 25;
    public static final int PFAD_LAENGE = 10;
    
    public static double GESCHWINDIGKEIT_Y = 0.0;
    public static int SAULEN_ABSTAND = 300;
    
    public GradientPaint backgroundPaint = new GradientPaint(BREITE / 2, HOEHE, Color.decode("#FFFACD"), BREITE / 2, 0, Color.decode("#87CEEB"));
    public boolean punkteDomaene = false;
    public int punkte = 0;
    public int VOGEL_Y = HOEHE / 2;
    public List<Saule> saulen = new ArrayList<>();
    
    public List<Paar<Point, Double>> pfad = new ArrayList<>();
    
    public JFrame frame;
    
    public FlatterigVogel()
    {
        super();
        
        frame = new JFrame("Der Flatterige Vogel");
        frame.setSize(BREITE, HOEHE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(1000, 700));
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        frame.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent evt) {
                
            }
            public void keyPressed(KeyEvent evt) {
                GESCHWINDIGKEIT_Y = SPRING_GESCHW;
            }
            public void keyTyped(KeyEvent evt) {}
        });
        
        AtomicReference<Long> letzteSaule = new AtomicReference<Long>(System.currentTimeMillis());
        
        new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spielAktualisieren();
                repaint();
                
                long now = System.currentTimeMillis();
                if (now - letzteSaule.get() > 250) {
                    letzteSaule.set(now);
                    neueSaule(BREITE);
                    
                }
            }
        }).start();
    }
    
    public void startNeuDurchfuhren() {
        VOGEL_Y = HOEHE / 2;
        GESCHWINDIGKEIT_Y = 0;
        this.saulen.clear();
        this.pfad.clear();
        this.punkteDomaene = false;
        this.punkte = 0;
    }
    
    public void neueSaule(int x) {
        for (int i = 0; i < saulen.size(); i ++) {
            if (!this.saulen.get(i).istSichtbar())
                this.saulen.remove(i);
        }
        this.saulen.add(Saule.zufallig(x));
    }
    
    public void spielAktualisieren() {
        // "Responsive Design" lol
        this.BREITE = this.frame.getWidth();
        this.HOEHE = this.frame.getHeight();
        
        this.GESCHWINDIGKEIT_Y += this.BESCHLEUNIGUNG_Y;
        
        if (this.GESCHWINDIGKEIT_Y > this.MAX_GESCHWINDIGKEIT) {
            this.GESCHWINDIGKEIT_Y = this.MAX_GESCHWINDIGKEIT;
        }
        
        this.VOGEL_Y += this.GESCHWINDIGKEIT_Y;
        
        if (VOGEL_Y > HOEHE || VOGEL_Y < 0)
            startNeuDurchfuhren();
        
        boolean punkte = false;
            
        for (int i = 0; i < this.saulen.size(); i ++) {
            Saule it = this.saulen.get(i);
            if (it.tutMitVogelKollidieren(BREITE / 2, VOGEL_Y)) {
                startNeuDurchfuhren();
            }
            if (it.istVogelInDerPunkteDomaene(BREITE / 2, VOGEL_Y))
                punkte = true;
        }
        
        boolean altPunkte = this.punkteDomaene;
        this.punkteDomaene = punkte;
        
        // "Falling edge" Detektor
        if (altPunkte && !punkte)
            this.punkte ++;
    }
    
    public void zeichnen(Graphics grafik) {
        Graphics2D zweiDimensional = (Graphics2D) grafik;
        zweiDimensional.setPaint(backgroundPaint);
        grafik.fillRect(0, 0, BREITE, HOEHE);
        
        zweiDimensional.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        // Hier wird der Vogel gezeichnet
        // grafik.setColor(Color.decode("#f1c40f"));
        // grafik.fillRect(BREITE / 2 - VOGEL_GROESSE / 2, VOGEL_Y - VOGEL_GROESSE / 2, VOGEL_GROESSE, VOGEL_GROESSE);
        
        for (int i = 0; i < this.saulen.size(); i ++) {
            Saule it = this.saulen.get(i);
            it.zeichnen(grafik);
            it.x -= 5;
        }
        
        grafik.setColor(Color.BLACK);
        grafik.setFont(new Font("EB Garamond", Font.PLAIN, 60));
        grafik.drawString(String.valueOf(punkte), BREITE - grafik.getFontMetrics().stringWidth(String.valueOf(punkte)) - 70, 70);
        
        // Pfad berechnen und zeichnen
        this.pfad.add(new Paar(new Point(BREITE / 2, VOGEL_Y), 255.0));
        
        Color originaleFarbe = Color.decode("#a29bfe");

        for (int i = 0; i < this.pfad.size(); i ++) {
            this.pfad.get(i).value -= 4;
            if (this.pfad.get(i).value <= 0) {
                this.pfad.remove(i);
            }
            Point it = this.pfad.get(i).key;
            it.x -= 5;
            
            grafik.setColor(new Color(originaleFarbe.getRed(), originaleFarbe.getGreen(), originaleFarbe.getBlue(), (this.pfad.get(i).value).intValue()));
            
            double elementGroesse = this.pfad.get(i).value / 9;
            
            grafik.fillOval(it.x, it.y, (int) elementGroesse, (int) elementGroesse);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        zeichnen(g);
    }
    
}
