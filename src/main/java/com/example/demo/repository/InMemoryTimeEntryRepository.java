package com.example.demo.repository;

import com.example.demo.model.TimeEntry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private ConcurrentHashMap<Long, TimeEntry> dataStorage = new ConcurrentHashMap<>();
    private AtomicLong idCounter = new AtomicLong();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        long newId = idCounter.incrementAndGet();
        timeEntry.setId(newId);
        dataStorage.put(newId, timeEntry);

        return find(timeEntry.getId());
    }

    @Override
    public TimeEntry find(long id) {
        return dataStorage.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return dataStorage.values().stream().collect(Collectors.toList());
    }

    @Override
    public TimeEntry update(long id, TimeEntry newTimeEntry) {
        newTimeEntry.setId(id);
        dataStorage.replace(id, newTimeEntry);
        return newTimeEntry;
    }

    @Override
    public TimeEntry delete(long id) {
        return dataStorage.remove(id);
    }
}
