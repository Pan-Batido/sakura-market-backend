package cl.sakuramarket.sakura_market_backend.service;

import cl.sakuramarket.sakura_market_backend.dto.Boletas.CrearBoletaRequest;
import cl.sakuramarket.sakura_market_backend.dto.Boletas.ItemBoletaDTO;
import cl.sakuramarket.sakura_market_backend.model.*;
import cl.sakuramarket.sakura_market_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepo;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    public BoletaService(BoletaRepository boletaRepo, ProductoRepository productoRepo, UsuarioRepository usuarioRepo) {
        this.boletaRepo = boletaRepo;
        this.productoRepo = productoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public List<Boleta> listarTodas() {
        return boletaRepo.findAllByOrderByFechaEmisionDesc();
    }

    @Transactional
    public Boleta crearBoleta(CrearBoletaRequest req) {
        Boleta boleta = new Boleta();
        boleta.setNumeroOrden("SAK-" + (100000 + new Random().nextInt(900000)));
        boleta.setNombreCliente(req.getCliente().getNombre());
        boleta.setApellidosCliente(req.getCliente().getApellidos());
        boleta.setCorreoCliente(req.getCliente().getCorreo());
        boleta.setCalleEntrega(req.getDireccion().getCalle());
        boleta.setDeptoEntrega(req.getDireccion().getDepartamento());
        boleta.setRegionEntrega(req.getDireccion().getRegion());
        boleta.setComunaEntrega(req.getDireccion().getComuna());
        boleta.setIndicaciones(req.getDireccion().getIndicaciones());

        if (req.getIdUsuario() != null) {
            usuarioRepo.findById(req.getIdUsuario()).ifPresent(boleta::setUsuario);
        }

        int totalCalculado = 0;

        for (ItemBoletaDTO item : req.getItems()) {
            Producto prod = productoRepo.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado ID: " + item.getId()));

            if (prod.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + prod.getNombre());
            }

            // Descontar stock en inventario
            prod.setStock(prod.getStock() - item.getCantidad());
            productoRepo.save(prod);

            DetalleBoleta detalle = new DetalleBoleta();
            detalle.setBoleta(boleta);
            detalle.setProducto(prod);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(prod.getPrecio());
            detalle.setSubtotal(prod.getPrecio() * item.getCantidad());

            boleta.getDetalles().add(detalle);
            totalCalculado += detalle.getSubtotal();
        }

        boleta.setTotal(totalCalculado);
        return boletaRepo.save(boleta);
    }
}
