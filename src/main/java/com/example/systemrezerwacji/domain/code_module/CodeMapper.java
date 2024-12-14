package com.example.systemrezerwacji.domain.code_module;

import com.example.systemrezerwacji.domain.code_module.dto.CodeDto;

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
