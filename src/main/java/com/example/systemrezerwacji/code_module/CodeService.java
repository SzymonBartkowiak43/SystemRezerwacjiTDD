package com.example.systemrezerwacji.code_module;

import com.example.systemrezerwacji.code_module.dto.CodeDto;
import com.example.systemrezerwacji.user_module.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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

    @Transactional
    public ConsumeMessage consumeCode(String codeValue, User user) {
        Optional<Code> codeOptional = codeRepository.findByCode(codeValue);

        if (codeOptional.isEmpty()) {
            return ConsumeMessage.failure("Code not found.");
        }

        Code code = codeOptional.get();

        if(!code.getIsConsumed()) {
            code.setConsumed();
            code.setDataConsumption();
            code.setUser(user);
            codeRepository.save(code);
            return ConsumeMessage.success();
        } else {
            return ConsumeMessage.failure("Code is already consumed!!!");
        }

    }

}
