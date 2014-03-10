package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.util.Util;

public class FacadeTest {
	private static final double EPS = Util.DEFAULT_EPSILON;

	private IFacade facade;
	
	@Before
	public void setup() {
		facade = new Facade();
	}

	@Test
	public void testCreation() {
		Worm worm1 = new Worm("James May", 0, 0, 1, 0);
		Worm worm2 = facade.createWorm(0, 0, 0, 1, "James May");
		assertEquals(worm1.getPosX(), worm2.getPosX(), EPS);
		assertEquals(worm1.getPosY(), worm2.getPosY(),EPS);
		assertEquals(worm1.getOrientation(), worm2.getOrientation(), EPS);
		assertEquals(worm1.getRadius(), worm2.getRadius(), EPS);
		assertEquals(worm1.getMass(), worm2.getMass(), EPS);
		assertEquals(worm1.getName(), worm2.getName());
	}
	
	@Test
	public void testGetters() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		assertEquals(worm1.getPosX(), facade.getX(worm1), EPS);
		assertEquals(worm1.getPosY(), facade.getY(worm1), EPS);
		assertEquals(worm1.getOrientation(), facade.getOrientation(worm1), EPS);
		assertEquals(worm1.getRadius(), facade.getRadius(worm1), EPS);
		assertEquals(worm1.getMass(), facade.getMass(worm1), EPS);
		assertEquals(worm1.getName(), facade.getName(worm1));
		assertEquals(0.25, facade.getMinimalRadius(worm1), EPS);
		assertEquals(worm1.getActionPoints(), facade.getActionPoints(worm1));
		assertEquals(worm1.getMaxActionPoints(), facade.getMaxActionPoints(worm1));
		assertEquals(worm1.getJumpTime(), facade.getJumpTime(worm1), EPS);
	}
	
	@Test
	public void testMoveLegal() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		facade.move(worm1, 1);
		assertEquals(worm1.getPosX(), 1.0, 0.00001);
		int targetAP = Worm.roundUp(worm1.getActionPoints() - Math.abs(Math.cos(0)) - Math.abs(4 * Math.sin(0)));
		assertEquals(targetAP, worm1.getActionPoints());
	}
	
	@Test(expected = ModelException.class)
	public void testMoveIllegalArg() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		facade.move(worm1, -2);
	}
	
	@Test
	public void testTurnLegal() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		Worm worm2 = new Worm("James May", 0, 0, 1, 0);
		facade.turn(worm1, Math.PI);
		worm2.turn(Math.PI, true);
		assertEquals(Math.PI, worm1.getOrientation(), 0.00001);
		assertEquals(worm2.getActionPoints(), worm1.getActionPoints());
	}
	
	@Test
	public void testJump() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		Worm worm2 = new Worm("James May", 0, 0, 1, 0);
		facade.turn(worm1, Math.PI/4.0);
		facade.jump(worm1);
		worm2.turn(Math.PI/4.0, true);
		worm2.jump();
		assertEquals(0,worm1.getActionPoints());
		assertEquals(worm2.getPosX(), worm1.getPosX(), EPS);
		assertEquals(0.0, worm1.getPosY(), EPS);
	}
	
	@Test
	public void testSetRadiusLegal() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		facade.setRadius(worm1, 2.0);
		assertEquals(2.0, worm1.getRadius(), EPS);
		assertEquals(1062 * ((4 / 3) * Math.PI * Math.pow(2.0 , 3)), worm1.getMass(), EPS);
	}
	
	@Test(expected = ModelException.class)
	public void testSetRadiusIllegal() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		facade.setRadius(worm1, -1.0);
	}
	
	@Test
	public void testRenameLegal() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		facade.rename(worm1, "Captain Slow");
		assertEquals(worm1.getName(), "Captain Slow");
	}
	
	@Test(expected = ModelException.class)
	public void testRenameIllegal() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		facade.rename(worm1, "James & Jeremy");
	}
	
	@Test(expected = ModelException.class)
	public void testExhaustedMove() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		facade.turn(worm1, Math.PI/4.0);
		facade.jump(worm1);
		facade.move(worm1, 1);
	}
	
	@Test(expected = ModelException.class)
	public void testExhaustedJump() {
		Worm worm1 = facade.createWorm(0, 0, 0, 1, "James May");
		facade.turn(worm1, Math.PI/4.0);
		facade.jump(worm1);
		facade.jump(worm1);
	}

}
