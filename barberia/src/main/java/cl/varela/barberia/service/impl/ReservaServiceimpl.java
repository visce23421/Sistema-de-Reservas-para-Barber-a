package cl.varela.barberia.service.impl;
import cl.varela.barberia.dto.BarberoDTO;
import cl.varela.barberia.dto.ClienteDTO;
import cl.varela.barberia.dto.ReservaDTO;
import cl.varela.barberia.dto.ServiciosDTO;
import cl.varela.barberia.enums.EstadoReserva;
import cl.varela.barberia.model.Barbero;
import cl.varela.barberia.model.Cliente;
import cl.varela.barberia.model.Reserva;
import cl.varela.barberia.model.Servicios;
import cl.varela.barberia.repository.BarberoRepository;
import cl.varela.barberia.repository.ClienteRepository;
import cl.varela.barberia.repository.ReservaRepository;
import cl.varela.barberia.repository.ServiciosRepository;
import cl.varela.barberia.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReservaServiceimpl implements IReservaService{

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private BarberoRepository barberoRepository;
    
    @Autowired
    private ServiciosRepository serviciosRepository;
    
    @Autowired
    private IReporteIngresosBarberoService reporteIngresosBarberoService;

    
    @Override
    public List<Reserva> findAll() {
        return (List<Reserva>)reservaRepository.findAll();
    }

    @Override
    public List<Reserva> obtenerPorFecha(LocalDate fecha) {
        return reservaRepository.findByFecha(fecha);
    }


 @Transactional
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
       
        if(reservaDTO.getFecha() == null || reservaDTO.getHora() == null) {
            throw new IllegalArgumentException("Fecha y hora son requeridas");
        }
        
        
        Cliente cliente = clienteRepository.findById(reservaDTO.getCliente().getIdCliente())
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            
        Barbero barbero = barberoRepository.findById(reservaDTO.getBarbero().getIdbarbero())
            .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado"));
            
        Servicios servicio = serviciosRepository.findById(reservaDTO.getServicios().getIdServicio())
            .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        
        // dps barbero
         int count = reservaRepository.countByBarberoFechaHora(
        reservaDTO.getBarbero().getIdbarbero(),
        reservaDTO.getFecha(),
        reservaDTO.getHora());
            if(count > 0) {
                 throw new IllegalStateException("El barbero ya tiene una reserva en ese horario");
        }
        
        
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setBarbero(barbero);
        reserva.setServicios(servicio);
        reserva.setFecha(reservaDTO.getFecha());
        reserva.setHora(reservaDTO.getHora());
        reserva.setEstado(EstadoReserva.PENDIENTE);
        Reserva reservaGuardada = reservaRepository.save(reserva);
        
        //Convertir a DTO 
        return convertirAReservaDTO(reservaGuardada);
    }

    private ReservaDTO convertirAReservaDTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setFecha(reserva.getFecha());
        dto.setHora(reserva.getHora());
        dto.setEstado(reserva.getEstado());
        
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(reserva.getCliente().getIdCliente());
        clienteDTO.setNombre(reserva.getCliente().getNombre());
        
        dto.setCliente(clienteDTO);
        
        BarberoDTO barberoDTO = new BarberoDTO();
        barberoDTO.setIdbarbero(reserva.getBarbero().getIdbarbero());
        barberoDTO.setNombre(reserva.getBarbero().getNombre());
        dto.setBarbero(barberoDTO);
        
        ServiciosDTO servicioDTO = new ServiciosDTO();
        servicioDTO.setIdServicio(reserva.getServicios().getIdServicio());
        servicioDTO.setNombre(reserva.getServicios().getNombre()); 
        servicioDTO.setPrecio(reserva.getServicios().getPrecio());
        dto.setServicios(servicioDTO);
        
        return dto;
    }
    @Override
    public List<Reserva> buscarPorNombreCliente(String nombreCliente) {
        return reservaRepository.findByCliente_NombreContainingIgnoreCase(nombreCliente);
    }

    @Override
    public List<Reserva> buscarPorNombreBarbero(String nombreBarbero) {
        return reservaRepository.findByBarbero_NombreContainingIgnoreCase(nombreBarbero);
    }

    @Override
    public List<Reserva> buscarPorClienteYBarbero(String nombreCliente, String nombreBarbero) {
        return reservaRepository.buscarPorClienteYBarbero(nombreCliente, nombreBarbero);
    }
    @Override
    public List<Reserva> obtenerPorNombreServicio(String nombreServicio) {
          boolean existeServicio = serviciosRepository.existsByNombreIgnoreCase(nombreServicio);
    if (!existeServicio) {
        throw new IllegalStateException("El servicio '" + nombreServicio + "' no existe");
    }

    // Si el servicio existe, buscam las reserva
    List<Reserva> reservas = reservaRepository.findByServicios_NombreContainingIgnoreCase(nombreServicio);
    if (reservas.isEmpty()) {
        throw new IllegalStateException("No se encontraron reservas con el servicio: " + nombreServicio);
    }
        return reservas;
    }

        
    @Override
    @Transactional
    public ReservaDTO actualizarEstado(Integer idReserva, String nuevoEstado) {
        Reserva reserva = reservaRepository.findById(idReserva)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        EstadoReserva estadoAnterior = reserva.getEstado();
        EstadoReserva nuevoEstadoEnum;
        try {
            nuevoEstadoEnum = EstadoReserva.valueOf(nuevoEstado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }
        reserva.setEstado(nuevoEstadoEnum);
        Reserva actualizada = reservaRepository.save(reserva);
        // Lógica para actualizar el reporte de ingresos si el estado cambia a COMPLETADA
        if (nuevoEstadoEnum == EstadoReserva.COMPLETADA && estadoAnterior != EstadoReserva.COMPLETADA) {
            Integer idBarbero = actualizada.getBarbero().getIdbarbero();
            Integer montoServicio = actualizada.getServicios().getPrecio();
            
            reporteIngresosBarberoService.actualizarReporte(idBarbero, montoServicio);
        }
        
        return modelMapper.map(actualizada, ReservaDTO.class);
    }
    @Override
    public List<Reserva> obtenerReservasPorBarbero(Integer idBarbero) {
        return reservaRepository.findByBarbero_Idbarbero(idBarbero);
    }
    @Override
    public Integer obtenerSumaPreciosReservasPorBarbero(Integer idBarbero) {
        return reservaRepository.sumPreciosByBarberoId(idBarbero);
    }
    
}
   


