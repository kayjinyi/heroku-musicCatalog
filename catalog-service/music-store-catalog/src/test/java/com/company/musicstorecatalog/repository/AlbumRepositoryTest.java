package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlbumRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;
    @Before
    public void setUp() throws Exception{
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();
        Artist newArtist = new Artist("Taylor Swift", "@Taylor", "@taylorswift18");
        newArtist = artistRepository.save(newArtist);


        Label newLabel = new Label("Big Machine", "bigMachine.com");
        newLabel = labelRepository.save(newLabel);

    }
    @Test
    public void shouldCreateGetAndDeleteAlbum() {
        List<Artist> artistList = artistRepository.findAll();
        int artistId = artistList.get(0).getId();
        List<Label> labelList = labelRepository.findAll();
        int labelId = labelList.get(0).getId();
        Album newAlbum = new Album("Bejeweled", artistId , LocalDate.of(2022, 10, 14), labelId, new BigDecimal("15.99"));
        newAlbum = albumRepository.save(newAlbum);
        Album foundAlbum = albumRepository.findById(newAlbum.getId()).get();

        assertEquals(newAlbum, foundAlbum);

        albumRepository.deleteById(newAlbum.getId());

        Optional<Album> shouldBeEmptyOptional = albumRepository.findById(newAlbum.getId());

        assertEquals(false, shouldBeEmptyOptional.isPresent());

    }

    @Test
    public void shouldFindAllAlbum(){
        List<Artist> artistList = artistRepository.findAll();
        int artistId = artistList.get(0).getId();
        List<Label> labelList = labelRepository.findAll();
        int labelId = labelList.get(0).getId();
        Album newAlbum = new Album("Bejeweled", artistId, LocalDate.of(2022, 10, 14), labelId , new BigDecimal("15.99"));
        Album newAlbum2 = new Album("Fearless", artistId, LocalDate.of(2020, 4, 9), labelId , new BigDecimal("20.99"));

        newAlbum = albumRepository.save(newAlbum);
        newAlbum2 = albumRepository.save(newAlbum2);
        List<Album> allAlbumList = new ArrayList<>();
        allAlbumList.add(newAlbum);
        allAlbumList.add(newAlbum2);

        List<Album> foundAll = albumRepository.findAll();
        assertEquals(allAlbumList.size(), foundAll.size());

    }


}