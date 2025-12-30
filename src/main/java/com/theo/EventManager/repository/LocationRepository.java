package com.theo.EventManager.repository;

import com.theo.EventManager.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
