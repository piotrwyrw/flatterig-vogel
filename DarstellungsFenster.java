import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;

public class DarstellungsFenster {
    
    public static final int START_BREITE = 1500;
    public static final int START_HOEHE = 900;
    
    public JFrame rahmen;
    
    @Eingespritzt
    private Darsteller darsteller;
    
    @Eingespritzt
    private TestSzene testSzene;
    
    @Eingespritzt
    private FlatterigVogel vogelSzene;
    
    @Bereit
    public void initialisieren() {
        rahmen = new JFrame("Der Flatternde Vogel");
        rahmen.setSize(START_BREITE, START_HOEHE);
        rahmen.setMinimumSize(new Dimension(START_BREITE, START_HOEHE));
        rahmen.setResizable(true);
        rahmen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rahmen.setLocationRelativeTo(null);
        rahmen.add(darsteller);
        rahmen.setVisible(true);
        
        rahmen.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent evt) {}
            public void keyPressed(KeyEvent evt) {
                darsteller.tastenEreignis(evt);
            }
            public void keyTyped(KeyEvent evt) {}
        });
        
        darsteller.szeneHinzufuegen("vogel", vogelSzene);
        darsteller.szeneWaehlen("vogel");
        darsteller.darstellungsProzessAnfangen();
    }
    
}