package com.company.musicstorecatalog.Controller;

import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping()
    public List<Artist> getArtists(){
        return artistRepository.findAll();
    }

    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable Integer id){
        Optional<Artist> returnVal = artistRepository.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "artistId '" + id + "' does not exist");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Artist addArtist(@RequestBody Artist artist){
        return artistRepository.save(artist);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@RequestBody Artist artist){
        artistRepository.save(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Integer id){
        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isPresent()) {
            artistRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "artistId '" + id + "' does not exist");
        }
    }


}
