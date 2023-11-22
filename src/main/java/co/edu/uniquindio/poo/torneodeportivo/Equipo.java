/**
 * Registro que agrupa los datos de un Equipo
 * @author Área de programación UQ
 * @since 2023-09
 * 
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */
package co.edu.uniquindio.poo.torneodeportivo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

import static co.edu.uniquindio.poo.util.AssertionUtil.ASSERTION;

public record Equipo(String nombre, Genero genero,Estadisticas estadisticas, Persona representante, Collection<Jugador> jugadores ) implements Participante {
    
    public Equipo {
        ASSERTION.assertion(nombre != null && !nombre.isBlank(), "El nombre es requerido");
        ASSERTION.assertion(representante != null, "El representante es requerido");
        ASSERTION.assertion(genero != null, "El genero es necesario");
        
    }

    public Equipo(String nombre, Genero genero,Estadisticas estadisticas, Persona representante) {
        this(nombre, genero,estadisticas, representante, new LinkedList<>());

    }
    // validar que el genero del jugador sea igual al del equipo
    private void validarGeneroJugador(Jugador jugador){
        ASSERTION.assertion(jugador.GeneroJugador() == genero , "El genero del jugador debe ser igual al genero del equipo");
    }

    /**
     * Permite registrar un jugador en un equipo siempre y cuando no exista ya un
     * jugador registrado en el equipo con el mismo nombre y apellido
     * 
     * @param jugador Jugador que se desea registrar.
     */
    public void registrarJugador(Jugador jugador) {
        validarJugadorExiste(jugador);
        validarGeneroJugador(jugador);
        jugadores.add(jugador);
    }

    /**
     * Permimte buscar un jugador en el equipo basado en su nombre y apellido.
     * 
     * @param jugador Jugador que se desea buscar
     * @return Optional con el jugador que coincida con el nombre y apellido del
     *         jugador buscado,
     *         o Optinal vacío en caso de no encontrar un jugador en el equipo con
     *         dicho nombre y apellido.
     */
    public Optional<Jugador> buscarJugador(Jugador jugador) {
        Predicate<Jugador> nombreIgual = jugador1 -> jugador1.getNombre().equals(jugador.getNombre());
        Predicate<Jugador> apellidoIgual = j -> j.getApellido().equals(jugador.getApellido());
        return jugadores.stream().filter(nombreIgual.and(apellidoIgual)).findAny();

        //Predicate<Jugador> nombreCompletoIgual = j -> j.getNombre().equals(jugador.getNombre()) && j.getApellido().equals(jugador.getApellido());

        //return jugadores.stream().filter(nombreCompletoIgual).findAny();
    }

    /**
     * Valida que no exista ya un jugador registrado con el mismo nombre y apellido,
     * en caso de haberlo genera un assertion error.
     */
    private void validarJugadorExiste(Jugador jugador) {
        boolean existeJugador = buscarJugador(jugador).isPresent();
        ASSERTION.assertion(!existeJugador, "El jugador ya esta registrado");
    }

    @Override
    public String getNombreCompleto() {
        return nombre;
    }

    @Override
    public Object getLicencia() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLicencia'");
    }

    @Override
    public Object getEstadisticas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstadisticas'");
    }


   
}   
