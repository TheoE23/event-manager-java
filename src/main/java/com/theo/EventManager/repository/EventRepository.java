package com.theo.EventManager.repository;

import com.theo.EventManager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}