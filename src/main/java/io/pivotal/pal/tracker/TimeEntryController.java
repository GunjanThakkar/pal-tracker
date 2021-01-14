package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
@RestController
public class TimeEntryController {
    TimeEntryRepository timeEntryRepository;


    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }
    @PostMapping(value="/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry te = timeEntryRepository.create(timeEntryToCreate);
        if(te != null) {
            return new ResponseEntity<TimeEntry>(te, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity <TimeEntry> (HttpStatus.NOT_FOUND);
    }
    @GetMapping(value="/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);
    }
    @GetMapping(value="/time-entries/{nonExistentTimeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long nonExistentTimeEntryId) {
        TimeEntry te = timeEntryRepository.find(nonExistentTimeEntryId);
        if(te == null)
            return new ResponseEntity<>(te,HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(te, HttpStatus.OK);
    }
    @PutMapping(value="/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId,@RequestBody TimeEntry timeEntryToUpdate) {
        TimeEntry te = timeEntryRepository.update( timeEntryId,  timeEntryToUpdate);
        if(te!=null && te.getId() == timeEntryId )
            return new ResponseEntity<TimeEntry>(te,HttpStatus.OK);
        else
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping(value="/time-entries/{timeEntryId}")
    public ResponseEntity<Void> delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        if(timeEntryRepository.find(timeEntryId) == null)
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
