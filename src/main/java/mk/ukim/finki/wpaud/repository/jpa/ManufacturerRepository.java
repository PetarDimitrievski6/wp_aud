package mk.ukim.finki.wpaud.repository.jpa;

import mk.ukim.finki.wpaud.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
