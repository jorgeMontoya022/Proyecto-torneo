package co.edu.uniquindio.poo.torneodeportivo;

public class Juez extends Persona {
    private final String licencia;

    public Juez(String nombre, String apellido, String email, String celular, String licencia) {
        super(nombre, apellido, email, celular);
        this.licencia = licencia;
    }

    public String getLicencia() {
        return licencia;
    }

}