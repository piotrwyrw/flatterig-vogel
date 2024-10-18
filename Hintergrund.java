import java.awt.image.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics;

public class Hintergrund {
    
    private BufferedImage originalBild;
    private double geschwindigkeit;
    
    private int fensterBreite;
    private int fensterHoehe;
    
    private List<Paar<Double, BufferedImage>> instanzen;
    
    public Hintergrund(BufferedImage ressource, double geschwindigkeit, int breite, int hoehe) {
        this.originalBild = ressource;
        this.geschwindigkeit = geschwindigkeit;
        this.instanzen = new ArrayList<>();
        
        this.fensterBreite = breite;
        this.fensterHoehe = hoehe;
    }
    
    public void aktualisieren(int breite, int hoehe) {
        this.fensterBreite = breite;
        this.fensterHoehe = hoehe;
        
        if (this.instanzen.size() < 2) {
            instanzen.add(new Paar<>(0.0, originalBild));
            instanzen.add(new Paar<>((double) this.fensterBreite, originalBild));
        }
        
        for (int i = 0; i < instanzen.size(); i ++) {
            Paar<Double, BufferedImage> instanz = instanzen.get(i);
            instanz.key += this.geschwindigkeit;
            
            // Unsichtbare Bilder sollen entfernt und mit neuen ersertzt werden!
            if (instanz.key + breite < 0) {
                instanzen.add(new Paar<>((double) this.fensterBreite, originalBild));
                instanzen.remove(i);
            }
        }
    }
    
    public void zeichnen(Graphics grafik) {
        for (int i = 0; i < instanzen.size(); i ++) {
            Paar<Double, BufferedImage> instanz = instanzen.get(i);
            grafik.drawImage(instanz.value, instanz.key.intValue(), 0, fensterBreite, fensterHoehe, null);
        }
    }
    
    public void aktualisierenUndZeichnen(int breite, int hoehe, Graphics grafik) {
        aktualisieren(breite, hoehe);
        zeichnen(grafik);
    }
    
}