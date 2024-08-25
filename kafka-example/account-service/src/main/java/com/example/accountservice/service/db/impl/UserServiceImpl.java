package com.example.accountservice.service.db.impl;

import com.example.accountservice.adapter.dto.request.UserRequestDto;
import com.example.accountservice.dao.entity.UserEntity;
import com.example.accountservice.dao.repository.UserRepository;
import com.example.accountservice.service.db.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity saveUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = userRepository.findByMail(userRequestDto.getMail());
        if(userEntity != null) {
            log.warn("user with this mail already exists");
            return userEntity;
        }
        userEntity = UserEntity.builder()
                .name(userRequestDto.getName())
                .lastName(userRequestDto.getLastName())
                .gender(userRequestDto.getGender())
                .surname(userRequestDto.getSurname())
                .jobTitle(userRequestDto.getJobTitle())
                .dateOfBirth(convertToDateFormat(userRequestDto.getDateOfBirth()))
                .dateOfIssue(convertToDateFormat(userRequestDto.getDocument().getDateOfIssue()))
                .documentNumber(userRequestDto.getDocument().getNumber())
                .documentSeries(userRequestDto.getDocument().getSeries())
                .mail(userRequestDto.getMail())
                .build();
        return userRepository.save(userEntity);
    }

    private LocalDate convertToDateFormat(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
