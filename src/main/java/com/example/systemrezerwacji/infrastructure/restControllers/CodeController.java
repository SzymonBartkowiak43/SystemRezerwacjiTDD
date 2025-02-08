package com.example.systemrezerwacji.infrastructure.restControllers;


import com.example.systemrezerwacji.domain.codeModule.CodeFacade;
import com.example.systemrezerwacji.domain.codeModule.dto.CodeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost")
@RequestMapping("/reservation-service/code")
public class CodeController {
    private final CodeFacade codeFacade;

    @PostMapping("/generateCode")
    public ResponseEntity<CodeDto> generateCode() {
        CodeDto codeDto = codeFacade.generateNewCode();
        return ResponseEntity.status(HttpStatus.CREATED).body(codeDto);
    }

    @GetMapping("/get-link-to-code")
    public ResponseEntity<String> getLinkToCode() {
//        String link = codeFacade.getLinkToCode();
        String link = "https://buy.stripe.com/test_bIYeXu5QW2za3D2aEF";
        return ResponseEntity.ok(link);
    }

}
