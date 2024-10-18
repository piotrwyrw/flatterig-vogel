
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.*;
import java.awt.image.*;

public class FlatterigVogel extends Szene
{
    public static int BREITE = 1000;
    public static int HOEHE = 700;
    public static final double ANZIEHUNGSKRAFT = 0.4;
    public static final double BESCHLEUNIGUNG_Y = ANZIEHUNGSKRAFT;
    public static final double MAX_GESCHWINDIGKEIT = 50000.0;
    public static final double SPRING_GESCHW = -8.0;
    public static final int VOGEL_GROESSE = 25;
    public static final int PFAD_LAENGE = 10;
    public static final int SAULEN_ABSTAND = 300;
    public static final int SAULEN_ABST_ZUFAELLIGKEIT = -50;
    public static final Color PFAD_FARBE = Color.decode("#778beb");
    
    public static double GESCHWINDIGKEIT_Y = 0.0;
    
    public static int ORIGINAL_GESCHWINDIGKEIT = 10;
    
    public static int SPEED = ORIGINAL_GESCHWINDIGKEIT;
    
    public boolean punkteDomaene = false;
    public int punkte = 0;
    public int VOGEL_Y = HOEHE / 2;
    public List<Saule> saulen = new ArrayList<>();
    
    public boolean verloren = false;
    
    public List<Paar<Point, Double>> pfad = new ArrayList<>();
    
    public long letzteSauleZeit;
    
    @Eingespritzt
    public DarstellungsFenster fenster;
    
    @Eingespritzt
    public RessourcenVerwaltung ressourcen;
        
    public ZusammengesetzterHintergrund hintergrund;
    
    @Bereit
    public void initialisieren() {
        letzteSauleZeit = System.currentTimeMillis();
        hintergrund = new ZusammengesetzterHintergrund(BREITE, HOEHE);
        hintergrund.neueSchicht(ressourcen.ressourceHolen("bg1"), -this.SPEED);
        hintergrund.neueSchicht(ressourcen.ressourceHolen("bg2"), -this.SPEED/2);
        hintergrund.neueSchicht(ressourcen.ressourceHolen("bg3"), -this.SPEED/3);
        hintergrund.neueSchicht(ressourcen.ressourceHolen("bg4"), -this.SPEED/4);
        hintergrund.neueSchicht(ressourcen.ressourceHolen("bg5"), -this.SPEED/5);
        hintergrund.neueSchicht(ressourcen.ressourceHolen("bg6"), -this.SPEED/6);
    }
    
    public void anfangen() {}
    
    public boolean sollSchliessen() {
        return false;
    }
    
    public void vielleichtSaulenGenerieren() {
        long jetzt = System.currentTimeMillis();
        if (jetzt - letzteSauleZeit > 1500 - Math.random() * 200) {
            letzteSauleZeit = jetzt;
            neueSaule(BREITE);
        }
    }
    
    public void neuStart() {
        VOGEL_Y = HOEHE / 2;
        GESCHWINDIGKEIT_Y = 0;
        this.saulen.clear();
        this.pfad.clear();
        this.punkteDomaene = false;
        this.punkte = 0;
        this.verloren = false;
        this.SPEED = ORIGINAL_GESCHWINDIGKEIT;
    }
    
    public void aktualisieren() {
        vielleichtSaulenGenerieren();
        
        JFrame rahmen = fenster.rahmen;
        
        // Fenstergroessenempfindliches Design
        this.BREITE = rahmen.getWidth();
        this.HOEHE = rahmen.getHeight();
        
        this.GESCHWINDIGKEIT_Y += this.BESCHLEUNIGUNG_Y;
        
        if (this.GESCHWINDIGKEIT_Y > this.MAX_GESCHWINDIGKEIT) {
            this.GESCHWINDIGKEIT_Y = this.MAX_GESCHWINDIGKEIT;
        }
        
        this.VOGEL_Y += this.GESCHWINDIGKEIT_Y;
        
        if (VOGEL_Y > HOEHE || VOGEL_Y < 0) {
            neuStart();
        }
        
        boolean punkte = false;
            
        for (int i = 0; i < this.saulen.size(); i ++) {
            Saule it = this.saulen.get(i);
            if (it.tutMitVogelKollidieren(BREITE / 2, VOGEL_Y)) {
                this.SPEED = -5;
                this.verloren = true;
            }
            if (it.istVogelInDerPunkteDomaene(BREITE / 2, VOGEL_Y))
                punkte = true;
        }
        
        boolean altPunkte = this.punkteDomaene;
        this.punkteDomaene = punkte;
        
        // Ein Detektor der fallenden Kanten
        if (altPunkte && !punkte)
            this.punkte ++;
    }
    
    public void tasteRunter() {
        if (verloren)
            return;
        
        this.GESCHWINDIGKEIT_Y = this.SPRING_GESCHW;
    }
    
    public void zeichnen(Graphics grafik) {
        hintergrund.zeichnen(grafik, BREITE, HOEHE);
        
        for (int i = 0; i < this.saulen.size(); i ++) {
            Saule it = this.saulen.get(i);
            it.zeichnen(grafik);
            it.x -= SPEED;
        }
        
        Graphics2D zweiDimensional = (Graphics2D) grafik;
        zweiDimensional.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        // Pfad berechnen und zeichnen
        this.pfad.add(new Paar(new Point(BREITE / 2 - VOGEL_GROESSE / 2, VOGEL_Y - VOGEL_GROESSE / 2), 255.0));
        
        Color originaleFarbe = PFAD_FARBE;

        for (int i = 0; i < this.pfad.size(); i ++) {
            this.pfad.get(i).value -= 6;
            
            if (this.pfad.get(i).value <= 0) {
                this.pfad.remove(i);
                continue;
            }
            
            Point it = this.pfad.get(i).key;
            it.x -= SPEED;
            
            grafik.setColor(new Color(originaleFarbe.getRed(), originaleFarbe.getGreen(), originaleFarbe.getBlue(), (this.pfad.get(i).value).intValue()));
            
            double elementGroesse = this.pfad.get(i).value / 9;
            
            grafik.fillOval(it.x, it.y, (int) elementGroesse, (int) elementGroesse);
        }
        
        // Hier wird der Vogel gezeichnet
        // grafik.setColor(Color.RED);
        // grafik.drawRect(BREITE / 2 - VOGEL_GROESSE / 2, VOGEL_Y - VOGEL_GROESSE / 2, VOGEL_GROESSE, VOGEL_GROESSE);
        
        grafik.setColor(Color.BLACK);
        grafik.setFont(new Font("EB Garamond", Font.PLAIN, 60));
        grafik.drawString(String.valueOf(punkte), BREITE - grafik.getFontMetrics().stringWidth(String.valueOf(punkte)) - 70, 70);
    }
    
    public void beenden() {}

    public void neueSaule(int x) {
        for (int i = 0; i < saulen.size(); i ++) {
            // Unsichtbare Elemente werden entfernt
            if (!this.saulen.get(i).istSichtbar())
                this.saulen.remove(i);
        }
        this.saulen.add(Saule.zufallig(x));
    }
    
}
