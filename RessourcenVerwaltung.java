import java.util.HashMap;
import java.awt.image.*;
import java.io.File;
import javax.imageio.*;

public class RessourcenVerwaltung {
    
    private HashMap<String, BufferedImage> ressourcen;
    
    @Bereit
    public void initialisieren() {
        this.ressourcen = new HashMap<>();
    }
    
    public void ressourceMelden(String bezeichnung, BufferedImage bild) {
        if (ressourcen.get(bezeichnung) != null)
            throw new RuntimeException("Eine Ressource mit der Bezeichnung '" + bezeichnung + "' existiert schon!");
        
        System.out.println("Neue Ressource gemeldet '" + bezeichnung + "'");
            
        ressourcen.put(bezeichnung, bild);
    }
    
    public void ressourceMelden(String bezeichnung, File datei) throws java.io.IOException {
        BufferedImage bild = ImageIO.read(datei);
        ressourceMelden(bezeichnung, bild);
    }
    
    public BufferedImage ressourceHolen(String bezeichnung) {
        BufferedImage ressource = ressourcen.get(bezeichnung);
        
        if (ressource == null)
            throw new RuntimeException("Die Ressource '" + bezeichnung + "' koennte nicht geholt werden!");
            
        return ressource;
    }
    
}