package cl.sakuramarket.sakura_market_backend.config;

import cl.sakuramarket.sakura_market_backend.model.*;
import cl.sakuramarket.sakura_market_backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final CategoriaRepository categoriaRepo;
    private final ProductoRepository productoRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepo,
                           CategoriaRepository categoriaRepo,
                           ProductoRepository productoRepo,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.categoriaRepo = categoriaRepo;
        this.productoRepo = productoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepo.count() == 0) {
            usuarioRepo.save(new Usuario("Administrador", "Sakura", "admin@sakuramarket.cl", passwordEncoder.encode("admin123"), Rol.ADMINISTRADOR));
            usuarioRepo.save(new Usuario("Vendedor", "Sakura", "vendedor@sakuramarket.cl", passwordEncoder.encode("vendedor123"), Rol.VENDEDOR));
            usuarioRepo.save(new Usuario("Cliente", "Prueba", "cliente@sakuramarket.cl", passwordEncoder.encode("user123"), Rol.CLIENTE));
        }

        if (categoriaRepo.count() == 0 && productoRepo.count() == 0) {
            Categoria comida = categoriaRepo.save(new Categoria("Comida", "Ramen artesanales y platos típicos asiáticos"));
            Categoria snacks = categoriaRepo.save(new Categoria("Snacks", "Pocky, mochi y dulces tradicionales"));
            Categoria bebidas = categoriaRepo.save(new Categoria("Bebidas", "Ramune, tés con leche y refrescos importados"));

            productoRepo.save(new Producto(comida, "Ramen Tonkotsu Clásico", "Fideos de trigo artesanales en cremoso caldo de cerdo.", 7990, 15, "/src/assets/img/RamenOrientalPicante.png"));
            productoRepo.save(new Producto(snacks, "Pocky de Té Verde Matcha", "Palitos de galleta crujiente cubiertos con crema de matcha.", 2490, 30, "/src/assets/img/Pocky1.webp"));
            productoRepo.save(new Producto(bebidas, "Bebida Ramune Original", "Bebida gaseosa japonesa tradicional sabor lima-limón.", 1990, 25, "/src/assets/img/bebida1.jpg"));
            productoRepo.save(new Producto(bebidas, "Leche con Té y Taro 390ml", "Té con leche y taro de cremosa textura.", 2300, 20, "/src/assets/img/bebida3.png"));
            productoRepo.save(new Producto(snacks, "Mochi de Fresa Tradicional", "Pastelitos de arroz suave rellenos de pasta de fresa.", 3200, 18, "/src/assets/img/Pocky2.webp"));
            productoRepo.save(new Producto(comida, "Ramen Neoguri Picante", "Ramen picante sabor a mariscos con fideos gruesos.", 2100, 12, "/src/assets/img/RamenCarne.jpg"));
            productoRepo.save(new Producto(snacks, "Dorayaki Relleno de Anko", "Bizcocho esponjoso japonés relleno de judía roja dulce.", 1800, 22, "/src/assets/img/Pocky3.webp"));
            productoRepo.save(new Producto(bebidas, "Jugo de Uva Verde Coco Palm", "Bebida de uva verde con trocitos reales de coco.", 1650, 40, "/src/assets/img/bebida6.png"));
            productoRepo.save(new Producto(snacks, "Pepero Almond Crunch", "Galletas de palito cubiertas de chocolate y almendras.", 2200, 25, "/src/assets/img/Pocky4.webp"));
            productoRepo.save(new Producto(comida, "Ramen Shin Ramyun Gourmet", "Clásico ramen coreano picante con caldo de verduras.", 1950, 15, "/src/assets/img/RamenPollo.png"));
        }
    }
}
