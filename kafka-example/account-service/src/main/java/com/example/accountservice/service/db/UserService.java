package com.example.accountservice.service.db;

import com.example.accountservice.adapter.dto.request.UserRequestDto;
import com.example.accountservice.dao.entity.UserEntity;

public interface UserService {

    UserEntity saveUser(UserRequestDto userRequestDto);
}
