import java.util.HashMap;
import java.lang.reflect.*;

public class AbhaengigkeitsInfusion
{
    private static AbhaengigkeitsInfusion infusion = null;
    
    public HashMap<Class<?>, Object> abhaengigkeiten;
    
    public AbhaengigkeitsInfusion() {
        abhaengigkeiten = new HashMap<>();
    }
    
    public static AbhaengigkeitsInfusion instanz() {
        if (infusion == null)
            infusion = new AbhaengigkeitsInfusion();
        return infusion;
    }
    
    public void abhaengigkeitMelden(Class<?> klasse) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Constructor konstruktor = null;
        konstruktor = klasse.getConstructor();
        Object instanz = konstruktor.newInstance();
        abhaengigkeiten.put(klasse, instanz);
        System.out.println("Abhaengigkeit registriert: " + klasse.getSimpleName());
    }
    
    // Zuerst werden alle infusionen durchgefuhrt und erst dann werden die @Bereit-Methoden aufgerufen
    public void einspritzen() throws IllegalAccessException, InvocationTargetException {
        for (Class<?> klasse : abhaengigkeiten.keySet()) {
            Object instanz = abhaengigkeiten.get(klasse);
            klassenInfusion(instanz);
        }
        
        for (Class<?> klasse : abhaengigkeiten.keySet()) {
            Object instanz = abhaengigkeiten.get(klasse);
            bereitMethodeAufrufen(instanz);
        }
    }
    
    public <T> T infusion(Class<T> klasse) {
        return (T) abhaengigkeiten.get(klasse);
    }
    
    public void bereitMethodeAufrufen(Object ziel) throws InvocationTargetException, IllegalAccessException {
        Method[] methoden = ziel.getClass().getDeclaredMethods();
        for (Method method : methoden) {
            if (!method.isAnnotationPresent(Bereit.class))
                continue; // Nix interessant
            method.invoke(ziel);
        }
    }
    
    public void klassenInfusion(Object ziel) throws IllegalAccessException {
        Field[] felder = ziel.getClass().getDeclaredFields();
        for (Field feld : felder) {
            if (!feld.isAnnotationPresent(Eingespritzt.class))
                continue; // Nix interessant
            
            feld.setAccessible(true);
            
            Object i = infusion(feld.getType());
            
            if (i == null)
                throw new RuntimeException("Die infusion des Typs '" + feld.getType().getSimpleName() + "' koennte in der Klasse '" + ziel.getClass().getSimpleName() + "' nicht durchgefuehrt werden!");
            
            feld.set(ziel, infusion(feld.getType()));
            
            System.out.println("Infusion des Typs '" + feld.getType().getSimpleName() + "' in der Klasse '" + ziel.getClass().getSimpleName() + "' erfolgreich durchgefuehrt!");
        }
    }
    
}
