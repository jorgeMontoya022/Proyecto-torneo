package co.edu.uniquindio.poo.torneodeportivo;

import static co.edu.uniquindio.poo.util.AssertionUtil.ASSERTION;

public class Estadisticas {
    private final String nombreEquipo;
    private final int perdidos;
    private final int victorias;
    private final int empates;

    public Estadisticas (String nombreEquipo, int perdidos, int victorias, int empates) {
        ASSERTION.assertion(nombreEquipo != null, "El nombre del equipo es necesario: ");
        ASSERTION.assertion(perdidos >=0, "El puntaje del equipo es necesario: ");
        ASSERTION.assertion(victorias >=0, "El puntaje del equipo es necesario: ");
        ASSERTION.assertion(empates >=0, "El puntaje del equipo es necesario: ");

        this.nombreEquipo = nombreEquipo;
        this.perdidos = perdidos;
        this.victorias = victorias;
        this.empates = empates;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public int getPerdidos() {
        return perdidos;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getEmpates() {
        return empates;
    }

    public Object registrarParticipante(Equipo equipo) {
        return null;
    }

}
