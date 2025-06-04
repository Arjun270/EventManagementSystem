package com.ems.UserService.Utility;

import com.ems.UserService.Dto.UserDto;
import com.ems.UserService.Entity.User;

public class UserEntityDtoConversion {
	//EntityToDto Conversion
	public static UserDto entityToDto(User user) {
	    return UserDto.builder()
	            .name(user.getName())
	            .email(user.getEmail())
	            .contact(user.getContact())
	            .build();
	}


    //DtoToEntity Conversion
    public static User dtoToEntity(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail().toLowerCase())
                .password(PasswordHandler.hashPassword(userDto.getPassword())) 
                .contact(userDto.getContact())
                .build();
    }
}
