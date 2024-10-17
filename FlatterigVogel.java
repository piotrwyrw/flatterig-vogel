
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.*;

public class FlatterigVogel extends JPanel
{
    
    public static final int BREITE = 1000;
    public static final int HOEHE = 700;
    
    public static final double ANZIEHUNGSKRAFT = 0.4;
    public static final double BESCHLEUNIGUNG_Y = ANZIEHUNGSKRAFT;
    public static final double MAX_GESCHWINDIGKEIT = 5.0;
    public static final double SPRING_GESCHW = -10.0;
    public static double GESCHWINDIGKEIT_Y = 0.0;
    public static int SAULEN_ABSTAND = 300;
    
    public boolean punkteDomaene = false;
    
    public int punkte = 0;
    
    public static final int VOGEL_GROESSE = 25;
    public int VOGEL_Y = HOEHE / 2;
    
    public List<Saule> saulen = new ArrayList<>();
    
    public FlatterigVogel()
    {
        super();
        
        JFrame frame = new JFrame("Der Flatterige Vogel");
        frame.setSize(BREITE, HOEHE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        frame.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent evt) {
                GESCHWINDIGKEIT_Y = SPRING_GESCHW;
            }
            public void keyPressed(KeyEvent evt) {}
            public void keyTyped(KeyEvent evt) {}
        });
        
        AtomicReference<Long> letzteSaule = new AtomicReference<Long>(System.currentTimeMillis());
        
        new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spielAktualisieren();
                repaint();
                
                long now = System.currentTimeMillis();
                if (now - letzteSaule.get() > 2500) {
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
    }
    
    public void neueSaule(int x) {
        for (int i = 0; i < saulen.size(); i ++) {
            if (!this.saulen.get(i).istSichtbar())
                this.saulen.remove(i);
        }
        this.saulen.add(Saule.zufallig(x));
    }
    
    public void spielAktualisieren() {
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
        grafik.setColor(Color.decode("#1b1b1b"));
        grafik.fillRect(0, 0, BREITE, HOEHE);
        
        grafik.setColor(Color.decode("#f1c40f"));
        grafik.fillRect(BREITE / 2 - VOGEL_GROESSE / 2, VOGEL_Y - VOGEL_GROESSE / 2, VOGEL_GROESSE, VOGEL_GROESSE);
        
        for (int i = 0; i < this.saulen.size(); i ++) {
            Saule it = this.saulen.get(i);
            it.zeichnen(grafik);
            it.x -= 5;
        }
        
        grafik.setColor(Color.decode("#ecf0f1"));
        grafik.setFont(new Font("EB Garamond", Font.PLAIN, 60));
        grafik.drawString(String.valueOf(punkte), BREITE - 70, 70);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        zeichnen(g);
    }
    
}
