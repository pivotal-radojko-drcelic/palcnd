package com.example.demo.controllers;

import com.example.demo.model.TimeEntry;
import com.example.demo.repository.TimeEntryRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/time-entries")
public class TimeEntryController {

    private final TimeEntryRepository timeEntryRepository;
    private final MeterRegistry meterRegistry;

    public TimeEntryController(TimeEntryRepository repository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = repository;
        this.meterRegistry = meterRegistry;
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody TimeEntry timeEntry) {
        TimeEntry newEntry = timeEntryRepository.create(timeEntry);
        meterRegistry.counter("TimeEntry.created").increment();
        meterRegistry.gauge("timeEntries.count", timeEntryRepository.list().size());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(newEntry);
    }

    @GetMapping
    public ResponseEntity<?> getTimeEntries() {
        List<TimeEntry> allEntries = timeEntryRepository.list();
        meterRegistry.counter("TimeEntry.listed").increment();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(allEntries);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTimeEntry(@PathVariable Long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        meterRegistry.counter("TimeEntry.read").increment();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(timeEntry);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> changeTimeEntity(@PathVariable Long id, @RequestBody TimeEntry newTimeEntry) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(id, newTimeEntry);
        meterRegistry.counter("TimeEntry.updated").increment();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(updatedTimeEntry);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTimeEntity(@PathVariable Long id) {
        TimeEntry deletedTimeEntry = timeEntryRepository.delete(id);
        meterRegistry.counter("TimeEntry.deleted").increment();
        meterRegistry.gauge("timeEntries.count", timeEntryRepository.list().size());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(deletedTimeEntry);
    }
}
