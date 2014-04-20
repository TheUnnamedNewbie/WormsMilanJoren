package worms.model.containment;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TeamTest {

	
	private Team team;
	private World world;

	@Before
	public void setUp() throws Exception {
		world = new World();
		team = new Team("Help", world);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreation_LegalCase1() {
		world = new World();
		team = new Team("Help", world);
		assertEquals(team.getName(), "Help");
		assertEquals(team.getWorld(), world);
	
	}
	
	


}
