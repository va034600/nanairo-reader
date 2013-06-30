package eu.nanairo.orm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NanairoDaoSupportTest {
	@Test
	public void testConvertColumns_01() {
		String[] columns = NanairoDaoSupport.convertColumns(SampleEntity.class);
		assertEquals("ID", columns[0]);
		assertEquals("TITLE", columns[1]);
		assertEquals("NEW_TITLE", columns[2]);
	}
}