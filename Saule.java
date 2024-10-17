import java.awt.*;

public class Saule {
    
    public static final int BREITE = 50;
    
    public int x;
    public int top;
    
    public Saule(int x, int top) {
        this.x = x;
        this.top = top;
    }
    
    public void zeichnen(Graphics grafik) {
        grafik.setColor(Color.decode("#27ae60"));
        
        // Zeichne den oberen Teil der Saeule
        grafik.fillRect(x, 0, BREITE, top);
        grafik.fillRect(x, top + FlatterigVogel.SAULEN_ABSTAND, BREITE, FlatterigVogel.HOEHE);
    }
    
    public boolean istSichtbar() {
        return x + BREITE > 0;
    }
    
    public static Saule zufallig(int x) {
        return new Saule(x, 100 + (int) (Math.random() * 100));
    }
    
    private boolean rechteckBeinhaltet(int x, int y, int w, int h, int px, int py) {
        return (px >= x && px <= x + w) && (py >= y && py <= y + h);
    }
    
    // Diese Methode schaut nach, ob die wichtigsten punkte in der "Kollisionsdomaene" des Vogels mit einer Saeule kollidieren
    // Die Punkte auf der linken Seite des Vogels werden nicht kontrolliert, da sich der Vogel immer nach rechts bewegt, und eine Kollision
    // mit der linken Rechtecksseite daher nicht moglich ist.
    public boolean tutMitVogelKollidieren(int vogelX, int vogelY) {
        return rechteckBeinhaltet(x, 0, BREITE, top, vogelX + FlatterigVogel.VOGEL_GROESSE / 2, vogelY)
                || rechteckBeinhaltet(x, top + FlatterigVogel.SAULEN_ABSTAND, BREITE, FlatterigVogel.HOEHE, vogelX + FlatterigVogel.VOGEL_GROESSE / 2, vogelY)
                || rechteckBeinhaltet(x, top + FlatterigVogel.SAULEN_ABSTAND, BREITE, FlatterigVogel.HOEHE, vogelX, vogelY + FlatterigVogel.VOGEL_GROESSE / 2)
                || rechteckBeinhaltet(x, top + FlatterigVogel.SAULEN_ABSTAND, BREITE, FlatterigVogel.HOEHE, vogelX, vogelY - FlatterigVogel.VOGEL_GROESSE / 2);
    }
    
    public boolean istVogelInDerPunkteDomaene(int vogelX, int vogelY) {
        return vogelX > x && vogelX < x + BREITE;
    }
    
}