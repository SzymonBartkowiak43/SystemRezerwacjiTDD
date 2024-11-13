package com.example.systemrezerwacji.code_module;

import com.example.systemrezerwacji.code_module.dto.CodeDto;
import com.example.systemrezerwacji.user_module.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CodeFacade {
    private final CodeService codeService;

    public CodeFacade(CodeService codeService) {
        this.codeService = codeService;
    }

    public CodeDto generateNewCode() {
        return codeService.generateNewCode();
    }

    @Transactional
    public ConsumeMessage consumeCode(String codeValue, User user) {
        return codeService.consumeCode(codeValue, user);
    }


}
