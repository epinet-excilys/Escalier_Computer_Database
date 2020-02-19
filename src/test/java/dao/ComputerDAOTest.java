package dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ComputerDAOTest {
	

	@Before
	public void setUp() throws Exception {
		System.setProperty("testState", "Running");
		
	}

	@After
	public void tearDown() throws Exception {
		System.setProperty("testState", "Ending");
	}

	@Test
	public void testgetNBRows() {
		try {
			assertTrue(ComputerDAO.getInstance().getNbRow()==50);
		} catch (SQLException e) {
			fail("L'acces à la BDD est impossible");
		}

	}

}
