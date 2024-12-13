package com.example.systemrezerwacji.domain.code_module;

public class CodeConfiguration {

    public CodeFacade createForTest(CodeRepository codeRepository) {
        CodeService codeService = new CodeService(codeRepository);
        return new CodeFacade(codeService);
    }
}
