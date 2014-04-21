package worms.model.containment;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




import worms.IllegalMapException;
import worms.IllegalSizeException;
import worms.model.Worm;
import worms.containment.World;


public class WorldTest {

	private World world;
	//private Team team1, team2;
	//private Worm worm1, worm2, worm3;
	
	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}

//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}

	@Before
	public void setUp() throws Exception {
		world = new World();
		
	}

//	@After
//	public void tearDown() throws Exception {
//	}

//	@Test
//	public void creation_LegalCase(){
//		world = new World(3, 3, );
//		
//	}
//	
	@Test 
	public void canExist_legalCase1_deadCenter(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {2.5, 2.5};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		assert(world.canExist(coordinates, 0.5));
		
	}
	
	
	@Test
	public void canExist_illegalcase_impassable_y(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {2, 4};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		System.out.println(world.canExist(coordinates, 0.5));
		assert((!world.canExist(coordinates, 0.5)));
		
	}
	
	@Test
	public void canExist_illegalcase_impassable_x(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {4, 2};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		System.out.println(world.canExist(coordinates, 0.5));
		assert((!world.canExist(coordinates, 0.5)));
		
	}
	
	@Test
	public void canExist_illegalcase_impassable_x_touching(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {2, 3.51};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		assert((!world.canExist(coordinates, 0.5)));
		
	}
	@Test
	public void canExist_legalcase_impassable_x_touching2(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {2, 3.49};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		assert((world.canExist(coordinates, 0.5)));	
	}
	
	@Test
	public void canExist_legalcase_impassable_y_touching(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {3.49, 2};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		assert((world.canExist(coordinates, 0.5)));	
	}

	@Test
	public void canExist_illegalcase_impassable_y_touching(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {3.51, 2};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		assert(!(world.canExist(coordinates, 0.5)));	
	}
	
	@Test
	public void canExist_legalcase_withinrectangle(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {2, 2};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		assert((world.canExist(coordinates, 1.0)));
		
	}
	
	
	@Test
	public void getBoolAt(){
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
		double[] coordinates = {2, 4};
		Random rand = new Random();
		world = new World(5.0, 5.0, passableMap, rand);
		assert(! world.getBoolAt(4,0));
		assert(world.getBoolAt(0, 0));
		assert(! world.getBoolAt(4, 4));
		assert(! world.getBoolAt(0, 4));
		
		
	}
	
	@Test
	public void test_isAdjacent_legalCase() {
		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, false, false},
				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false},{false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(5, 6, passableMap, rand);
		assert(!world.isAdjacent(toArray(3, 3), 0.5));
		assert(!world.isAdjacent(toArray(4, 3), 0.5));
		assert(!world.isAdjacent(toArray(4, 4), 0.5));
//		System.out.println("canExist " + world.canExist(toArray(3.49, 2), 0.5));
//		System.out.println("canExist " + world.canExist(toArray(3.49, 2), 0.5*1.1));
		assert(world.isAdjacent(toArray(3.49, 3), 0.5));
		assert(!world.isAdjacent(toArray(3, 1.49), 0.5));
//		System.out.println("canExist " + world.canExist(toArray(3, 1.51), 0.5));
//		System.out.println("canExist " + world.canExist(toArray(3, 1.51), 0.5*1.1));
		assert(world.isAdjacent(toArray(3, 1.51), 0.5));
		assert(!world.isAdjacent(toArray(2, 4.51), 0.5));
//		System.out.println("mapdata " + world.getBoolAt(2, 4));
//		System.out.println("canExist " + world.canExist(toArray(2, 4.49), 0.5));
//		System.out.println("canExist " + world.canExist(toArray(2, 4.49), 0.5*1.1));
		assert(world.isAdjacent(toArray(2, 4.49), 0.5));
//		

	}
	
	@Test(expected = IllegalMapException.class)
	public void createWorld_IllegalPassableMapCase(){
		boolean[][] passableMap = {	{true, true, true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true, true},
				{false, false, false, false, false, false, false, false, false, false}};
		Random rand = new Random();	
		world = new World(10, 9, passableMap, rand);
	}
	
	@Test(expected = IllegalSizeException.class)
	public void createWorld_IllegalMapSize_NaN_x(){
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
		world = new World(Double.NaN, 9, passableMap, rand);
	}
	
	@Test(expected = IllegalSizeException.class)
	public void createWorld_IllegalMapSize_NaN_y(){
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
		world = new World(9,Double.NaN, passableMap, rand);
	}
	
	@Test(expected = IllegalSizeException.class)
	public void createWorld_IllegalMapSize_MaxVal_x(){
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
		world = new World(Double.MAX_VALUE, 9, passableMap, rand);
	}
	
	@Test(expected = IllegalSizeException.class)
	public void createWorld_IllegalMapSize_MaxVal_y(){
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
		world = new World( 9, Double.MAX_VALUE, passableMap, rand);
	} 
	
	@Test
	public void isLegalSize_NaN() {
		assertEquals(World.isLegalSize(Double.NaN, 10), false);
		assertEquals(World.isLegalSize(10, Double.NaN), false);
		
	}
	
	
	public double[] toArray(double a, double b){
		double[] array = {a, b};
		return array;
	}
	
	
//	
//	@Test 
//	public void canExist_legalCase3(){
//		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
//				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
//		double[] coordinates1 = {3.4, 3.4};
//		Random rand = new Random();
//		world = new World(5.0, 5.0, passableMap, rand);
//		assert(world.canExist(coordinates, 0.5));
//		
//		}
//	
//	
//	@Test 
//	public void canExist_legalCase_Bottom(){
//		boolean[][] passableMap =  {{false, false, false, false, false},{true, true, true, true, false},
//				{true, true, true, true, false},{true, true, true, true, false},{true, true, true, true, false}};
//		double[] coordinates1 = {2, 2};
//		Random rand = new Random();
//		world = new World(5.0, 5.0, passableMap, rand);
//		assert(world.canExist(coordinates, 0.5));
//		
//		}

}
	
	
	
