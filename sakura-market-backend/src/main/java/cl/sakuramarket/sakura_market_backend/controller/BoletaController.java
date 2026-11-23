package cl.sakuramarket.sakura_market_backend.controller;

import cl.sakuramarket.sakura_market_backend.dto.Boletas.CrearBoletaRequest;
import cl.sakuramarket.sakura_market_backend.dto.Respuestas.MensajeResponse;
import cl.sakuramarket.sakura_market_backend.model.Boleta;
import cl.sakuramarket.sakura_market_backend.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "Boletas y Órdenes", description = "Emisión y consulta de boletas de compra")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las boletas (Administrador y Vendedor)")
    public List<Boleta> listar() {
        return boletaService.listarTodas();
    }

    @PostMapping
    @Operation(summary = "Generar nueva boleta de compra y descontar stock")
    public ResponseEntity<?> crear(@RequestBody CrearBoletaRequest req) {
        try {
            Boleta boleta = boletaService.crearBoleta(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(boleta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(e.getMessage()));
        }
    }
}
