public class Initialisierung {
    
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        AbhaengigkeitsInfusion.instanz().abhaengigkeitMelden(Darsteller.class);
        AbhaengigkeitsInfusion.instanz().abhaengigkeitMelden(DarstellungsFenster.class);
        AbhaengigkeitsInfusion.instanz().abhaengigkeitMelden(TestSzene.class);
        AbhaengigkeitsInfusion.instanz().einspritzen();
    }
    
}