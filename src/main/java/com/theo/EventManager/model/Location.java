package com.theo.EventManager.model;

import jakarta.persistence.*;

import java.util.List;

    @Entity
    @Table(name = "locations")
    public class Location {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @OneToMany(mappedBy = "location")
        private List<Event> events;

        public Location () {}

        public Location (String name)
        {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> eventList) {
            this.events = events;
        }
    }
