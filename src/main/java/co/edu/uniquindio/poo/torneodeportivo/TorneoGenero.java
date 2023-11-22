package co.edu.uniquindio.poo.torneodeportivo;

import java.util.function.BooleanSupplier;

public enum TorneoGenero {
    FEMENINO,
    MASCULINO,
    MIXTO;

    public BooleanSupplier contains(Jugador jugadorFemenino) {
        return null;
    }
}
