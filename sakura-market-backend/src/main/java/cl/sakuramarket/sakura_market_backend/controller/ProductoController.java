package cl.sakuramarket.sakura_market_backend.controller;

import cl.sakuramarket.sakura_market_backend.model.Categoria;
import cl.sakuramarket.sakura_market_backend.model.Producto;
import cl.sakuramarket.sakura_market_backend.repository.CategoriaRepository;
import cl.sakuramarket.sakura_market_backend.repository.ProductoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "CRUD de catálogo e inventario de Sakura Market")
public class ProductoController {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public ProductoController(ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos activos")
    public List<Producto> listar() {
        return productoRepo.findByActivoTrue();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de un producto por ID")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        return productoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto (Solo Administrador)")
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        String nombreCat = String.valueOf(body.get("categoria"));
        Categoria cat = categoriaRepo.findByNombre(nombreCat)
                .orElseGet(() -> categoriaRepo.findAll().get(0));

        Producto p = new Producto();
        p.setNombre((String) body.get("nombre"));
        p.setDescripcion((String) body.getOrDefault("descripcion", ""));
        p.setPrecio(Integer.valueOf(String.valueOf(body.get("precio"))));
        p.setStock(Integer.valueOf(String.valueOf(body.get("stock"))));
        p.setImagen((String) body.getOrDefault("imagen", ""));
        p.setCategoria(cat);

        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepo.save(p));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente (Solo Administrador)")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        return productoRepo.findById(id).map(p -> {
            if (body.containsKey("nombre")) p.setNombre((String) body.get("nombre"));
            if (body.containsKey("descripcion")) p.setDescripcion((String) body.get("descripcion"));
            if (body.containsKey("precio")) p.setPrecio(Integer.valueOf(String.valueOf(body.get("precio"))));
            if (body.containsKey("stock")) p.setStock(Integer.valueOf(String.valueOf(body.get("stock"))));
            if (body.containsKey("imagen")) p.setImagen((String) body.get("imagen"));
            if (body.containsKey("categoria")) {
                String nombreCat = String.valueOf(body.get("categoria"));
                categoriaRepo.findByNombre(nombreCat).ifPresent(p::setCategoria);
            }
            return ResponseEntity.ok(productoRepo.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar (desactivar) un producto (Solo Administrador)")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        return productoRepo.findById(id).map(p -> {
            p.setActivo(false);
            productoRepo.save(p);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}