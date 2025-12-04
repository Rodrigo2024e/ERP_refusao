
package com.smartprocessrefusao.erprefusao.resources.exceptions;

import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.IntegrityConstraintViolationException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), "Resource not found", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), "Database exception", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), "Erro ao inserir movimentação",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	// ======= Bean Validation (@NotNull, @Positive, @Size, etc.) =======
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError(Instant.now(), status.value(), "Validation exception",
				"Erro de validação nos campos", request.getRequestURI());

		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(IntegrityConstraintViolationException.class)
	public ResponseEntity<StandardError> integrityConstraint(IntegrityConstraintViolationException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), "Violação de integridade de dados",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {
		String error = "Violação de Integridade de Dados. O campo fornecido já existe no banco de dados.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	// ======= Erros de conversão JSON: Enviou texto em campo numérico, enum
	// inválido, "" etc. =======
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		String campo = "campo desconhecido";
		String mensagem = "Formato inválido nos dados enviados.";

		Throwable cause = e.getCause();

		// Detecta erro de formato: BigDecimal, Integer, Enum, etc.
		if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife) {
			if (!ife.getPath().isEmpty()) {
				campo = ife.getPath().get(0).getFieldName();
			}
			mensagem = "Formato inválido para o campo: " + campo;
		}

		// Monta a resposta padrão da API
		StandardError err = new StandardError(Instant.now(), status.value(), mensagem,
				e.getMostSpecificCause().getMessage(), // mensagem técnica
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}

}
