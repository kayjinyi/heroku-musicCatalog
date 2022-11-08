package com.company.musicstorecatalog.Controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelRepository labelRepository;

    @GetMapping()
    public List<Label> getLabels(){
        return labelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Label getLabelById(@PathVariable Integer id){
        Optional<Label> returnVal = labelRepository.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "labelId '" + id + "' does not exist");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Label addLabel(@RequestBody Label label){
        return labelRepository.save(label);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody Label label){
        labelRepository.save(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Integer id){
        Optional<Label> label = labelRepository.findById(id);
        if(label.isPresent()) {
            labelRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "labelId '" + id + "' does not exist");
        }
    }



}
