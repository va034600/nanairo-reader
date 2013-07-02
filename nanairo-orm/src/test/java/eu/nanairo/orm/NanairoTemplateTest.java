package eu.nanairo.orm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class NanairoTemplateTest {
	@Test
	public void testConvertColumns_01() {
		String[] columns = NanairoTemplate.convertColumns(SampleNewEntity.class);
		assertNotNull(columns[0]);
		 assertEquals("ID", columns[0]);
		 assertEquals("TITLE", columns[1]);
		 assertEquals("NEW_TITLE", columns[2]);
	}

	@Test
	public void testGetSqlForInsert_01() {
		SampleNewEntity sampleEntity = new SampleNewEntity();
		sampleEntity.setId(3L);
		sampleEntity.setTitle("abc");
		String sql = NanairoTemplate.getSqlForInsert(SampleNewEntity.class, sampleEntity);
		assertEquals("INSERT INTO SAMPLE_NEW (ID, TITLE) VALUES (?, ?)", sql);
	}

	@Test
	public void testGetTableName_01() {
		String tableName = NanairoTemplate.getTableName(SampleNewEntity.class);
		assertEquals("SAMPLE_NEW", tableName);
	}

}