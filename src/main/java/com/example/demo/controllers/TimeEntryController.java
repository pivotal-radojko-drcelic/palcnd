package com.example.demo.controllers;

import com.example.demo.model.TimeEntry;
import com.example.demo.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping(value = "/time-entries")
public class TimeEntryController {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody TimeEntry timeEntry) {
        TimeEntry newEntry = timeEntryRepository.create(timeEntry);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(newEntry);
    }

    @GetMapping
    public ResponseEntity<?> getTimeEntries() {
        List<TimeEntry> allEntries = timeEntryRepository.list();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(allEntries);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTimeEntry(@PathVariable Long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(timeEntry);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> changeTimeEntity(@PathVariable Long id, @RequestBody TimeEntry newTimeEntry) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(id, newTimeEntry);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(updatedTimeEntry);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTimeEntity(@PathVariable Long id) {
        TimeEntry deletedTimeEntry = timeEntryRepository.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(deletedTimeEntry);
    }
}
