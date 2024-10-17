public class Initialisierung {
    
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        AbhaengigkeitsInfusion.instanz().abhaengigkeitMelden(Darsteller.class);
        AbhaengigkeitsInfusion.instanz().abhaengigkeitMelden(DarstellungsFenster.class);
        AbhaengigkeitsInfusion.instanz().abhaengigkeitMelden(TestSzene.class);
        AbhaengigkeitsInfusion.instanz().abhaengigkeitMelden(FlatterigVogel.class);
        AbhaengigkeitsInfusion.instanz().einspritzen(new Class<?>[] {
            Darsteller.class,           // } Der Darsteller muss aufgrund von circular-dependencies VOR dem DarstellungsFenster
            DarstellungsFenster.class,  // } eingespritzt und initialisiert werden!
            TestSzene.class,
            FlatterigVogel.class
        });
    }
    
}