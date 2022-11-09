package com.company.musicstorecatalog.Controller;

import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @MockBean
    private ArtistRepository artistRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private Artist inputArtist;
    private Artist inputArtistWithId;
    private String inputJson;
    private Artist outputArtist;
    private Artist outputArtist2;



    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        inputArtist = new Artist("Taylor Swift", "@Taylor", "@taylorswift18");
        inputArtistWithId = new Artist(1,"Taylor Swift", "@Taylor", "@taylorswift18");
        outputArtist = new Artist(1,"Taylor Swift", "@Taylor", "@taylorswift18");
        outputArtist2 = new Artist(5,"Carpenter", "@Carpenter", "@CarpenterForever");

    }
    @Test
    public void shouldReturn201AndOnPost() throws Exception{
        inputJson = mapper.writeValueAsString(inputArtist);
        String outputJson = mapper.writeValueAsString(outputArtist);
        when(artistRepository.save(inputArtist)).thenReturn(outputArtist);

        mockMvc.perform(post("/artist")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumRecommendedById() throws Exception{
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputArtist2);
        when(artistRepository.findById(id)).thenReturn(Optional.of(outputArtist2));

        mockMvc.perform(get("/artist/8",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith404WhenGetByNotFoundIdthrowsException() throws Exception{
        Integer id = 888;

        when(artistRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/artist/8",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {

        List<Artist> outputList = Arrays.asList(outputArtist,outputArtist2);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(artistRepository).findAll();

        mockMvc.perform(get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturn204OnDelete() throws Exception {
        Integer id = 123;
        String outputJson = mapper.writeValueAsString(outputArtist2);
        when(artistRepository.findById(id)).thenReturn(Optional.of(outputArtist2));
        mockMvc.perform(delete("/artist/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        int id = 5;
        String inputJson = mapper.writeValueAsString(inputArtist);

        mockMvc.perform(put("/artist")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingAlbumRecomendation() throws Exception {
        // Arrange
        doReturn(Optional.ofNullable(null)).when(artistRepository).findById(134);

        // Act
        mockMvc.perform(
                        delete("/artist/134"))
                .andDo(print())
                .andExpect(status().isNotFound()); // Assert return 404 NOT_FOUND
    }

}