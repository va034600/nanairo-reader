package eu.nanairo.orm.dao;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class BaseDaoImplTest {
	@Test
	public void testGetMessage_01() throws IOException {
		String[] columns = BaseDaoImpl.convertColumns(SampleEntity.class);
		assertEquals("ID", columns[0]);
		assertEquals("TITLE", columns[1]);
		assertEquals("NEW_TITLE", columns[2]);
	}
}