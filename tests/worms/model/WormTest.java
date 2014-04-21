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
import worms.weapons.Rifle;
import worms.weapons.Bazooka; 
import worms.model.*;

import worms.util.Util;


public class WormTest {

	private Worm worm1, worm2;// worm2, worm3;
	private World world;
	//private boolean[][] passableMap;
	private Team team;
	private static final double EPS = Util.DEFAULT_EPSILON;
	private Bazooka bazooka;
	private Rifle rifle;
	private Facade facade;
	
	
	
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
	
	@Test(expected = IllegalArgumentException.class)
	public void createWormTest_illegalRadius() throws IllegalArgumentException{
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		team = new Team("Team", world);
		worm1 = new Worm("Charles", 3, 3, 0.2, 0.9, world);
	}

	@Test
	public void canHaveAsWeapon_test(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		team = new Team("Team", world);
		worm1 = new Worm("Charles", 3, 3, 0.7, 0.9, world);
		rifle = new Rifle(worm1);
		bazooka = new Bazooka(worm1);
		assertEquals(worm1.canHaveAsWeapon(rifle), true);
		assertEquals(worm1.canHaveAsWeapon(bazooka), true);
	}
	
	@Test
	public void hasAsWeapon_test(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		team = new Team("Team", world);
		worm1 = new Worm("Charles", 3, 3, 0.7, 0.9, world);
		rifle = new Rifle(worm1);
		worm1.addAsWeapon(rifle);
		assertEquals(worm1.hasAsWeapon(rifle), true);
		bazooka = new Bazooka(worm1);
		assertEquals(worm1.hasAsWeapon(bazooka), false);
		worm1.addAsWeapon(bazooka);
		assertEquals(worm1.hasAsWeapon(bazooka), true);
	}
	
	@Test
	public void hasPropperWeapons_test(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		team = new Team("Team", world);
		worm1 = new Worm("Charles", 3, 3, 0.7, 0.9, world);
		rifle = new Rifle(worm1);
		worm1.addAsWeapon(rifle);
		assertEquals(worm1.hasProperWeapons(), true);
		bazooka = new Bazooka(worm1);
		worm1.addAsWeapon(bazooka);
		assertEquals(worm1.hasProperWeapons(), true);	
	}
	
	@Test(expected = AssertionError.class)
	public void hasPropperWeapons_test_illegal(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		team = new Team("Team", world);
		worm1 = new Worm("Charles", 3, 3, 0.7, 0.9, world);
		worm2 = new Worm("Darwin", 3, 3, 0.7, 0.9, world);
		rifle = new Rifle(worm1);
		worm1.addAsWeapon(rifle);
		bazooka = new Bazooka(worm2);
		worm1.addAsWeapon(bazooka);
	}	
	
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
	public void renameWorm_testLegalCases(){
		facade = new Facade();
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = facade.createWorld(5, 6, passableMap, rand);
		worm1 = facade.createWorm(world, 3, 3, 0.1, 0.5, "Tester1");
		assertEquals(worm1.getName(), "Tester1");
		facade.rename(worm1, "Tester");
		assertEquals(worm1.getName(), "Tester");
		
	
	}
	
	@Test(expected = ModelException.class)
	public void renameWorm_testIllegalName1(){
		facade = new Facade();
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = facade.createWorld(5, 6, passableMap, rand);
		worm1 = facade.createWorm(world, 3, 3, 0.1, 0.5, "Tester1");
		assertEquals(worm1.getName(), "Tester1");
		facade.rename(worm1, "Excellent#");
	}
	
	@Test(expected = ModelException.class)
	public void renameWorm_testIllegalName2(){
		facade = new Facade();
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = facade.createWorld(5, 6, passableMap, rand);
		worm1 = facade.createWorm(world, 3, 3, 0.1, 0.5, "Tester1");
		assertEquals(worm1.getName(), "Tester1");
		facade.rename(worm1, "abba");
	}
	
	
//	@Test
//	public void fall_test1() {
//		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
//				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
//		Random rand = new Random();	
//		world = new World(5, 6, passableMap, rand);
//		worm1 = new Worm("Tester1", 3, 3, 0.5, 0.1, world);
//		worm1.fall();
//		System.out.println("Postfall X: " + worm1.getPosX());
//		System.out.println("Postfall Y: " + worm1.getPosY());
//		assert((worm1.getCoordinates() == toArray(3, 1.5)));
//	}
//	
	@Test
	public void jump_test_hitWall() {
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		worm1 = new Worm("Tester1", 2, 1.5, 0.5, Math.PI/4.0 , world);
		//worm1.jump(timestep);
		
	}
	
	@Test
	public void jumpTestFlatSurface() {
		boolean[][] passableMap = {	{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{false, false, false, false, false, false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(10, 9, passableMap, rand);
		worm1 = new Worm("Tester1", 1, 1.52, 0.5, Math.PI/4.0 , world);
		assertEquals(worm1.getPosX(), 1, EPS);
		assertEquals(worm1.getPosY(), 1.52, EPS);
		worm1.jump(0.01);
		assertEquals(worm1.getPosX(), 1+5.589396, 0.2); //this doesnt work, getPosX() returns 1.0
		assertEquals(worm1.getPosY(), 1.52, 0.05);//works... somewhat... Y cord is 0.1 in passable terrain after this. Don't know if thats acceptable or not.
		
		
	
	}
	
	@Test
	public void jumpTimeTestFlatSurface() {
		boolean[][] passableMap = {	{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{true, true, true, true, true, true, true, true, true, true},
									{false, false, false, false, false, false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(10, 9, passableMap, rand);
		worm1 = new Worm("Tester1", 1, 1.51, 0.5, Math.PI/4.0 , world);
		assertEquals(worm1.getPosX(), 1, EPS);
		assertEquals(worm1.getPosY(), 1.51, EPS);
		assertEquals(worm1.jumpTime(worm1.getActionPoints(), 0.001), 0.583383508, 0.01); //doesnt work either
	}
	
	
	
	
	
	
	
	public double[] toArray(double a, double b){
		double[] array = {a, b};
		return array;
	}

}
