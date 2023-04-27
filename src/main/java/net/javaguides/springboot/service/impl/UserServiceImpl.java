package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistsException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //convert UserDto to User JPA Entity
//        User user= UserMapper.mapToUser(userDto);

//        User user= modelMapper.map(userDto,User.class);

        Optional<User> optionalUser=userRepository.findByEmail(userDto.getEmailAddress());

        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email already exists for User");
        }

        User user= AutoUserMapper.MAPPER.mapToUser(userDto);

        User savedUser=userRepository.save(user);

        //convert savedUser to UserDto
//        UserDto savedUserDto=UserMapper.mapToUserDto(savedUser);

//        UserDto savedUserDto=modelMapper.map(savedUser,UserDto.class);

        UserDto savedUserDto=AutoUserMapper.MAPPER.mapToUserDto(savedUser);

        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {

        User user=userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User","id",userId)
        );

//        return UserMapper.mapToUserDto(user.get());
//        return modelMapper.map(user.get(),UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users=userRepository.findAll();

//        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
//        return users.stream().map((user) -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return users.stream().map((user) -> AutoUserMapper.MAPPER.mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        User existingUser=userRepository.findById(userDto.getId()).orElseThrow(
                ()->new ResourceNotFoundException("User","id",userDto.getId())
        );

        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmailAddress());

        User updatedUser=userRepository.save(existingUser);

//        return UserMapper.mapToUserDto(updatedUser);
//        return modelMapper.map(updatedUser,UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {

        User existingUser=userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User","id",userId)
        );

        userRepository.deleteById(userId);
    }
}
