package cl.sakuramarket.sakura_market_backend.controller;

import cl.sakuramarket.sakura_market_backend.model.Usuario;
import cl.sakuramarket.sakura_market_backend.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Consulta de usuarios registrados (Solo Administrador)")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;

    public UsuarioController(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public List<Usuario> listar() {
        return usuarioRepo.findAll();
    }
}
