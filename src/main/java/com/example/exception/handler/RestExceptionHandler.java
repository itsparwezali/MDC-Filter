package com.example.exception.handler;


import com.example.error.ApiError;
import com.example.exception.*;
import com.example.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @Autowired
//    private MetaTableRepository metaTableRepository;

    /**
     * Handle MissingServletRequestParameterException. Triggered when a
     * 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers,
                                                                          HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
     * invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
     *                validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     * @Valid validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        printException(ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     * @Validated fails.
     */
//    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
//    protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
//        printException(ex);
//        ApiError apiError = new ApiError(BAD_REQUEST);
//        apiError.setMessage("Validation error");
//        apiError.addValidationErrors(ex.getConstraintViolations());
//        return buildResponseEntity(apiError);
//    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more
     * detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        printException(ex);
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles ResourceAlreadyExistException. Created to encapsulate errors with
     * more detail
     *
     * @param ex the ResourceAlreadyExistException
     * @return the ApiError object
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExist(ResourceAlreadyExistException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles UnauthorizedException. Created to encapsulate errors with
     * more detail
     *
     * @param ex the UnauthorizedException
     * @return the ApiError object
     */
    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthroizedLogin(UnauthorizedException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles MethodNotAllowedException. Created to encapsulate errors with
     * more detail
     *
     * @param ex the MethodNotAllowedException
     * @return the ApiError object
     */
    @ExceptionHandler(MethodNotAllowedException.class)
    protected ResponseEntity<Object> handleMethodNotallowed(MethodNotAllowedException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles PreconditionFailedException. Created to encapsulate errors with
     * more detail
     *
     * @param ex the PreconditionFailedException
     * @return the ApiError object
     */
    @ExceptionHandler(PreconditionFailedException.class)
    protected ResponseEntity<Object> handlePreconditionFailedException(MethodNotAllowedException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.PRECONDITION_FAILED);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles NotacceptedRequestException. Created to encapsulate errors with
     * more detail
     *
     * @param ex the NotacceptedRequestException
     * @return the ApiError object
     */
    @ExceptionHandler(NotacceptedRequestException.class)
    protected ResponseEntity<Object> handleNotAccepted(NotacceptedRequestException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles RuntimeException. Created to encapsulate errors with more detail
     *
     * @param ex the RuntimeException
     * @return the ApiError object
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is
     * malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    /**
     * Handle javax.persistence.EntityNotFoundException
     *
     * @param ex
     * @return
     */
//    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
//    protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
//        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex));
//    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different
     * DB causes.
     *
     * @param ex      the DataIntegrityViolationException
     * @param request
     * @return the ApiError object
     */
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
//        printException(ex);
//        if (ex.getCause() instanceof ConstraintViolationException) {
//            log.error("Exception : " + ex.getMessage());
//            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
//        }
//        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
//    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<Object> handleMultipartException(MaxUploadSizeExceededException ex, WebRequest request) {
        printException(ex);
        if (ex.getCause() instanceof MaxUploadSizeExceededException) {
            log.error("Exception : " + ex.getMessage());
            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "File size exceeds limit", ex.getCause()));
        }
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex      the Exception
     * @param request
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        printException(ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(),
                ex.getValue(), ex.getRequiredType().getSimpleName()));
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ResponseEntity<Object> buildServerResponseEntity(ServerResponse serverResponse) {
        return new ResponseEntity<>(serverResponse, serverResponse.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setSuccess(false);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        printException(ex);
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setSuccess(false);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

//    @ExceptionHandler(ReCaptchaNotFoundException.class)
//    protected ResponseEntity<Object> handleReCaptchaNotFoundException(ReCaptchaNotFoundException ex) {
//        printException(ex);
//        RecaptchaModel recaptchaModel = new RecaptchaModel();
//        recaptchaModel.setStatus("SHOW_RECAPTCHA");
//        recaptchaModel.setSitekey(AESEncryption.decryptText(metaTableRepository.getValueByName(MetaTableConstants.RECAPTCHA_SITE_KEY.getName())));
//        ServerResponse serverResponse = ServerResponse.builder()
//                .httpStatus(HttpStatus.BAD_REQUEST)
//                .success(false)
//                .message(ex.getMessage())
//                .data(recaptchaModel)
//                .build();
//        return buildServerResponseEntity(serverResponse);
//    }

//    @ExceptionHandler(RedirectToErrorPageException.class)
//    protected ModelAndView handleRedirectToExpetionPage(RedirectToErrorPageException ex) {
//        printException(ex);
//        String redirectUrl = "redirect:" + ex.getRedirectLink();
//        if (ex.getMessage() != null) {
//            redirectUrl += "?message=" + AESEncryption.encryptTextForBackwardEncryption(ex.getMessage());
//        }
//        return new ModelAndView(redirectUrl);
//    }

    @ExceptionHandler(RestTemplateException.class)
    protected ResponseEntity<Object> handleRestTemplateException(RestTemplateException ex) {
        printException(ex);
        ApiError apiError = new ApiError(ex.getHttpStatus());
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<Object> handleJwtTokenMissingException(ExpiredJwtException ex) {
//        printException(ex);
//        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
//        apiError.setMessage("Please log in again.");
//        apiError.setTitle("Session Expired");
//        return buildResponseEntity(apiError);
//    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("Internal Server Error.");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UserTemporarilyBlockedException.class)
    protected ResponseEntity<Object> handleUserTemporarilyBlockedException(UserTemporarilyBlockedException ex) {
        printException(ex);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

//    @ExceptionHandler(CustomValidationException.class)
//    protected ResponseEntity<Object> handleCustomValidationException(CustomValidationException ex) {
//        printException(ex);
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
//        apiError.setMessage(ex.getMessage());
//        apiError.addCustomValidationErrors(ex.getCustomFieldErrorList());
//        return buildResponseEntity(apiError);
//    }

    private void printException(Exception ex) {
        if (log.isDebugEnabled()) {
            log.error("Exception :: ", ex);
        }
    }

}
