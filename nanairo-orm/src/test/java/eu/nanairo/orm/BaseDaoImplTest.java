package eu.nanairo.orm;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import eu.nanairo.orm.BaseDaoImpl;

public class BaseDaoImplTest {
	@Test
	public void testConvertColumns_01() throws IOException {
		String[] columns = BaseDaoImpl.convertColumns(SampleEntity.class);
		assertEquals("ID", columns[0]);
		assertEquals("TITLE", columns[1]);
		assertEquals("NEW_TITLE", columns[2]);
	}
}