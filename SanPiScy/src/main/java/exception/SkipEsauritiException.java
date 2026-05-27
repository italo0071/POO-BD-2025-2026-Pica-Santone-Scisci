package exception;

public class SkipEsauritiException extends Exception {

    public SkipEsauritiException(String dettaglioAnomaliaBloccante) {
        super(dettaglioAnomaliaBloccante);
    }
}