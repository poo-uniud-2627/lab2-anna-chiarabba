// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 5, variante 1: la nebbia. I contratti ci sono, i campi e i corpi dei metodi li scrivete voi.
package nebbia;

import geometria.Posizione;
import geometria.Rettangolo;

/**
 * Una zona del campo in cui la palla non si vede: quando ci entra sparisce, quando esce ricompare.
 * Gli ostacoli e le racchette nella nebbia si vedono. La nebbia non cambia mai.
 * Quale nebbia c'è in un livello lo decide il MotoreDeiLivelli.
 */
public class Nebbia {
    private Rettangolo zonaNebbia;
    private static boolean isNebbia = false;

    // costruttore
    private Nebbia(Rettangolo zonaNebbia) {
        this.zonaNebbia = zonaNebbia;
    }
    /** Nessuna nebbia: non nasconde niente. */
    public static Nebbia nessuna() {
        isNebbia = false;
        return new Nebbia(null);
    }

    /**
     * La striscia al centro di un campo largo larghezzaCampo e alto altezzaCampo pixel:
     * alta come il campo, larga frazione della larghezza del campo, con il centro al centro del campo.
     * Con frazione 0 è nessuna().
     * Lancia IllegalArgumentException se larghezzaCampo o altezzaCampo non sono positivi,
     * o se frazione non è fra 0 (compreso) e 1 (escluso).
     */
    public static Nebbia alCentro(int larghezzaCampo, int altezzaCampo, double frazione) {
        if (larghezzaCampo <= 0) {
            throw new IllegalArgumentException(
                "Larghezza campo minore o uguale a 0"
            );
        }
        if (altezzaCampo <= 0) {
            throw new IllegalArgumentException(
                "Altezza campo minore o uguale a 0"
            );
        }
        if (frazione < 0 || frazione >= 1) {
            throw new IllegalArgumentException(
                "Frazione non è compresa fra 0 e 1"
            );
        }
        if (frazione == 0) {
            return nessuna();
        }
        
        double larghezzaStriscia = Math.round(frazione * larghezzaCampo);
        double xStriscia = (larghezzaCampo - larghezzaStriscia) / 2;

        Rettangolo zonaNebbia = new Rettangolo(xStriscia, 0, larghezzaStriscia, altezzaCampo);
        isNebbia = true;
        return new Nebbia(zonaNebbia);
    }

    /** Vero se la nebbia c'è. */
    public boolean isPresente() {
        return isNebbia;
    }

    /** La zona coperta dalla nebbia. Pretende: isPresente(). Lancia IllegalStateException se la nebbia non c'è. */
    public Rettangolo getZona() {
        if (!isPresente()) {
            throw new IllegalStateException(
                "Non c'è la nebbia"
            );
        }

        return zonaNebbia;
    }

    /**
     * Vero se un punto (per esempio il centro della palla) è nella nebbia, bordi compresi.
     * Promette: falso se la nebbia non c'è.
     * Lancia IllegalArgumentException se punto è null.
     */
    public boolean nasconde(Posizione punto) {
        if (punto == null) {
            throw new IllegalArgumentException(
                "Punto è null"
            );
        }

        if (!isPresente()) {
            return false;
        }

        double x = punto.x();
        double y = punto.y();

        return x >= zonaNebbia.x()
            && x <= zonaNebbia.x() + zonaNebbia.larghezza()
            && y >= zonaNebbia.y()
            && y <= zonaNebbia.y() + zonaNebbia.altezza();
    }
}
