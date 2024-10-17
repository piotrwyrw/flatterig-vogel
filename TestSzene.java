import java.awt.*;

public class TestSzene extends Szene {
    
    @Eingespritzt
    private DarstellungsFenster fenster;
    
    public void anfangen() {}
    public boolean sollSchliessen() {
        return false;
    }
    public void aktualisieren() {}
    public void zeichnen(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, fenster.rahmen.getWidth(), fenster.rahmen.getHeight());
    }
    public void tasteRunter() {}
    public void beenden() {}
}