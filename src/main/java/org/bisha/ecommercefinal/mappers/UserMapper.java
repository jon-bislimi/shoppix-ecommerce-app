package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.UserDto;
import org.bisha.ecommercefinal.models.User;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends SimpleMapper<User, UserDto> {
}