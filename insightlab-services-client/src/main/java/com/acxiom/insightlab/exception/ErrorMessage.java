package com.acxiom.insightlab.exception;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author dmytro.plekhotkin
 * 
 */
final class ErrorMessage {
	/**
	 * Slf4j logger.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ErrorMessage.class);
	/**
	 * Constant. Represents message key in error's json object.
	 */
	private static final String MESSAGE_KEY = "message";
	/**
	 * Constant. Represents status code key in error's json object.
	 */
	private static final String CODE_KEY = "code";

	/**
	 * Constant. Represents stack trace key in error's json object.
	 */
	private static final String STACK_TRACE_KEY = "stackTrace";
	/**
	 * Constant. Represents error key in json object.
	 */
	private static final String ERROR_KEY = "error";

	/**
	 * User friendly message.
	 */
	private String message;
	/**
	 * Status code.
	 */
	private int code;
	/**
	 * Message designed for developers only.
	 */
	private String developerMessage;

	/**
	 * Stack trace.
	 */
	private StackTraceElement[] stackTrace;

	/**
	 * Public constructor. Initializes message field.
	 * 
	 * @param message
	 *            User friendly message
	 */
	public ErrorMessage(final String message) {
		this.message = message;
	}

	/**
	 * Public constructor. Initializes message and code fields.
	 * 
	 * @param message
	 * @param code
	 */
	public ErrorMessage(final String message, final int code) {
		this(message);
		this.code = code;
	}

	/**
	 * Public constructor. Initializes message, developerMessage and code
	 * fields.
	 * 
	 * @param message
	 * @param developerMessage
	 * @param code
	 */
	public ErrorMessage(final String message, final String developerMessage,
			final int code) {
		this(message, code);
		this.developerMessage = developerMessage;
	}

	/**
	 * Public constructor. Initializes message, developerMessage, code and stack
	 * trace fields.
	 * 
	 * @param message
	 * @param developerMessage
	 * @param code
	 * @param stackTrace
	 */
	public ErrorMessage(final String message, final String developerMessage,
			final int code, final StackTraceElement[] stackTrace) {
		this(message, developerMessage, code);
		this.stackTrace = (StackTraceElement[]) stackTrace.clone();
	}

	/**
	 * Builds error message json object that contains useful info about
	 * exception.
	 * 
	 * @return JSONObject that represents error message.
	 */
	public JSONObject buildMessage(final boolean isDebug) {
		final JSONObject messageObject = new JSONObject();

		try {
			final JSONObject error = new JSONObject();

			if (isDebug) {
				message = developerMessage;
			}

			if (message != null) {
				error.put(MESSAGE_KEY, message);
			}

			if (code != 0) {
				error.put(CODE_KEY, code);
			}

			if (stackTrace != null && stackTrace.length > 0) {
				final StringBuffer stackTraceBuffer = new StringBuffer();
				for (int i = 0; i < stackTrace.length; i++) {
					stackTraceBuffer.append(stackTrace[i]);
				}
				error.put(STACK_TRACE_KEY, stackTraceBuffer.toString());
			}

			messageObject.put(ERROR_KEY, error);

		} catch (JSONException e) {
			LOGGER.error("Exception while building error message!", e);
		}
		return messageObject;
	}

	/**
	 * Gets user friendly message.
	 * 
	 * @return message User friendly message designed for users
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets user friendly message.
	 * 
	 * @param message
	 *            User friendly message designed for users
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * Gets error's status code.
	 * 
	 * @return Status code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets error's status code.
	 * 
	 * @param code
	 *            Status code
	 */
	public void setCode(final int code) {
		this.code = code;
	}

	/**
	 * Gets raw error message.
	 * 
	 * @return Error message designed for developers
	 */
	public String getDeveloperMessage() {
		return developerMessage;
	}

	/**
	 * Sets raw error message.
	 * 
	 * @param developerMessage
	 *            Error message designed for developers
	 */
	public void setDeveloperMessage(final String developerMessage) {
		this.developerMessage = developerMessage;
	}

	/**
	 * Gets stack trace.
	 * 
	 * @return Stack trace
	 */
	public StackTraceElement[] getStackTrace() {
		return (StackTraceElement[]) stackTrace.clone();
	}

	/**
	 * Sets stack trace.
	 * 
	 * @param stackTrace
	 *            Error's stack trace
	 */
	public void setStackTrace(final StackTraceElement[] stackTrace) {
		this.stackTrace = (StackTraceElement[]) stackTrace.clone();
	}
}