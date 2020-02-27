package dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mapper.ComputerMapper;
import model.Company;
import model.Computer;

public class ComputerDAOTest {

	private final int INTENDED_USE_RETURN_VALUE = 1;
	private final int WRONG_USE_RETURN_VALUE = 0;
	private final int TAILLE_PAGE = 10;

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
			assertTrue(ComputerDAO.getInstance().getNbRow() == 50);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testAddComputerIntendedUse() {
		Company company = new Company.Builder().setIdBuild(1).build();
		Computer computer = new Computer.Builder().setNameBuild("Nom")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().create(computer) == INTENDED_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testAddComputerNull() {
		Computer computer = new Computer.Builder().build();

		try {
			assertTrue(ComputerDAO.getInstance().create(computer) == WRONG_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testModifComputerIntendedUse() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == INTENDED_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testModifComputerNull() {
		Computer computer = new Computer.Builder().build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == WRONG_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testModifComputerGreaterId() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == WRONG_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testDeleteComputerIntendedUse() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == INTENDED_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testDeleteComputerGreaterId() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == WRONG_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testDeleteComputerNullId() {
		Computer computer = new Computer.Builder().build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == WRONG_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testFindComputerIntendeduse() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().findByID(1).get().equals(computer));
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testFindIdEqualZero() {

		try {
			assertFalse(ComputerDAO.getInstance().findByID(0).isPresent());
		} catch (NoSuchElementException e2) {
			fail("Optional n'est pas correctement implémenter");
		}

	}

	@Test
	public void testFindAllPaginateCorrectSize() {

		assertTrue(ComputerDAO.getInstance().findAllPaginate(0, TAILLE_PAGE).size() == TAILLE_PAGE);

	}

	@Test
	public void testFindAllPaginateWrongEntry() {
		List<Computer> computers = new ArrayList<>();
		computers = ComputerDAO.getInstance().findAllPaginate(-5, TAILLE_PAGE);
		assertTrue(computers.size() == TAILLE_PAGE);
	}

	@Test
	public void testFindAllPaginateAllComputersListAreEquals() {
		List<Computer> computersBDD = ComputerDAO.getInstance().findAllPaginate(0, TAILLE_PAGE);
		List<Computer> computersAdd = getTheFirst10Computers();
		assertEquals(computersBDD, computersAdd);

	}

	public List<Computer> getTheFirst10Computers() {
		List<Computer> computers = new ArrayList<>();
		Company company1 = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Company company2 = new Company.Builder().setIdBuild(2).setNameBuild("Thinking Machines").build();
		Company company3 = new Company.Builder().setIdBuild(1).setNameBuild("RCA").build();
		Company company4 = new Company.Builder().setIdBuild(1).setNameBuild("Netronics").build();
		Company companyNull = new Company.Builder().build();
		Computer computer1 = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(company1).build();
		computers.add(computer1);
		Computer computer2 = new Computer.Builder().setIdBuild(2).setNameBuild("CM-2a").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computers.add(computer2);
		Computer computer3 = new Computer.Builder().setIdBuild(3).setNameBuild("CM-200").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computers.add(computer3);
		Computer computer4 = new Computer.Builder().setIdBuild(4).setNameBuild("CM-5e").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computers.add(computer4);
		Computer computer5 = new Computer.Builder().setIdBuild(5).setNameBuild("CM-5")
				.setIntroducedDateBuild(ComputerMapper.getInstance().fromStringToLocalDate("1991-01-01"))
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computers.add(computer5);
		Computer computer6 = new Computer.Builder().setIdBuild(6).setNameBuild("MacBook Pro")
				.setIntroducedDateBuild(ComputerMapper.getInstance().fromStringToLocalDate("2006-01-10"))
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company1).build();
		computers.add(computer6);
		Computer computer7 = new Computer.Builder().setIdBuild(7).setNameBuild("Apple IIe")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(companyNull).build();
		computers.add(computer7);
		Computer computer8 = new Computer.Builder().setIdBuild(8).setNameBuild("Apple IIc")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(companyNull).build();
		computers.add(computer8);
		Computer computer9 = new Computer.Builder().setIdBuild(9).setNameBuild("Apple IIGS")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(companyNull).build();
		computers.add(computer9);
		Computer computer10 = new Computer.Builder().setIdBuild(10).setNameBuild("Apple IIc Plus")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(companyNull).build();
		computers.add(computer10);
		return computers;
	}

}
