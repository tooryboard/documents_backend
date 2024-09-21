package ru.work.documents.config;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.work.documents.model.Document;
import ru.work.documents.model.DocumentRegCard;
import ru.work.documents.model.DocumentVersion;

@Configuration
public class HibernateConfig {

	@Bean
	SessionFactory sessionFactory() {
		final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().build();
		return new MetadataSources(serviceRegistry)
				.addAnnotatedClass(Document.class)
				.addAnnotatedClass(DocumentRegCard.class)
				.addAnnotatedClass(DocumentVersion.class)
				.getMetadataBuilder()
				.build()
				.getSessionFactoryBuilder()
				.build();
	}

	@Bean
	EntityManager entityManager() {
		return sessionFactory().createEntityManager();
	}
}
