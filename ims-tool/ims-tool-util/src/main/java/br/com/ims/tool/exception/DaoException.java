package br.com.ims.tool.exception;

import java.sql.SQLException;

public class DaoException extends SQLException {

	public DaoException(String message) {
		super(message);
	}

	public DaoException() {

	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
