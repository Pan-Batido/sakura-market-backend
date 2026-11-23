package cl.sakuramarket.sakura_market_backend.controller;

import cl.sakuramarket.sakura_market_backend.model.Categoria;
import cl.sakuramarket.sakura_market_backend.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorías", description = "Gestión de categorías de productos")
public class CategoriaController {

    private final CategoriaRepository categoriaRepo;

    public CategoriaController(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping
    @Operation(summary = "Listar todas las categorías")
    public List<Categoria> listar() {
        return categoriaRepo.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear nueva categoría (Solo Administrador)")
    public ResponseEntity<Categoria> crear(@RequestBody Categoria cat) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepo.save(cat));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría por ID (Solo Administrador)")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        if (!categoriaRepo.existsById(id)) return ResponseEntity.notFound().build();
        categoriaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
