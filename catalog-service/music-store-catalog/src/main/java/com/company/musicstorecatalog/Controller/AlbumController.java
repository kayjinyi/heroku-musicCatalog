package com.company.musicstorecatalog.Controller;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping()
    public List<Album> getAlbums(){
        return albumRepository.findAll();
    }

    @GetMapping("/{id}")
    public Album getAlbumById(@PathVariable Integer id){
        Optional<Album> returnVal = albumRepository.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "albumId '" + id + "' does not exist");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Album addAlbum(@RequestBody Album album){
        return albumRepository.save(album);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@RequestBody Album album){
        albumRepository.save(album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Integer id){
        Optional<Album> album = albumRepository.findById(id);
        if(album.isPresent()) {
            albumRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "albumId '" + id + "' does not exist");
        }
    }

}
