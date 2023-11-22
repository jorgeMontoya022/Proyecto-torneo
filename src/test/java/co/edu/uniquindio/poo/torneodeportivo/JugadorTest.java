/**
 * Clase para probar el registro de los jugadores
 * @author Área de programación UQ
 * @since 2023-08
 * 
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */
package co.edu.uniquindio.poo.torneodeportivo;

import java.time.LocalDate;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class JugadorTest {
    /**
     * Instancia para el manejo de logs
     */
    private static final Logger LOG = Logger.getLogger(JugadorTest.class.getName());

    /**
     * Verificar que sea posible registrar un jugador en un equipo
     * 
     */
    @Test
    public void registrarJugadorEquipo() {
        LOG.info("Inicio de prueba registrarJugadorEquipo...");
        // Almacenar los datos de prueba Equipo{Uniquindio}
        // Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador
        // {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 15 años}

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var estadistica = new Estadisticas("Real", 0, 0, 0);
        var equipo = new Equipo("Uniquindio", Genero.MASCULINO, estadistica, representante);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",
                LocalDate.now().minusYears(15), Genero.MASCULINO);

        equipo.registrarJugador(jugador);

        // Recuperación y verificación de datos
        assertTrue(equipo.jugadores().contains(jugador));
        assertEquals(1, equipo.jugadores().size());
        LOG.info("Fin de prueba registrarJugadorEquipo...");
    }

    /**
     * Verificar que sea posible registrar un jugador en un equipo desde el torneo
     * 
     */
    @Test
    public void registrarJugadorTorneo() {
        LOG.info("Inicio de prueba registrarJugadorTorneo...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\|
        // fechaActual - 15 días\|fechaActual+15 días\|24\|18\|0\|LOCAL|FEMENINO|GRUPAL}
        // Equipo{UniquindioFEM}
        // Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador
        // {Laura,Garcia,lauragarcia@email.com,6067431234, fechaActual - 15 años}

        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15),
                LocalDate.now().plusDays(15), (byte) 24, (byte) 18, 0, TipoTorneo.LOCAL, CaracterTorneo.GRUPAL,
                TorneoGenero.MASCULINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var estadistica = new Estadisticas("Real", 0, 0, 0);
        var equipo = new Equipo("UniquindioFEM", Genero.FEMENINO, estadistica, representante);
        var jugador = new Jugador("Laura", "Garcia", "lauragarcia@email.com", "6067431234",
                LocalDate.now().minusYears(15), Genero.FEMENINO);

        torneo.registrarParticipante(equipo);
        torneo.registrarJugador("UniquindioFEM", jugador);

        // Recuperación y verificación de datos
        assertTrue(equipo.jugadores().contains(jugador));
        assertEquals(1, equipo.jugadores().size());
        LOG.info("Fin de prueba registrarJugadorTorneo...");
    }

    /**
     * Verificar que sea posible registrar un jugador en un equipo desde el torneo
     * cuando el torneo no tiene limite de edad
     * 
     */
    @Test
    public void registrarJugadorTorneoSinLimiteEdad() {
        LOG.info("Inicio de prueba registrarJugadorTorneoSinLimiteEdad...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\|
        // fechaActual - 15 días\|fechaActual+15 días\|24\|0\|0\|LOCAL|MASCULINO|GRUPAL}
        // Equipo{UniquindioMasculino}
        // Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador
        // {Christian,Candela,chrcandela@email.com,6038917552, fechaActual - 21 años}

        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15),
                LocalDate.now().plusDays(15), (byte) 24, (byte) 0, 0, TipoTorneo.LOCAL, CaracterTorneo.GRUPAL,
                TorneoGenero.MASCULINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var estadistica = new Estadisticas("Real", 0, 0, 0);
        var equipo = new Equipo("UniquindioMasculino", Genero.MASCULINO, estadistica, representante);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6038917552",
                LocalDate.now().minusYears(21), Genero.MASCULINO);

        torneo.registrarParticipante(equipo);
        torneo.registrarJugador("UniquindioMasculino", jugador);

        // Recuperación y verificación de datos
        assertTrue(equipo.jugadores().contains(jugador));
        assertEquals(1, equipo.jugadores().size());
        LOG.info("Fin de prueba registrarJugadorTorneoSinLimiteEdad...");
    }

    /**
     * Verificar que no sea posible registrar un jugador en un equipo desde el
     * torneo cuando el torneo con limite de edad
     * 
     * NOTA: Si el registro se hiciera desde el equipo sería posible de momento
     * registrar el jugador, debido a que el torneo no tiene control sobre el código
     * interno del equipo, esto se puede solucionar validando todos los equipos al
     * momento de iniciar un torneo. O se podría aplicar el patron de diseño
     * listener para que torneo sea advertido cuando se haga una modificación en la
     * lista de jugadores del equipo.
     * 
     */
    @Test
    public void registrarJugadorTorneoConLimiteEdad() {
        LOG.info("Inicio de prueba registrarJugadorTorneoConLimiteEdad...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\|
        // fechaActual - 15 días\|fechaActual+15 días\|24\|18\|0\|LOCAL|MIXTO|GRUPAL}
        // Equipo{Uniquindio}
        // Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador
        // {Ana,Ruiz,anaruiz@email.com,6073861062, fechaActual - 21 años}

        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15),
                LocalDate.now().plusDays(15), (byte) 24, (byte) 18, 0, TipoTorneo.LOCAL, CaracterTorneo.GRUPAL,
                TorneoGenero.MASCULINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var estadistica = new Estadisticas("Real", 0, 0, 0);
        var equipo = new Equipo("Uniquindio",Genero.MASCULINO, estadistica, representante);
        var jugador = new Jugador("Ana", "Ruiz", "anaruiz@email.com", "6073861062", LocalDate.now().minusYears(21), Genero.MASCULINO);

        torneo.registrarParticipante(equipo);
        assertThrows(Throwable.class, () -> torneo.registrarJugador("Uniquindio", jugador));

        LOG.info("Fin de prueba registrarJugadorTorneoConLimiteEdad...");
    }

    /**
     * Verificar que no sea posible registrar un jugador en un equipo si las
     * inscripciones ya cerraron
     * 
     * NOTA: Si el registro se hiciera desde el equipo sería posible de momento
     * registrar el jugador, debido a que el torneo no tiene control sobre el código
     * interno del equipo, esto se puede solucionar validando todos los equipos al
     * momento de iniciar un torneo. O se podría aplicar el patron de diseño
     * listener para que torneo sea advertido cuando se haga una modificación en la
     * lista de jugadores del equipo.
     * 
     */
    @Test
    public void registrarJugadorTorneoInscripcionesCerradas() {
        LOG.info("Inicio de prueba registrarJugadorTorneoInscripcionesCerradas...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\|
        // fechaActual - 15 días\|fechaActual-1 día\|24\|18\|0\|LOCAL|MIXTO|GRUPAL}
        // Equipo{Uniquindio}
        // Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300} Jugador
        // {Ana,Ruiz,anaruiz@email.com,6073861062, fechaActual - 15 años}

        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15),
                LocalDate.now().plusDays(1), (byte) 24, (byte) 18, 0, TipoTorneo.LOCAL, CaracterTorneo.GRUPAL,
                TorneoGenero.FEMENINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var estadistica = new Estadisticas("Real", 0, 0, 0);
        var equipo = new Equipo("Uniquindio", Genero.FEMENINO, estadistica, representante);
        var jugador = new Jugador("Ana", "Ruiz", "anaruiz@email.com", "6073861062", LocalDate.now().minusYears(15), Genero.FEMENINO);

        torneo.registrarParticipante(equipo);
        torneo.setFechaCierreInscripciones(LocalDate.now().minusDays(1));
        assertThrows(Throwable.class, () -> torneo.registrarJugador("Uniquindio", jugador));

        LOG.info("Fin de prueba registrarJugadorTorneoInscripcionesCerradas...");
    }

    /**
     * Verificar que no sea posible registrar dos jugadores con el mismo nombre y
     * apellido en un mismo equipo
     * 
     */
    @Test
    public void registrarJugadoresRepetidosEquipo() {
        LOG.info("Inicio de prueba registrarJugadorEquipo...");
        // Almacenar los datos de prueba Equipo{Uniquindio}
        // Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador
        // {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 15 años}

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var estadistica = new Estadisticas("Real", 0, 0, 0);
        var equipo = new Equipo("UniquindioMasculino", Genero.MASCULINO, estadistica, representante);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",
                LocalDate.now().minusYears(15), Genero.MASCULINO);
        var jugador2 = new Jugador("Christian", "Candela", "ccandela@email.com", "6067431235",
                LocalDate.now().minusYears(15), Genero.MASCULINO);
        equipo.registrarJugador(jugador);
        assertThrows(Throwable.class, () -> equipo.registrarJugador(jugador2));

        // Recuperación y verificación de datos

        LOG.info("Fin de prueba registrarJugadorEquipo...");
    }

    /**
     * Verificar que no sea posible registrar dos jugadores con el mismo nombre y apellido en un mismo  torneo 
     * 
     */
    @Test
    public void registrarJugadoresRepetidosTorneo() {
        LOG.info("Inicio de prueba registrarJugadoresRepetidosTorneo...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\| fechaActual - 15 días\|fechaActual+15 días\|24\|18\|0\|LOCAL|FEMENINO|GRUPAL}  Equipo{UniquindioFEM} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300},  Jugador {Laura,Garcia,lauragarcia@email.com,6067431234, fechaActual - 15 años}, Jugador {Laura,Garcia,lauragarcia@email.com,6067431234, fechaActual - 15 años}, Equipo{QuindíoFEM}

         var torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)18, 0,TipoTorneo.LOCAL, CaracterTorneo.GRUPAL, TorneoGenero.FEMENINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var estadistica = new Estadisticas("Real", 0, 0, 0);
        var equipo = new Equipo("UniquindioFEM", Genero.FEMENINO, estadistica, representante);
        var equipo2 = new Equipo("QuindíoFEM", Genero.FEMENINO, estadistica, representante);
        torneo.registrarParticipante(equipo);
        torneo.registrarParticipante(equipo2);

        var jugador = new Jugador("Laura", "Garcia", "lauragarcia@email.com", "6067431234",LocalDate.now().minusYears(15), Genero.FEMENINO);
        var jugador2 = new Jugador("Laura", "Garcia", "lauragarcia@email.com", "6067431234",LocalDate.now().minusYears(15),Genero.FEMENINO);
                
        torneo.registrarJugador("UniquindioFEM",jugador);
        assertThrows(Throwable.class,()->torneo.registrarJugador("QuindíoFEM",jugador2));

        // Recuperación y verificación de datos
        
    } 

    

    @Test
    public void registrarJugadoresGenerosDistintos() {
        LOG.info("Inicio de prueba registrarJugadoresRepetidosTorneo...");
       
         var torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)18, 0,TipoTorneo.LOCAL, CaracterTorneo.GRUPAL, TorneoGenero.FEMENINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var estadistica = new Estadisticas("Real", 0, 0, 0);
        var equipo = new Equipo("UniquindioFEM", Genero.FEMENINO, estadistica, representante);
        var equipo2 = new Equipo("QuindíoFEM", Genero.FEMENINO, estadistica, representante);
        torneo.registrarParticipante(equipo);
        torneo.registrarParticipante(equipo2);

        var jugador = new Jugador("Laura", "Garcia", "lauragarcia@email.com", "6067431234",LocalDate.now().minusYears(15), Genero.FEMENINO);
        var jugador2 = new Jugador("Daniel", "Muñoz", "DanielM@email.com", "3227069941",LocalDate.now().minusYears(15),Genero.MASCULINO);
                
        equipo.registrarJugador(jugador);
        assertThrows(Throwable.class,()->equipo.registrarJugador(jugador2));
        
        assertTrue(!equipo.jugadores().contains(jugador2));
    }

}

