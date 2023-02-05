package com.udacity.jdnd.course3.critter.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Getter
@Setter
@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "no content available")
public class NoContentException extends RuntimeException {
}

