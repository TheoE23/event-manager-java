package com.theo.EventManager.repository;

import com.theo.EventManager.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {


    Page<Event> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Event> findByLocationId(Long locationId, Pageable pageable);

    Page<Event> findByCategoryIdAndLocationId(
            Long categoryId,
            Long locationId,
            Pageable pageable
    );
}