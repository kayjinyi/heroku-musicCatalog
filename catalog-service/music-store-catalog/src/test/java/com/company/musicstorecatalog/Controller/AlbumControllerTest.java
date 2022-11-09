package com.company.musicstorecatalog.Controller;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.ErrorMessage;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
import com.company.musicstorecatalog.repository.AlbumRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {
    @MockBean
    private AlbumRepository albumRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private Album inputAlbum;
    private Album inputAlbumWithId;
    private String inputJson;
    private Album outputAlbum;
    private Album outputAlbum2;



    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        inputAlbum = new Album("Bejeweled", 1, LocalDate.of(2022, 10, 14), 1, new BigDecimal("15.99"));
        inputAlbumWithId = new Album(1,"Bejeweled", 1, LocalDate.of(2022, 10, 14), 1, new BigDecimal("15.99"));
        outputAlbum = new Album(1,"Bejeweled", 1, LocalDate.of(2022, 10, 14), 1, new BigDecimal("15.99"));
        outputAlbum2 = new Album(5,"Fearless", 1, LocalDate.of(2020, 4, 9), 1, new BigDecimal(20.99));

    }
    @Test
    public void shouldReturn201AndOnPost() throws Exception{
        inputJson = mapper.writeValueAsString(inputAlbum);
        String outputJson = mapper.writeValueAsString(outputAlbum);
        when(albumRepository.save(inputAlbum)).thenReturn(outputAlbum);

        mockMvc.perform(post("/album")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumRecommendedById() throws Exception{
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputAlbum2);
        when(albumRepository.findById(id)).thenReturn(Optional.of(outputAlbum2));

        mockMvc.perform(get("/album/8",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith404WhenGetByNotFoundIdthrowsException() throws Exception{
        Integer id = 888;

        when(albumRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/album/8",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {

        List<Album> outputList = Arrays.asList(outputAlbum,outputAlbum2);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(albumRepository).findAll();

        mockMvc.perform(get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturn204OnDelete() throws Exception {
        Integer id = 123;
        String outputJson = mapper.writeValueAsString(outputAlbum2);
        when(albumRepository.findById(id)).thenReturn(Optional.of(outputAlbum2));
        mockMvc.perform(delete("/album/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        int id = 5;
        String inputJson = mapper.writeValueAsString(inputAlbum);

        mockMvc.perform(put("/album")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingAlbumRecomendation() throws Exception {
        // Arrange
        doReturn(Optional.ofNullable(null)).when(albumRepository).findById(134);

        // Act
        mockMvc.perform(
                        delete("/album/134"))
                .andDo(print())
                .andExpect(status().isNotFound()); // Assert return 404 NOT_FOUND
    }

}