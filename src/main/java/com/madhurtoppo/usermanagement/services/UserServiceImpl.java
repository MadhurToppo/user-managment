package com.madhurtoppo.usermanagement.services;

import com.madhurtoppo.usermanagement.dtos.Mapper;
import com.madhurtoppo.usermanagement.dtos.UserDto;
import com.madhurtoppo.usermanagement.dtos.UsersDto;
import com.madhurtoppo.usermanagement.exceptions.InvalidArgumentException;
import com.madhurtoppo.usermanagement.exceptions.UserAlreadyExistsException;
import com.madhurtoppo.usermanagement.exceptions.UserNotFoundException;
import com.madhurtoppo.usermanagement.model.User;
import com.madhurtoppo.usermanagement.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** UserServiceImpl */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  private final Mapper mapper;

  @Override
  public Long createUser(final UserDto userDto) {
    if (repository.existsByNameContainingIgnoreCase(userDto.name())) {
      throw new UserAlreadyExistsException(userDto.name());
    }
    final User user = mapper.toEntity(userDto);
    final User savedUser = repository.save(user);
    return savedUser.getId();
  }

  @Override
  public UserDto getUser(final long id) {
    final User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    final UserDto userDto = mapper.toDto(user);
    return userDto;
  }

  @Override
  public UsersDto getAllUsers() {
    final List<UserDto> users = repository.findAll().stream().map(user -> mapper.toDto(user)).toList();
    final UsersDto usersDto = new UsersDto(users);
    return usersDto;
  }

  @Override
  public String update(final UserDto userDto, final long id) {
    if (userDto.name() == null || userDto.age() < 0 || userDto.city() == null) {
      throw new InvalidArgumentException();
    }
    final User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    user.setName(userDto.name());
    user.setAge(userDto.age());
    user.setCity(userDto.city());
    repository.save(user);
    return "Successfully updated.";
  }

  @Override
  public String delete(final long id) {
    final User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    repository.delete(user);
    return "Successfully deleted";
  }
}
