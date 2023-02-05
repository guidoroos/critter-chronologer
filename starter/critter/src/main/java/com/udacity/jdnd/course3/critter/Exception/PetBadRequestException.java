package com.udacity.jdnd.course3.critter.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Getter
@Setter
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "cannot save pet without owner specified")
public class PetBadRequestException extends RuntimeException {
}

