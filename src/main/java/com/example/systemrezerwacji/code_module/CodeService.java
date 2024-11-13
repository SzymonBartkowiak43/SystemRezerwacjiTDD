package com.example.systemrezerwacji.code_module;

import com.example.systemrezerwacji.code_module.dto.CodeDto;
import com.example.systemrezerwacji.user_module.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import static com.example.systemrezerwacji.code_module.CodeError.*;

@Service
class CodeService {
    private final CodeRepository codeRepository;

    CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    CodeDto generateNewCode() {
        String uniqueCode = UUID.randomUUID().toString();
        Code newCode = new Code(uniqueCode);

        Code save = codeRepository.save(newCode);

        return CodeMapper.toDto(save);
    }

    ConsumeMessage consumeCode(String codeValue, User user) {
        return codeRepository.findByCode(codeValue)
                .map(code -> {
                    if(!code.getIsConsumed()) {
                        code.setConsumed();
                        code.setUser(user);
                        codeRepository.save(code);
                        return ConsumeMessage.success();
                    } else {
                        return ConsumeMessage.failure(CODE_ALREADY_CONSUMED.getMessage());
                    }
                })
                .orElseGet(() -> ConsumeMessage.failure(CODE_NOT_FOUND.getMessage()));
    }

}
