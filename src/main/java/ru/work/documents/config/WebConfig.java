package ru.work.documents.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.work.documents.serialization.LocalDateTimeDeserializer;
import ru.work.documents.serialization.LocalDateTimeSerializer;

import javax.servlet.MultipartConfigElement;
import java.time.LocalDateTime;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.work.documents")
public class WebConfig implements WebMvcConfigurer {

	@Bean
	ViewResolver viewResolver() {
		final FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(true);
		viewResolver.setSuffix(".html");
		return viewResolver;
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		return new MultipartConfigElement("");
	}

	@Bean
	MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000);
		return multipartResolver;
	}

	@Bean
	FreeMarkerConfigurer freeMarkerConfigurer() {
		final FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/freemarker/");
		return configurer;
	}

	@Bean
	ObjectMapper objectMapper() {
		return new Jackson2ObjectMapperBuilder()
				.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
				.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer())
				.build();
	}

	@Configuration
	static class FreeMarkerObjectWrapperConfig implements BeanPostProcessor {
		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
			if (bean instanceof FreeMarkerConfigurer) {
				final FreeMarkerConfigurer configurer = (FreeMarkerConfigurer) bean;
				configurer.getConfiguration().setObjectWrapper(new Java8ObjectWrapper(freemarker.template.Configuration.getVersion()));
			}

			return bean;
		}
	}
}