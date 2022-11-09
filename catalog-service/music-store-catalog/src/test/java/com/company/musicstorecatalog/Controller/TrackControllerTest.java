package com.company.musicstorecatalog.Controller;

import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
public class TrackControllerTest {

    @MockBean
    private TrackRepository trackRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private Track inputTrack;
    private Track inputTrackWithId;
    private String inputJson;
    private Track outputTrack;
    private Track outputTrack2;



    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        inputTrack = new Track("the Lakes", 1, new BigDecimal(199));
        inputTrackWithId = new Track(1,"the Lakes", 1, new BigDecimal(199));
        outputTrack = new Track(1,"the Lakes", 1, new BigDecimal(199));
        outputTrack2 = new Track(5,"This Love", 2, new BigDecimal(129));

    }
    @Test
    public void shouldReturn201AndOnPost() throws Exception{
        inputJson = mapper.writeValueAsString(inputTrack);
        String outputJson = mapper.writeValueAsString(outputTrack);
        when(trackRepository.save(inputTrack)).thenReturn(outputTrack);

        mockMvc.perform(post("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumRecommendedById() throws Exception{
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputTrack2);
        when(trackRepository.findById(id)).thenReturn(Optional.of(outputTrack2));

        mockMvc.perform(get("/track/8",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith404WhenGetByNotFoundIdthrowsException() throws Exception{
        Integer id = 888;

        when(trackRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/track/8",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {

        List<Track> outputList = Arrays.asList(outputTrack,outputTrack2);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(trackRepository).findAll();

        mockMvc.perform(get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturn204OnDelete() throws Exception {
        Integer id = 123;
        String outputJson = mapper.writeValueAsString(outputTrack2);
        when(trackRepository.findById(id)).thenReturn(Optional.of(outputTrack2));
        mockMvc.perform(delete("/track/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        int id = 5;
        String inputJson = mapper.writeValueAsString(inputTrack);

        mockMvc.perform(put("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingAlbumRecomendation() throws Exception {
        // Arrange
        doReturn(Optional.ofNullable(null)).when(trackRepository).findById(134);

        // Act
        mockMvc.perform(
                        delete("/track/134"))
                .andDo(print())
                .andExpect(status().isNotFound()); // Assert return 404 NOT_FOUND
    }
}