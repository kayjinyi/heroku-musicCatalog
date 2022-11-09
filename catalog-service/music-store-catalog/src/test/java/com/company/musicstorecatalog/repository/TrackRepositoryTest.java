package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrackRepositoryTest {
        @Autowired
    TrackRepository trackRepository;
        @Autowired
    AlbumRepository albumRepository;
        @Autowired
    LabelRepository labelRepository;
        @Autowired
    ArtistRepository artistRepository;
    @Before
    public void setUp() throws Exception{
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();
        Artist newArtist = new Artist("Taylor Swift", "@Taylor", "@taylorswift18");
        newArtist = artistRepository.save(newArtist);
        Label newLabel = new Label("Big Machine", "bigMachine.com");
        newLabel = labelRepository.save(newLabel);
        List<Artist> artistList = artistRepository.findAll();
        int artistId = artistList.get(0).getId();
        List<Label> labelList = labelRepository.findAll();
        int labelId = labelList.get(0).getId();
        Album newAlbum = new Album("Bejeweled", artistId , LocalDate.of(2022, 10, 14), labelId, new BigDecimal("15.99"));
        newAlbum = albumRepository.save(newAlbum);
    }
    @Test
    public void shouldCreateGetAndDeleteTrack() {
        List<Album> albumList = albumRepository.findAll();
        int albumId= albumList.get(0).getId();
        Track newTrack = new Track("the Lakes", albumId, new BigDecimal("199"));
        newTrack = trackRepository.save(newTrack);
        Track foundTrack = trackRepository.findById(newTrack.getId()).get();

        assertEquals(newTrack, foundTrack);

        trackRepository.deleteById(newTrack.getId());

        Optional<Track> shouldBeEmptyOptional = trackRepository.findById(newTrack.getId());

        assertEquals(false, shouldBeEmptyOptional.isPresent());

    }

    @Test
    public void shouldFindAllTrack(){
        List<Album> albumList = albumRepository.findAll();
        int albumId= albumList.get(0).getId();
        Track newTrack = new Track("the Lakes", albumId, new BigDecimal("199"));
        Track newTrack2 = new Track("This Love", albumId, new BigDecimal("129"));

        newTrack = trackRepository.save(newTrack);
        newTrack2 = trackRepository.save(newTrack2);
        List<Track> allTrackList = new ArrayList<>();
        allTrackList.add(newTrack);
        allTrackList.add(newTrack2);

        List<Track> foundAll = trackRepository.findAll();
        assertEquals(allTrackList.size(), foundAll.size());

    }

}