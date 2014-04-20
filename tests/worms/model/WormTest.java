package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.containment.Team;
import worms.containment.World;
import worms.util.Util;

public class WormTest {

	private Worm worm1;// worm2, worm3;
	private World world;
	//private boolean[][] passableMap;
	private Team team;
	private static final double EPS = Util.DEFAULT_EPSILON;
	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}

//	@Before
//	public void setUp() throws Exception {
//		world = new World()
//		team = new Team("Team", world);
//		worm = new Worm("Testworm", 2, 2, 0.5, 1.5, world);
//	}
	

//	@After
//	public void tearDown() throws Exception {
//	}

	@Test
	public void createWorm_legalCase1() {
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		team = new Team("Team", world);
		worm1 = new Worm("Charles", 3, 3, 0.7, 0.9, world);
		worm1.join(team);
		assertEquals(worm1.getName(), "Charles");
		assertEquals(worm1.getPosX(), 3, EPS);
		assertEquals(worm1.getPosY(), 3, EPS);
		assertEquals(worm1.getRadius(), 0.7, EPS);
		assertEquals(worm1.getMaxActionPoints(), 1526);
		assertEquals(worm1.getMaxHitPoints(), 1526);
		assertEquals(worm1.getTeam(), team);
		assertEquals(worm1.getWorld(), world);
		assertEquals(worm1.getMass(),1525.833853, EPS); 
		assertEquals(worm1.getActionPoints(), 1526);
		assertEquals(worm1.getHitPoints(), 1526);
		worm1.setRadius(1);
		assertEquals(worm1.getMass(),4448.495197, EPS); 
		assertEquals(worm1.getActionPoints(), 1526);
		assertEquals(worm1.getHitPoints(), 1526);
		assertEquals(worm1.getRadius(), 1, EPS);
		assertEquals(worm1.getMaxActionPoints(), 4448);
		assertEquals(worm1.getMaxHitPoints(), 4448);
		worm1.setRadius(0.6);
		assertEquals(worm1.getMass(),960.8749627, EPS); 
		assertEquals(worm1.getActionPoints(), 961);
		assertEquals(worm1.getHitPoints(), 961);
		assertEquals(worm1.getRadius(), 0.6, EPS);
		assertEquals(worm1.getMaxActionPoints(), 961);
		assertEquals(worm1.getMaxHitPoints(), 961);
		
		
	}
//	
//	}
	
	
	@Test
	public void canfall_test1() {
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		worm1 = new Worm("Tester1", 3, 3, 0.5, 0.1, world);
		assert(worm1.canFall());
		worm1.setCoordinates(toArray(2, 4.49));
		assert(!worm1.canFall());
		worm1.setCoordinates(toArray(3.49, 1));
		assert(worm1.canFall());	
	}
	
	@Test
	public void fall_test1() {
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		worm1 = new Worm("Tester1", 3, 3, 0.5, 0.1, world);
		worm1.fall();
		System.out.println("Postfall X: " + worm1.getPosX());
		System.out.println("Postfall Y: " + worm1.getPosY());
		assert((worm1.getCoordinates() == toArray(3, 1.5)));
	}
	
	@Test
	public void jump_test_hitWall() {
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		worm1 = new Worm("Tester1", 2, 1.5, 0.5, Math.PI/4.0 , world);
		//worm1.jump(timestep);
		
	}
	
	public double[] toArray(double a, double b){
		double[] array = {a, b};
		return array;
	}

}
