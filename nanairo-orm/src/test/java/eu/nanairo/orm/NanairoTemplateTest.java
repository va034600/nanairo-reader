package eu.nanairo.orm;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class NanairoTemplateTest {
	@Test
	public void testConvertColumns_01() {
		String[] columns = NanairoTemplate.convertColumns(SampleEntity.class);
		assertNotNull(columns[0]);
		//TODO
		// assertEquals("ID", columns[0]);
		// assertEquals("TITLE", columns[1]);
		// assertEquals("NEW_TITLE", columns[2]);
	}
}