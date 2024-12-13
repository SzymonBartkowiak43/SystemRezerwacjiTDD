package com.example.systemrezerwacji.domain.code_module;

class CodeConfiguration {

    CodeFacade createForTest(CodeRepository codeRepository) {
        CodeService codeService = new CodeService(codeRepository);
        return new CodeFacade(codeService);
    }
}