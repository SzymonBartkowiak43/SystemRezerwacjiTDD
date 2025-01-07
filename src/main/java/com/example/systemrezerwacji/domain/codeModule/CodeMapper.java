package com.example.systemrezerwacji.domain.codeModule;

import com.example.systemrezerwacji.domain.codeModule.dto.CodeDto;

class CodeMapper {
    static CodeDto toDto(Code code) {
        return new CodeDto(
                code.getCode(),
                code.getIsConsumed(),
                code.getDataGenerated(),
                code.getUser() != null ? code.getUser().getId() : null
        );
    }
}
