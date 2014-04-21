package worms.model.containment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.containment.Team;
import worms.containment.World;
import worms.model.Worm;

public class TeamTest {

	
	private Team team;
	private World world;
	private Worm worm1, worm2, worm3, worm4;
	private ArrayList<Worm> allworms;
	
//	@Before
//	public void setUp() throws Exception {
//		world = new World();
//		team = new Team("Help", world);
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testCreation_LegalCase1() {
//		world = new World();
//		team = new Team("Help", world);
//		assertEquals(team.getName(), "Help");
//		assertEquals(team.getWorld(), world);
//	
//	} 	
	
	@Test
	public void team_test1() {
		allworms = new ArrayList<Worm>();
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		team = new Team("Team", world);
		worm1 = new Worm("Charles", 3, 3, 0.7, 0.9, world);
		worm2 = new Worm("Thomas", 3, 3, 0.7, 0.9, world);
		worm3 = new Worm("Nemo", 3, 3, 0.7, 0.9, world);
		worm4 = new Worm("Toby", 3, 3, 0.7, 0.9, world);
		worm1.join(team);
		allworms.add(worm1);
		assertEquals(team.getWorld(), world);
		assertEquals(team.getName(), "Team");
		assertEquals(team.getAllWorms(), allworms);
		allworms.add(worm2);
		worm2.join(team);
		assertEquals(team.getAllWorms(), allworms);
		assertEquals(team.getWormAt(1), worm2);
		assertEquals(team.getWormAt(0), worm1);
		worm3.join(team);
		assertEquals(team.getNbWorms(), 3);
		assertEquals(team.getIndexOfWorm(worm2), 1);
		assertEquals(team.hasAsWorm(worm1), true);
		assertEquals(team.hasAsWorm(worm2), true);
		assertEquals(team.hasAsWorm(worm3), true);
		assertEquals(team.hasAsWorm(worm4), false);
//		team.removeAsWorm(worm2);
//		assertEquals(team.getNbWorms(), 2);
//		assertEquals(team.getWormAt(1), worm3);
//		assertEquals(team.getWormAt(0), worm1);
	}

	
	//@Test

}
