package com.example.systemrezerwacji.domain.codeModule;

import com.example.systemrezerwacji.domain.codeModule.dto.CodeDto;
import com.example.systemrezerwacji.domain.codeModule.message.ConsumeMessage;
import com.example.systemrezerwacji.domain.userModule.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class CodeFacade {
    private final CodeService codeService;

    @Transactional
    public CodeDto generateNewCode() {
        return codeService.generateNewCode();
    }

    @Transactional
    public ConsumeMessage consumeCode(String codeValue, User user) {
        return codeService.consumeCode(codeValue, user);
    }


}
