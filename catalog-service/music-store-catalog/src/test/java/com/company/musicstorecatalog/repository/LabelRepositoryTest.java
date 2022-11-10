package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Label;
import org.junit.After;
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
@SpringBootTest()
public class LabelRepositoryTest {
    @Autowired
    LabelRepository labelRepository;
    @Before
    public void setUp() throws Exception{
        labelRepository.deleteAll();
    }
    @After
    public void cleanUp() throws Exception{
        labelRepository.deleteAll();


    }
    @Test
    public void shouldCreateGetAndDeleteLabel() {
        Label newLabel = new Label("Big Machine", "bigMachine.com");
        newLabel = labelRepository.save(newLabel);
        Label foundLabel = labelRepository.findById(newLabel.getId()).get();

        assertEquals(newLabel, foundLabel);

        labelRepository.deleteById(newLabel.getId());

        Optional<Label> shouldBeEmptyOptional = labelRepository.findById(newLabel.getId());

        assertEquals(false, shouldBeEmptyOptional.isPresent());

    }

    @Test
    public void shouldFindAllLabel(){
        Label newLabel = new Label("Big Machine", "bigMachine.com");
        Label newLabel2 = new Label("good music records", "www.gmr.com");

        newLabel = labelRepository.save(newLabel);
        newLabel2 = labelRepository.save(newLabel2);
        List<Label> allLabelList = new ArrayList<>();
        allLabelList.add(newLabel);
        allLabelList.add(newLabel2);

        List<Label> foundAll = labelRepository.findAll();
        assertEquals(allLabelList.size(), foundAll.size());

    }

}