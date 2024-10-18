import java.io.File;

public class RessourcenMelder {
    
    @Eingespritzt
    private RessourcenVerwaltung ressourcen;
    
    @Bereit
    public void melden() throws java.io.IOException {
        ressourcen.ressourceMelden("himmel", new File("ressourcen/himmel.png"));
    }
    
}