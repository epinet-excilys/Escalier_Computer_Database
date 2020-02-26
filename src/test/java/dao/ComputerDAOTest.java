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
		} catch (SQLException e) {
			fail("L'acces à la BDD est impossible");
		}

	}

	@Test
	public void testAddComputerIntendedUse() {
		Company company = new Company.builder().setIdBuild(1).build();
		Computer computer = new Computer.ComputerBuilder().setNameBuild("Nom")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().create(computer) == INTENDED_USE_RETURN_VALUE);
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
			assertTrue(ComputerDAO.getInstance().create(computer) == WRONG_USE_RETURN_VALUE);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testModifComputerIntendedUse() {
		Company company = new Company.builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == INTENDED_USE_RETURN_VALUE);
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
			assertTrue(ComputerDAO.getInstance().update(computer) == WRONG_USE_RETURN_VALUE);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testModifComputerGreaterId() {
		Company company = new Company.builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == WRONG_USE_RETURN_VALUE);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testDeleteComputerIntendedUse() {
		Company company = new Company.builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == INTENDED_USE_RETURN_VALUE);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testDeleteComputerGreaterId() {
		Company company = new Company.builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.ComputerBuilder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(LocalDate.now().minusYears(5)).setDiscoDateBuild(LocalDate.now().minusYears(1))
				.setIdCompagnyBuild(company).build();

		try {
			assertTrue(ComputerDAO.getInstance().update(computer) == WRONG_USE_RETURN_VALUE);
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
			assertTrue(ComputerDAO.getInstance().update(computer) == WRONG_USE_RETURN_VALUE);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Ajout n'a pas marcher à la BDD est impossible");
		}

	}

	@Test
	public void testFindComputerIntendeduse() {
		Company company = new Company.builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
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
	public void testFindIdEqualZero() {

		try {
			assertFalse(ComputerDAO.getInstance().find(0).isPresent());
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} catch (NoSuchElementException e2) {
			fail("Optional n'est pas correctement implémenter");
		}

	}
	
	@Test
	public void testFindAllPaginateCorrectSize() {

		try {
			assertTrue(ComputerDAO.getInstance().findAllPaginate(0,TAILLE_PAGE).size()==TAILLE_PAGE);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} 

	}
	
	@Test
	public void testFindAllPaginateWrongEntry() {
		List<Computer> computers = new ArrayList<>(); 
		try {
			computers = ComputerDAO.getInstance().findAllPaginate(-5,TAILLE_PAGE);
			assertTrue(computers.size()==TAILLE_PAGE);
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} 

	}
	
	@Test
	public void testFindAllPaginateAllComputersListAreEquals() {
		
		try {
			List<Computer> computersBDD = ComputerDAO.getInstance().findAllPaginate(0,TAILLE_PAGE);
			List<Computer> computersAdd = getTheFirst10Computers();
			
			
			assertEquals(computersBDD,computersAdd);
			
		} catch (SQLException e1) {
			fail("L'acces à la BDD est impossible");
		} 

	}
	
	public List<Computer> getTheFirst10Computers(){
		List<Computer> computers = new ArrayList<>();
		Company company1 = new Company.builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Company company2 = new Company.builder().setIdBuild(2).setNameBuild("Thinking Machines").build();
		Company company3 = new Company.builder().setIdBuild(1).setNameBuild("RCA").build();
		Company company4 = new Company.builder().setIdBuild(1).setNameBuild("Netronics").build();
		Company companyNull = new Company.builder().build();
		Computer computer1 = new Computer.ComputerBuilder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroDateBuild(null).setDiscoDateBuild(null)
				.setIdCompagnyBuild(company1).build();
		computers.add(computer1);
		Computer computer2 = new Computer.ComputerBuilder().setIdBuild(2).setNameBuild("CM-2a")
				.setIntroDateBuild(null).setDiscoDateBuild(null)
				.setIdCompagnyBuild(company2).build();
		computers.add(computer2);
		Computer computer3 = new Computer.ComputerBuilder().setIdBuild(3).setNameBuild("CM-200")
				.setIntroDateBuild(null).setDiscoDateBuild(null)
				.setIdCompagnyBuild(company2).build();
		computers.add(computer3);
		Computer computer4 = new Computer.ComputerBuilder().setIdBuild(4).setNameBuild("CM-5e")
				.setIntroDateBuild(null).setDiscoDateBuild(null)
				.setIdCompagnyBuild(company2).build();
		computers.add(computer4);
		Computer computer5 = new Computer.ComputerBuilder().setIdBuild(5).setNameBuild("CM-5")
				.setIntroDateBuild(ComputerMapper.getInstance().fromStringToLocalDate("1991-01-01"))
				.setDiscoDateBuild(null)
				.setIdCompagnyBuild(company2).build();
		computers.add(computer5);
		Computer computer6 = new Computer.ComputerBuilder().setIdBuild(6).setNameBuild("MacBook Pro")
				.setIntroDateBuild(ComputerMapper.getInstance().fromStringToLocalDate("2006-01-10"))
				.setDiscoDateBuild(null)
				.setIdCompagnyBuild(company1).build();
		computers.add(computer6);
		Computer computer7 = new Computer.ComputerBuilder().setIdBuild(7).setNameBuild("Apple IIe")
				.setIntroDateBuild(null)
				.setDiscoDateBuild(null)
				.setIdCompagnyBuild(companyNull).build();
		computers.add(computer7);
		Computer computer8 = new Computer.ComputerBuilder().setIdBuild(8).setNameBuild("Apple IIc")
				.setIntroDateBuild(null)
				.setDiscoDateBuild(null)
				.setIdCompagnyBuild(companyNull).build();
		computers.add(computer8);
		Computer computer9 = new Computer.ComputerBuilder().setIdBuild(9).setNameBuild("Apple IIGS")
				.setIntroDateBuild(null)
				.setDiscoDateBuild(null)
				.setIdCompagnyBuild(companyNull).build();
		computers.add(computer9);
		Computer computer10 = new Computer.ComputerBuilder().setIdBuild(10).setNameBuild("Apple IIc Plus")
				.setIntroDateBuild(null)
				.setDiscoDateBuild(null)
				.setIdCompagnyBuild(companyNull).build();
		computers.add(computer10);
		return computers;
	}
	
	

}
