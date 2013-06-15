package eu.nanairo_reader.module;

import com.google.inject.AbstractModule;

import eu.nanairo_reader.SampleService;
import eu.nanairo_reader.SampleServiceImpl;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(SampleService.class).to(SampleServiceImpl.class);
	}
}
