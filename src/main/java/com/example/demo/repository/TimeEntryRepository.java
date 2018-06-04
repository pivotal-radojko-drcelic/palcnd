package com.example.demo.repository;

import com.example.demo.model.TimeEntry;

import java.sql.Time;
import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry timeEntry);
    public TimeEntry find(long id);
    public List<TimeEntry> list();
    public TimeEntry update(long id, TimeEntry newTimeEntry);
    public void delete(long id);
}
