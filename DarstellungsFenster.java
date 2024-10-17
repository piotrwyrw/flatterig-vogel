import javax.swing.*;
import java.awt.Dimension;

public class DarstellungsFenster {
    
    public static final int START_BREITE = 1500;
    public static final int START_HOEHE = 700;
    
    public JFrame rahmen;
    
    @Eingespritzt
    private Darsteller darsteller;
    
    @Eingespritzt
    private TestSzene testSzene;
    
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
        
        darsteller.szeneHinzufuegen("test", testSzene);
        darsteller.szeneWaehlen("test");
        darsteller.darstellungsProzessAnfangen();
    }
    
}