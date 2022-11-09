package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Artist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRepositoryTest {
        @Autowired
    ArtistRepository artistRepository;
    @Before
    public void setUp() throws Exception{
        artistRepository.deleteAll();
    }
    @Test
    public void shouldCreateGetAndDeleteArtist() {
        Artist newArtist = new Artist("Taylor Swift", "@Taylor", "@taylorswift18");
        newArtist = artistRepository.save(newArtist);
        Artist foundArtist = artistRepository.findById(newArtist.getId()).get();

        assertEquals(newArtist, foundArtist);

        artistRepository.deleteById(newArtist.getId());

        Optional<Artist> shouldBeEmptyOptional = artistRepository.findById(newArtist.getId());

        assertEquals(false, shouldBeEmptyOptional.isPresent());

    }

    @Test
    public void shouldFindAllArtist(){
        Artist newArtist = new Artist("Taylor Swift", "@Taylor", "@taylorswift18");
        Artist newArtist2 = new Artist("Carpenter", "@Carpenter", "@CarpenterForever");

        newArtist = artistRepository.save(newArtist);
        newArtist2 = artistRepository.save(newArtist2);
        List<Artist> allArtistList = new ArrayList<>();
        allArtistList.add(newArtist);
        allArtistList.add(newArtist2);

        List<Artist> foundAll = artistRepository.findAll();
        assertEquals(allArtistList.size(), foundAll.size());

    }

}