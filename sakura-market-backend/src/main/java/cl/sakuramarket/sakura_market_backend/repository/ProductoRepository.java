package cl.sakuramarket.sakura_market_backend.repository;

import cl.sakuramarket.sakura_market_backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByActivoTrue();
}
