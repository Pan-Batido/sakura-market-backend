package cl.sakuramarket.sakura_market_backend.repository;

import cl.sakuramarket.sakura_market_backend.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
    List<Boleta> findAllByOrderByFechaEmisionDesc();
}
