package com.example.systemrezerwacji.domain.salonModule;

import com.example.systemrezerwacji.domain.codeModule.CodeFacade;
import com.example.systemrezerwacji.domain.codeModule.message.ConsumeMessage;
import com.example.systemrezerwacji.domain.salonModule.dto.CreateNewSalonDto;
import com.example.systemrezerwacji.domain.salonModule.exception.SalonCreationException;
import com.example.systemrezerwacji.domain.userModule.User;
import com.example.systemrezerwacji.domain.userModule.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
class SalonCreator {
    private final SalonValidator validator;
    private final UserFacade userFacade;
    private final CodeFacade codeFacade;
    private final SalonService salonService;

    public Long create(CreateNewSalonDto salonDto) throws SalonCreationException {
        validateSalonDto(salonDto);

        User user = findUser(salonDto.userId());
        verifyCode(salonDto.code(), user);

        User owner = assignOwnerRole(user);
        return salonService.createNewSalon(salonDto, Optional.of(owner));
    }

    private void validateSalonDto(CreateNewSalonDto salonDto) throws SalonCreationException {
        String validationMessage = validator.validate(salonDto).validationMessage();
        if (!SalonValidationResult.SUCCESS_MESSAGE.equals(validationMessage)) {
            throw new SalonCreationException(validationMessage);
        }
    }

    private User findUser(String userId) throws SalonCreationException {
        return userFacade.getUserToCreateSalon(Long.valueOf(userId))
                .orElseThrow(() -> new SalonCreationException("User not found"));
    }

    private void verifyCode(String code, User user) throws SalonCreationException {
        ConsumeMessage consumeMessage = codeFacade.consumeCode(code, user);
        if (!consumeMessage.isSuccess()) {
            throw new SalonCreationException(consumeMessage.message());
        }
    }

    private User assignOwnerRole(User user) throws SalonCreationException {
        return userFacade.addUserRoleOwner(user.getId())
                .orElseThrow(() -> new SalonCreationException("Failed to assign owner role"));
    }
}
