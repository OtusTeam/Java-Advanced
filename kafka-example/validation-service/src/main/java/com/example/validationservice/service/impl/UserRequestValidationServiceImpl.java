package com.example.validationservice.service.impl;

import com.example.validationservice.adapter.dto.request.UserDto;
import com.example.validationservice.adapter.dto.response.ErrorResponseDto;
import com.example.validationservice.service.UserRequestValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRequestValidationServiceImpl implements UserRequestValidationService {

    private final Validator validator;

    @Override
    public ErrorResponseDto validateUserRequests(UserDto userRequestDto) {
        ErrorResponseDto validationRes = null;
        final BeanPropertyBindingResult errors =
                new BeanPropertyBindingResult(userRequestDto, "userRequestDto");
        validator.validate(userRequestDto, errors);
        if(errors.hasErrors()) {
            validationRes = new ErrorResponseDto();
            validationRes.setUserRequestId(userRequestDto.getRequestId());
            validationRes.setErrorFields(errors.getFieldErrors().stream().map(FieldError::getField).toList());
            Map<Object, String> map = errors.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            validationRes.setMessage("Запрос requestId = " + userRequestDto.getRequestId() +
                    " не прошел валидацию по причине - данные поля не заполнены: " + map.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining(", ")));
        }
        return validationRes;
    }
}
