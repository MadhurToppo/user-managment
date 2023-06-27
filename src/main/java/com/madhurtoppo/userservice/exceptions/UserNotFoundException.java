package com.madhurtoppo.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** UserNotFoundException */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(long id) {
    super("User with id '%d' not found!".formatted(id));
  }
}
