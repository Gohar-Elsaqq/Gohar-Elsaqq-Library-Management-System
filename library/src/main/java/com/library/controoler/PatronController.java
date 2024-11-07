package com.library.controoler;

import com.library.dto.BookDTO;
import com.library.dto.PatronDTO;
import com.library.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patron")
public class PatronController {
    @Autowired
    private PatronService patronService;

    @GetMapping
    public List<PatronDTO> getAllPatron() throws Exception {
        return patronService.getAllPatron();
    }

    @GetMapping("/{id}")
    private PatronDTO getById(@PathVariable Long id){
        return patronService.getById(id);
    }
    @PostMapping
    private PatronDTO save (@RequestBody PatronDTO patronDTO) throws Exception {
        return patronService.save(patronDTO);
    }
    @PutMapping()
    public PatronDTO updatePatron(@RequestBody PatronDTO patronDTO) throws Exception {
//            bookDTO.setId(id);
        return patronService.save(patronDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) throws Exception {
        String result = patronService.deleteById(id);
        return ResponseEntity.ok(result);
    }
}
