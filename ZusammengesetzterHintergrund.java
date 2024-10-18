
import java.util.List;
import java.util.ArrayList;
import java.awt.image.*;
import java.awt.Graphics;

public class ZusammengesetzterHintergrund
{
    private List<Hintergrund> schichten;
    
    private int breite;
    private int hoehe;
    
    public ZusammengesetzterHintergrund(int breite, int hoehe) {
        this.schichten = new ArrayList<>();
        this.breite = breite;
        this.hoehe = hoehe;
    }
    
    public void neueSchicht(BufferedImage ressource, double geschwindigkeit) {
        this.schichten.add(new Hintergrund(ressource, geschwindigkeit, breite, hoehe));
    }
    
    public void zeichnen(Graphics grafik, int breite, int hoehe) {
        this.schichten.reversed().forEach(it -> it.aktualisierenUndZeichnen(breite, hoehe, grafik));
    }
    
}
