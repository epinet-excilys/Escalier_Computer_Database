package dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Company;
import model.Computer;

public class ComputerDAOTest {

	private int intendedUseResult = 1;
	private int wrongUseResult = 0;

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
		} catch (SQLException e) {
			fail("L'acces à la BDD est impossible");
		}

	}

	@Test
	public void testAddComputerIntendedUse() {
		Company company = new Company.CompanyBuilder().setIdBuild(1).build();
		Computer computer = new Computer.ComputerBuilder().setNameBuild("Nom")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().create(computer) == intendedUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testAddComputerNull() {
		Computer computer = new Computer.ComputerBuilder().build();

		try {
			assertTrue(ComputerDAO.getInstance().create(computer) == wrongUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testModifComputerIntendedUse() {
		Company company = new Company.CompanyBuilder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == intendedUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testModifComputerNull() {
		Computer computer = new Computer.ComputerBuilder().build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == wrongUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testModifComputerGreaterId() {
		Company company = new Company.CompanyBuilder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == wrongUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testDeleteComputerIntendedUse() {
		Company company = new Company.CompanyBuilder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == intendedUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testDeleteComputerGreaterId() {
		Company company = new Company.CompanyBuilder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == wrongUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testDeleteComputerNullId() {
		Computer computer = new Computer.ComputerBuilder().build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == wrongUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testFindComputerIntendeduse() {
		Company company = new Company.CompanyBuilder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().find(1).get().equals(computer));
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}
	
	@Test
	public void testFindNullId() {
		Computer computer = new Computer.ComputerBuilder().build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == intendedUseResult);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}


}
