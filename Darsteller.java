import java.util.Map;
import java.util.HashMap;
import java.awt.*;
import javax.swing.*;

public class Darsteller extends JPanel {
    
    public HashMap<String, Szene> szenen;
    
    public Szene gewaehlteSzene;
    
    @Bereit
    public void initialisieren() {
        this.szenen = new HashMap<>();
        this.gewaehlteSzene = null;
    }
    
    public void szeneHinzufuegen(String bezeichnung, Szene instanz) {
        if (instanz == null)
            throw new RuntimeException("Die Szene muss existieren um eingefugt zu werden!");
        
        this.szenen.put(bezeichnung, instanz);
    }
    
    public Szene holeSzene(String bezeichnung) {
        return szenen.get(bezeichnung);
    }
    
    public void szeneWaehlen(String bezeichnung) {
        Szene sz = holeSzene(bezeichnung);
        
        if (sz == null)
            throw new RuntimeException("Die angefragte Szene koennte nicht geholt werden!!");
        
        if (this.gewaehlteSzene != null)
            this.gewaehlteSzene.beenden();
        
        this.gewaehlteSzene = sz;
        this.gewaehlteSzene.anfangen();
    }
    
    public void darstellungsProzessAnfangen() {
        new Thread() {
            public void run() {
                while (true) {
                    if (gewaehlteSzene == null)
                        continue;
                        
                    if (gewaehlteSzene.sollSchliessen()) {
                        gewaehlteSzene.beenden();
                        System.exit(0);
                    }
                        
                    gewaehlteSzene.aktualisieren();
                    repaint();
                }
            }
        };
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (this.gewaehlteSzene != null)
            this.gewaehlteSzene.zeichnen(g);
    }
    
}