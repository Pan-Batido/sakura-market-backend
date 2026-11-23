package cl.sakuramarket.sakura_market_backend.repository;

import cl.sakuramarket.sakura_market_backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByNombre(String nombre);
}