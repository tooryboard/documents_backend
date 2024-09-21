package ru.work.documents.Initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.work.documents.config.WebConfig;
import ru.work.documents.controller.DocumentController;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {WebConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {DocumentController.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
}