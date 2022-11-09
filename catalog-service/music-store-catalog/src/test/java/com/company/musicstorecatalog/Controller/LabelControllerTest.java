package com.company.musicstorecatalog.Controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
@WebMvcTest(LabelController.class)
public class LabelControllerTest {


    @MockBean
    private LabelRepository labelRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private Label inputLabel;
    private Label inputLabelWithId;
    private String inputJson;
    private Label outputLabel;
    private Label outputLabel2;



    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        inputLabel = new Label("Big Machine", "bigMachine.com");
       inputLabelWithId = new Label(1,"Big Machine", "bigMachine.com");
        outputLabel = new Label(1,"Big Machine", "bigMachine.com");
        outputLabel2 = new Label(5,"good music records", "www.gmr.com");

    }
    @Test
    public void shouldReturn201AndOnPost() throws Exception{
        inputJson = mapper.writeValueAsString(inputLabel);
        String outputJson = mapper.writeValueAsString(outputLabel);
        when(labelRepository.save(inputLabel)).thenReturn(outputLabel);

        mockMvc.perform(post("/label")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumRecommendedById() throws Exception{
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputLabel2);
        when(labelRepository.findById(id)).thenReturn(Optional.of(outputLabel2));

        mockMvc.perform(get("/label/8",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith404WhenGetByNotFoundIdthrowsException() throws Exception{
        Integer id = 888;

        when(labelRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/label/8",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {

        List<Label> outputList = Arrays.asList(outputLabel,outputLabel2);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(labelRepository).findAll();

        mockMvc.perform(get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturn204OnDelete() throws Exception {
        Integer id = 123;
        String outputJson = mapper.writeValueAsString(outputLabel2);
        when(labelRepository.findById(id)).thenReturn(Optional.of(outputLabel2));
        mockMvc.perform(delete("/label/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        int id = 5;
        String inputJson = mapper.writeValueAsString(inputLabel);

        mockMvc.perform(put("/label")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingAlbumRecomendation() throws Exception {
        // Arrange
        doReturn(Optional.ofNullable(null)).when(labelRepository).findById(134);

        // Act
        mockMvc.perform(
                        delete("/label/134"))
                .andDo(print())
                .andExpect(status().isNotFound()); // Assert return 404 NOT_FOUND
    }
}