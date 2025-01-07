package com.example.systemrezerwacji.domain.codeModule;

class CodeConfiguration {

    CodeFacade createForTest(CodeRepository codeRepository) {
        CodeService codeService = new CodeService(codeRepository);
        return new CodeFacade(codeService);
    }
}
