package ru.work.documents.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.work.documents.exception.DocumentsRuntimeException;

import java.util.concurrent.Callable;

@Service
@RequiredArgsConstructor
public class TransactionHelper {

	private final EntityManager entityManager;

	public <T> T execute(Callable<T> callable) {
		entityManager.getTransaction().begin();

		T result;
		try {
			result = callable.call();
			entityManager.getTransaction().commit();
		} catch (DocumentsRuntimeException e) {
			entityManager.getTransaction().rollback();
			throw e;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new DocumentsRuntimeException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e);
		}

		return result;
	}

	public void execute(Runnable runnable) {
		entityManager.getTransaction().begin();

		try {
			runnable.run();
			entityManager.getTransaction().commit();
		} catch (DocumentsRuntimeException e) {
			entityManager.getTransaction().rollback();
			throw e;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new DocumentsRuntimeException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
}
