package com.example.systemrezerwacji.infrastructure.restControllers;


import com.example.systemrezerwacji.domain.codeModule.CodeFacade;
import com.example.systemrezerwacji.domain.codeModule.dto.CodeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CodeController {
    private final CodeFacade codeFacade;

    @PostMapping("/generateCode")
    public ResponseEntity<CodeDto> generateCode() {
        CodeDto codeDto = codeFacade.generateNewCode();
        return ResponseEntity.status(HttpStatus.CREATED).body(codeDto);
    }

}
