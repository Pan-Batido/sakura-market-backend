package cl.sakuramarket.sakura_market_backend.controller;

import cl.sakuramarket.sakura_market_backend.dto.Login;
import cl.sakuramarket.sakura_market_backend.dto.Registro;
import cl.sakuramarket.sakura_market_backend.dto.Respuestas.AuthResponse;
import cl.sakuramarket.sakura_market_backend.dto.Respuestas.MensajeResponse;
import cl.sakuramarket.sakura_market_backend.model.Rol;
import cl.sakuramarket.sakura_market_backend.model.Usuario;
import cl.sakuramarket.sakura_market_backend.repository.UsuarioRepository;
import cl.sakuramarket.sakura_market_backend.security.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints para login y registro de usuarios con JWT")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, UsuarioRepository usuarioRepo,
                          PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<?> login(@RequestBody Login req) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
            Usuario usuario = usuarioRepo.findByEmail(req.getEmail()).orElseThrow();
            String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());
            return ResponseEntity.ok(new AuthResponse(token, usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MensajeResponse("Correo electrónico o contraseña incorrectos."));
        }
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar un nuevo cliente en la plataforma")
    public ResponseEntity<?> registro(@RequestBody Registro req) {
        if (usuarioRepo.existsByEmail(req.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeResponse("El correo electrónico ya se encuentra registrado."));
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(req.getNombre());
        nuevo.setApellidos(req.getApellidos());
        nuevo.setEmail(req.getEmail().toLowerCase());
        nuevo.setPassword(passwordEncoder.encode(req.getPassword()));
        nuevo.setRol(Rol.CLIENTE);

        if (req.getDireccion() != null) {
            nuevo.setCalle(req.getDireccion().getCalle());
            nuevo.setDepartamento(req.getDireccion().getDepartamento());
            nuevo.setRegion(req.getDireccion().getRegion());
            nuevo.setComuna(req.getDireccion().getComuna());
        }

        usuarioRepo.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MensajeResponse("Cuenta creada con éxito"));
    }

    @PostMapping("/google")
    @Operation(summary = "Iniciar sesión o registrarse mediante Google OAuth 2.0")
    public ResponseEntity<?> loginGoogle(@RequestBody Map<String, String> body) {
        try {
            String credential = body.get("credential");
            if (credential == null || credential.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MensajeResponse("Token de Google no proporcionado."));
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://oauth2.googleapis.com/tokeninfo?id_token=" + credential))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MensajeResponse("Token de Google inválido o expirado."));
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode googleData = mapper.readTree(response.body());

            String email = googleData.get("email").asText().toLowerCase();
            String nombre = googleData.has("given_name") ? googleData.get("given_name").asText() : "Usuario";
            String apellidos = googleData.has("family_name") ? googleData.get("family_name").asText() : "Google";

            Usuario usuario = usuarioRepo.findByEmail(email).orElseGet(() -> {
                Usuario nuevo = new Usuario();
                nuevo.setNombre(nombre);
                nuevo.setApellidos(apellidos);
                nuevo.setEmail(email);
                nuevo.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                nuevo.setRol(Rol.CLIENTE);
                return usuarioRepo.save(nuevo);
            });

            String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());
            return ResponseEntity.ok(new AuthResponse(token, usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MensajeResponse("Error al validar la cuenta de Google."));
        }
    }
}