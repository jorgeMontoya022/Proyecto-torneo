/**
 * Clase que agrupa los datos de un Torneo
 * @author Área de programación UQ
 * @since 2023-08
 * 
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */
package co.edu.uniquindio.poo.torneodeportivo;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static co.edu.uniquindio.poo.util.AssertionUtil.ASSERTION;

public class Torneo {
    private final String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaInicioInscripciones;
    private LocalDate fechaCierreInscripciones;
    private final byte numeroParticipantes;
    private final byte limiteEdad;
    private final int valorInscripcion;
    private final TipoTorneo tipoTorneo;
    private final Collection<Participante> participantes;
    private final CaracterTorneo caracter;
    private final TorneoGenero torneoGenero;
    private final Collection<Juez> jueces;
    private final Collection<Enfrentamiento> enfrentamientos;
    private final Collection <Equipo> equipos = new LinkedList<>();

    public Torneo(String nombre, LocalDate fechaInicio,
            LocalDate fechaInicioInscripciones,
            LocalDate fechaCierreInscripciones, byte numeroParticipantes,
            byte limiteEdad, int valorInscripcion, TipoTorneo tipoTorneo, CaracterTorneo caracter, TorneoGenero torneoGenero) {

        ASSERTION.assertion(nombre != null, "El nombre es requerido");
        ASSERTION.assertion(torneoGenero !=null, "el genero del torneo es necesario");

        ASSERTION.assertion(numeroParticipantes >= 0, "El número de participantes no puede ser negativo");
        ASSERTION.assertion(limiteEdad >= 0, "El limite de edad no puede ser negativo");
        ASSERTION.assertion(valorInscripcion >= 0, "El valor de la inscripción no puede ser negativo");

        this.nombre = nombre;

        setFechaInicioInscripciones(fechaInicioInscripciones);
        setFechaCierreInscripciones(fechaCierreInscripciones);
        setFechaInicio(fechaInicio);
        this.numeroParticipantes = numeroParticipantes;
        this.limiteEdad = limiteEdad;
        this.valorInscripcion = valorInscripcion;
        this.tipoTorneo = tipoTorneo;
        this.participantes = new LinkedList<>();
        this.caracter = Objects.requireNonNull(caracter, "El carácter del torneo es requerido");
        this.torneoGenero = torneoGenero;
        this.jueces = new LinkedList<>();
        this.enfrentamientos = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaInicioInscripciones() {
        return fechaInicioInscripciones;
    }

    public LocalDate getFechaCierreInscripciones() {
        return fechaCierreInscripciones;
    }

    public byte getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public byte getLimiteEdad() {
        return limiteEdad;
    }

    public int getValorInscripcion() {
        return valorInscripcion;
    }

    public TipoTorneo getTipoTorneo() {
        return tipoTorneo;
    }

    public CaracterTorneo getCaracter() {
        return caracter;
    }

    public TorneoGenero getTorneoGenero() {
        return torneoGenero;
    }

    public Collection<Juez> getJueces() {
        return Collections.unmodifiableCollection(jueces);
    }

    public Collection<Equipo> getEquipos(){
        return Collections.unmodifiableCollection(equipos);
    }

    public Collection<Enfrentamiento> getEnfrentamientos() {
        return Collections.unmodifiableCollection(enfrentamientos);
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        ASSERTION.assertion(fechaInicio != null, "La fecha de inicio es requerida");
        ASSERTION.assertion((fechaInicioInscripciones == null || fechaInicio.isAfter(fechaInicioInscripciones)) &&
                (fechaCierreInscripciones == null || fechaInicio.isAfter(fechaCierreInscripciones)),
                "La fecha de inicio no es válida");
        this.fechaInicio = fechaInicio;
    }

    public void setFechaInicioInscripciones(LocalDate fechaInicioInscripciones) {
        ASSERTION.assertion(fechaInicioInscripciones != null, "La fecha de inicio de inscripciones es requerida");
        this.fechaInicioInscripciones = fechaInicioInscripciones;
    }

    public void setFechaCierreInscripciones(LocalDate fechaCierreInscripciones) {
        ASSERTION.assertion(fechaCierreInscripciones != null, "La fecha de cierre es requerida");
        ASSERTION.assertion(fechaCierreInscripciones.isAfter(fechaInicioInscripciones),
                "La fecha de cierre de inscripciones debe ser posterior a la fecha de inicio de inscripciones");
        this.fechaCierreInscripciones = fechaCierreInscripciones;
    }

    /**
     * Permite registrar un participante en el torneo
     * 
     * @param participante Participante a ser registrado
     * @throws Se genera un error si ya existe un equipo registrado con el mismo
     *            nombre, o en caso de que las inscripciones del torneo no estén
     *            abiertas.
     */
    public void registrarParticipante(Participante participante) {
        validarParticipanteExiste(participante);

        validarInscripciopnesAbiertas();
        validarCaracter(participante);

        participantes.add(participante);
    }

    /**
     * Valida que el participante sea acorde con el carácter del torneo.
     * 
     * @param participante Participante a ser registrado
     */
    private void validarCaracter(Participante participante) {
        ASSERTION.assertion(caracter.esValido(participante), "Las inscripciones no están abiertas");
    }

    /**
     * Valida que las inscripciones del torneo esten abiertas, en caso de no estarlo
     * genera un assertion error.
     */
    private void validarInscripciopnesAbiertas() {
        boolean inscripcionAbierta = fechaInicioInscripciones.isBefore(LocalDate.now())
                && fechaCierreInscripciones.isAfter(LocalDate.now());
        ASSERTION.assertion(inscripcionAbierta, "Las inscripciones no están abiertas");
    }

    /**
     * Valida que no exista ya un equipo registrado con el mismo nombre, en caso de
     * haberlo genera un assertion error.
     */
    private void validarParticipanteExiste(Participante participante) {
        boolean existeEquipo = buscarParticipantePorNombre(participante.getNombreCompleto()).isPresent();
        ASSERTION.assertion(!existeEquipo, "El equipo ya esta registrado");
    }

    /**
     * Permite obtener una copia no modificable de la lista de los participantes
     * registrados.
     * 
     * @return Collection<Participante> no modificable de los participantes
     *         registrados en el torneo.
     */
    public Collection<Participante> getParticipantes() {
        return Collections.unmodifiableCollection(participantes);
    }

    /**
     * Permite buscar un participante por su nombre entre los participantes
     * registrados en el torneo
     * 
     * @param nombre Nombre del participante que se está buscando
     * @return Un Optional<Participante> con el participante cuyo nombre sea igual
     *         al nombre buscado, o un Optional vacío en caso de no encontrar un
     *         participante con nombre igual al dado.
     */
    public Optional<Participante> buscarParticipantePorNombre(String nombre) {
        Predicate<Participante> condicion = participante -> participante.getNombreCompleto().equals(nombre);
        return participantes.stream().filter(condicion).findAny();
    }

    /**
     * Permite registrar un jugador en el equipo siempre y cuando este dentro de las
     * fechas validas de registro,
     * no exista ya un jugador registrado con el mismo nombre y apellido y el
     * jugador no exceda el limite de edad del torneo.
     * 
     * @param nombre  Nombre del equipo en que se desea registrar el jugador
     * @param jugador Jugador que se desea registrar.
     */
    public void registrarJugador(String nombre, Jugador jugador) {
        
        var participante = buscarParticipantePorNombre(nombre);

        participante.ifPresent((e) -> {
            if (e instanceof Equipo equipo) {
                registrarJugador(equipo, jugador);
            }
        });
    }

    /**
     * Permite registrar un jugador en el equipo siempre y cuando este dentro de las
     * fechas validas de registro,
     * no exista ya un jugador registrado con el mismo nombre y apellido y el
     * jugador no exceda el limite de edad del torneo.
     * 
     * @param equipo  Equipo en el que se desea registrar el jugador.
     * @param jugador Jugador que se desea registrar.
     */
    public void registrarJugador(Equipo equipo, Jugador jugador) {
        ASSERTION.assertion(!LocalDate.now().isAfter(fechaCierreInscripciones),
                "No se pueden registrar jugadores después del a fecha de cierre de inscripciones");
        validarLimiteEdadJugador(jugador);
        validarJugadorExiste(jugador);
        equipo.registrarJugador(jugador);
    }

    /**
     * Permite buscar un jugador basado en su nombre y apellido en todos los equipos
     * registrados en el torneo.
     * 
     * @param jugador Jugador que se desea buscar
     * @return Optional con el jugador encontrado o un optional vacío en caso de no
     *         haber encontrado un jugador con el nombre y apellido del jugador
     *         buscado.
     */
    public Optional<Jugador> buscarJugador(Jugador jugador) {
        return participantes.stream()
                .filter(p -> p instanceof Equipo)
                .map(p -> (Equipo) p)
                .map(equipo -> equipo.buscarJugador(jugador))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }

    /**
     * Valida que no exista ya un jugador registrado con el mismo nombre y apellido,
     * en caso de haberlo genera un assertion error.
     */
    private void validarJugadorExiste(Jugador jugador) {
        boolean existeJugador = buscarJugador(jugador).isPresent();
        ASSERTION.assertion(!existeJugador, "El jugador ya esta registrado");
    }

    /**
     * Valida que no exista se puedan registrar jugadores que al momento del inicio
     * del torneo excedan el limite de edad.
     */
    private void validarLimiteEdadJugador(Jugador jugador) {
        var edadAlInicioTorneo = jugador.calcularEdad(fechaInicio);
        ASSERTION.assertion(limiteEdad == 0 || limiteEdad >= edadAlInicioTorneo,
                "No se pueden registrar jugadores que excedan el limite de edad del torneo");
    }

    // El método se llama listaEnfrentamientos y recibe un parámetro nombre, que representa el nombre de un equipo. El objetivo del método es filtrar y devolver una colección de enfrentamientos en los cuales el equipo especificado (nombre) esté involucrado, ya sea como equipo local o visitante.
    
    public Collection<Enfrentamiento> listaEnfrentamientos(String nombre) {
        // Se crea una nueva colección para almacenar los enfrentamientos relacionados con el equipo dado.
        Collection<Enfrentamiento> ListaEnfrentamientoEquipo = new LinkedList<>();
    
        // Verificar si la lista de enfrentamientos no es nula.
        if (enfrentamientos != null) {
            // Iterar a través de cada enfrentamiento en la lista de enfrentamientos.
            for (Enfrentamiento enfrentamiento : enfrentamientos) {
                // Obtener los nombres de los equipos local y visitante como cadenas.
                String equipoVisitante = (enfrentamiento.getEquipoVisitante() != null) ? enfrentamiento.getEquipoVisitante().toString() : "";
                String equipoLocal = (enfrentamiento.getEquipoLocal() != null) ? enfrentamiento.getEquipoLocal().toString() : "";
    
                // Verificar si el nombre del equipo coincide con el equipo local o visitante del enfrentamiento actual.
                if (equipoVisitante.equalsIgnoreCase(nombre) || equipoLocal.equalsIgnoreCase(nombre)) {
                    // Agregar el enfrentamiento actual a la lista de enfrentamientos relacionados con el equipo.
                    ListaEnfrentamientoEquipo.add(enfrentamiento);
                }
            }
        }
    
        // Devolver la colección de enfrentamientos relacionados con el equipo dado.
        return ListaEnfrentamientoEquipo;
    }
    
    
// Método que devuelve una colección de enfrentamientos que tienen un juez con el número de licencia proporcionado
public Collection<Enfrentamiento> juecesEnfrentamientos(String licencia) {
    // Se crea una nueva colección para almacenar los enfrentamientos que cumplen con la condición
    Collection<Enfrentamiento> listaEnfrentamientosJuez = new LinkedList<>();

    // Se verifica si la lista de enfrentamientos no es nula antes de comenzar el procesamiento
    if (enfrentamientos != null) {
        // Iteración sobre cada objeto Enfrentamiento en la lista enfrentamientos
        for (Enfrentamiento enfrentamiento : enfrentamientos) {
            // Se obtiene el número de licencia del juez asociado al enfrentamiento. Si el juez es nulo, se asigna una cadena vacía.
            String juez = (enfrentamiento.getJuez() != null) ? enfrentamiento.getJuez().toString() : "";

            // Se compara el número de licencia del juez con el número de licencia proporcionado como parámetro
            // Si son iguales, se agrega el enfrentamiento a la lista listaEnfrentamientosJuez
            if (juez.equals(licencia)) {
                listaEnfrentamientosJuez.add(enfrentamiento);
            }
        }
    }

    // Se devuelve la lista de enfrentamientos que tienen un juez con el número de licencia especificado
    return listaEnfrentamientosJuez;
   }



 


    public List<Estadisticas> obtenerEstadisticasEquiposOrdenadas() {
        // Utiliza streams para procesar la lista de equipos y obtener las estadísticas.
        // Luego, ordena las estadísticas según ciertos criterios y devuelve una lista ordenada.
        return equipos.stream()
                .map(equipo -> equipo.estadisticas())  // Mapea cada equipo a sus estadísticas.
                .sorted(Comparator
                        .comparingInt(Estadisticas::getVictorias)  // Ordena por victorias.
                        .thenComparingInt(Estadisticas::getEmpates)  // En caso de empate, ordena por empates.
                        .thenComparingInt(Estadisticas::getPerdidos)  // En caso de empate en empates, ordena por derrotas.
                        .reversed())  // Invierte el orden para obtener orden descendente.
                .collect(Collectors.toList());  // Recolecta las estadísticas ordenadas en una lista.
    }
}

     











   


     
        
    









