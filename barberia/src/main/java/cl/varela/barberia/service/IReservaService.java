package cl.varela.barberia.service;

import java.time.LocalDate;
import java.util.List;

import cl.varela.barberia.dto.ReservaDTO;
import cl.varela.barberia.model.Reserva;

public interface IReservaService {
    List<Reserva> obtenerPorFecha(LocalDate fecha);
    ReservaDTO crearReserva(ReservaDTO reservaDTO);
    List<Reserva> findAll();
       List<Reserva> buscarPorNombreCliente(String nombreCliente);
    List<Reserva> buscarPorNombreBarbero(String nombreBarbero);
    List<Reserva> buscarPorClienteYBarbero(String nombreCliente, String nombreBarbero);
      List<Reserva> obtenerPorNombreServicio(String nombreServicio);
      ReservaDTO actualizarEstado(Integer idReserva, String nuevoEstado);
      List<Reserva> obtenerReservasPorBarbero(Integer idBarbero);
      Integer obtenerSumaPreciosReservasPorBarbero(Integer idBarbero);
}


