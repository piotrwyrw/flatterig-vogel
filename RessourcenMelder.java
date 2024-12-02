import java.io.File;

public class RessourcenMelder {
    
    @Eingespritzt
    private RessourcenVerwaltung ressourcen;
    
    @Bereit
    public void melden() throws java.io.IOException {
        ressourcen.ressourceMelden("bg1", new File("ressourcen/hintergrund/1.png"));
        ressourcen.ressourceMelden("bg2", new File("ressourcen/hintergrund/2.png"));
        ressourcen.ressourceMelden("bg3", new File("ressourcen/hintergrund/3.png"));
        ressourcen.ressourceMelden("bg4", new File("ressourcen/hintergrund/4.png"));
        ressourcen.ressourceMelden("bg5", new File("ressourcen/hintergrund/5.png"));
        ressourcen.ressourceMelden("bg6", new File("ressourcen/hintergrund/6.png"));
    }
    
}