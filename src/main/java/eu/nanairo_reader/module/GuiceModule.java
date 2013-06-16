package eu.nanairo_reader.module;

import com.google.inject.AbstractModule;

import eu.nanairo_reader.service.RssService;
import eu.nanairo_reader.service.RssServiceImpl;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(RssService.class).to(RssServiceImpl.class);
	}
}