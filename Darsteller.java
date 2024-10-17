import java.util.Map;
import java.util.HashMap;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
        new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gewaehlteSzene == null)
                    return;
                        
                if (gewaehlteSzene.sollSchliessen()) {
                    gewaehlteSzene.beenden();
                    System.exit(0);
                }
                    
                gewaehlteSzene.aktualisieren();
                repaint();
            }
        }).start();
    }
    
    public void tastenEreignis(KeyEvent evt) {
        if (gewaehlteSzene == null)
            return;
            
        gewaehlteSzene.tasteRunter();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (this.gewaehlteSzene != null)
            this.gewaehlteSzene.zeichnen(g);
    }
    
}