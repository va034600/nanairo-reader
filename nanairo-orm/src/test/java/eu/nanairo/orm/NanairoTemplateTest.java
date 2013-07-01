package eu.nanairo.orm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class NanairoTemplateTest {
	@Test
	public void testConvertColumns_01() {
		String[] columns = NanairoTemplate.convertColumns(SampleEntity.class);
		assertNotNull(columns[0]);
		// TODO
		// assertEquals("ID", columns[0]);
		// assertEquals("TITLE", columns[1]);
		// assertEquals("NEW_TITLE", columns[2]);
	}

	@Test
	public void testGetSqlForInsert_01() {
		SampleEntity sampleEntity = new SampleEntity();
		sampleEntity.setId(3);
		sampleEntity.setTitle("abc");
		String sql = NanairoTemplate.getSqlForInsert(SampleEntity.class, sampleEntity);
		assertEquals("INSERT INTO SAMPLE (ID, TITLE) VALUES (?, ?)", sql);
	}

}