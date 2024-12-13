package com.example.systemrezerwacji.domain.code_module;

import com.example.systemrezerwacji.domain.code_module.dto.CodeDto;
import com.example.systemrezerwacji.domain.code_module.message.ConsumeMessage;
import com.example.systemrezerwacji.domain.user_module.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class CodeFacade {
    private final CodeService codeService;

    public CodeDto generateNewCode() {
        return codeService.generateNewCode();
    }

    @Transactional
    public ConsumeMessage consumeCode(String codeValue, User user) {
        return codeService.consumeCode(codeValue, user);
    }


}
