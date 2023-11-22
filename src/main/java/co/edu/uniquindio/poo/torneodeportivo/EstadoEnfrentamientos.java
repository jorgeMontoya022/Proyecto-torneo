package co.edu.uniquindio.poo.torneodeportivo;

import java.time.LocalDate;

public enum EstadoEnfrentamientos {
    PENDIENTE,
    EN_JUEGO,
    FINALIZADO,
    APLAZADO;

    public static boolean esEstadoValido(EstadoEnfrentamientos estado, LocalDate fechaHoraInicio, LocalDate fechaHoraResultado) {
        switch (estado) {
            case PENDIENTE:
                // Puede asignarse a enfrentamientos programados pero no jugados
                return fechaHoraInicio.isAfter(fechaHoraResultado); // Verifica si la fecha de inicio está en el futuro
            case EN_JUEGO:
                // Solo se asigna si la fecha y hora de inicio son oportunos
                return fechaHoraInicio.isBefore(fechaHoraResultado); // Verifica si la fecha de inicio está en el pasado
            case FINALIZADO: 
                // Se asigna automáticamente al registrar el resultado
                return fechaHoraResultado != null; // Verifica si se ha registrado el resultado
            case APLAZADO:
                // Puede asignarse a enfrentamientos que no pueden jugarse
                return true;
            default:
                return false;
                
        }
    }
}
