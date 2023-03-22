package com.example.postof.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason ="Service in progress. Please wait a few minutes.")
public class NotReadyException extends Exception {


	private static final long serialVersionUID = 1L;

}
