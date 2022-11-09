package com.company.musicstorecatalog.Controller;

import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @GetMapping()
    public List<Track> getTracks(){
        return trackRepository.findAll();
    }

    @GetMapping("/{id}")
    public Track getTrackById(@PathVariable Integer id){
        Optional<Track> returnVal = trackRepository.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "trackId '" + id + "' does not exist");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Track addTrack(@RequestBody @Valid Track track){
        return trackRepository.save(track);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@RequestBody @Valid Track track){
        trackRepository.save(track);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable Integer id){
        Optional<Track> track = trackRepository.findById(id);
        if(track.isPresent()) {
            trackRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "trackId '" + id + "' does not exist");
        }
    }

}
