import java.awt.Graphics;

public abstract class Szene {
    public abstract void anfangen();
    public abstract boolean sollSchliessen();
    public abstract void aktualisieren();
    public abstract void zeichnen(Graphics g);
    public abstract void beenden();
    public abstract void tasteRunter();
}