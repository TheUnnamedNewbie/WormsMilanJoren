package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.util.Util;

public class FacadeTest {
	private static final double EPS = Util.DEFAULT_EPSILON;

	private IFacade facade;
	private double dT = worms.gui.GUIConstants.JUMP_TIME_STEP;
	
	@Before
	public void setup() {
		facade = new Facade();
	}

	@Test
	public void testCreation() {
		Worm worm1 = new Worm("James May", 0, 0, 1, 0, null);
		Worm worm2 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		assertEquals(worm1.getPosX(), worm2.getPosX(), EPS);
		assertEquals(worm1.getPosY(), worm2.getPosY(),EPS);
		assertEquals(worm1.getOrientation(), worm2.getOrientation(), EPS);
		assertEquals(worm1.getRadius(), worm2.getRadius(), EPS);
		assertEquals(worm1.getMass(), worm2.getMass(), EPS);
		assertEquals(worm1.getName(), worm2.getName());
	}
	
	@Test
	public void testGetters() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		assertEquals(worm1.getPosX(), facade.getX(worm1), EPS);
		assertEquals(worm1.getPosY(), facade.getY(worm1), EPS);
		assertEquals(worm1.getOrientation(), facade.getOrientation(worm1), EPS);
		assertEquals(worm1.getRadius(), facade.getRadius(worm1), EPS);
		assertEquals(worm1.getMass(), facade.getMass(worm1), EPS);
		assertEquals(worm1.getName(), facade.getName(worm1));
		assertEquals(0.25, facade.getMinimalRadius(worm1), EPS);
		assertEquals(worm1.getActionPoints(), facade.getActionPoints(worm1));
		assertEquals(worm1.getMaxActionPoints(), facade.getMaxActionPoints(worm1));
		assertEquals(worm1.jumpTime(worm1.getActionPoints(), dT), facade.getJumpTime(worm1, dT), EPS);
	}
	
	@Test
	public void testMove_Legal() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		double targetAP = worm1.getActionPoints()
				- Math.abs(Math.cos(worm1.getOrientation()))
				- Math.abs(4 * Math.sin(worm1.getOrientation()));
		facade.move(worm1);
		assertEquals(worm1.getPosX(), 1.0, 0.00001);
		assertEquals((int)Math.ceil(targetAP), worm1.getActionPoints());
	}
	
//	@Test(expected = ModelException.class)
//	public void testMove_IllegalArg() {
//		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
//		facade.move(worm1, -2);
//	}
	
//	@Test
//	public void testCanMove_Illegal() {
//		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
//		boolean Target = worm1.canMove(-2);
//		assertEquals(false, Target);
//	}
	
	@Test
	public void testTurnLegal() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		Worm worm2 = new Worm("James May", 0, 0, 1, 0, null);
		facade.turn(worm1, Math.PI);
		worm2.turn(Math.PI, true);
		assertEquals(Math.PI, worm1.getOrientation(), 0.00001);
		assertEquals(worm2.getActionPoints(), worm1.getActionPoints());
	}
	
	@Test
	public void testJump() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		Worm worm2 = new Worm("James May", 0, 0, 1, 0, null);
		facade.turn(worm1, Math.PI/4.0);
		facade.jump(worm1, dT);
		worm2.turn(Math.PI/4.0, true);
		worm2.jump(dT);
		assertEquals(0,worm1.getActionPoints());
		assertEquals(worm2.getPosX(), worm1.getPosX(), EPS);
		assertEquals(0.0, worm1.getPosY(), EPS);
	}
	
	@Test
	public void testSetRadiusLegal() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		facade.setRadius(worm1, 2.0);
		assertEquals(2.0, worm1.getRadius(), EPS);
		assertEquals(1062 * ((4.0 / 3.0) * Math.PI * Math.pow(2.0 , 3)), worm1.getMass(), EPS);
	}
	
	@Test(expected = ModelException.class)
	public void testSetRadiusIllegal() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		facade.setRadius(worm1, -1.0);
	}
	
	@Test
	public void testRenameLegal() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		facade.rename(worm1, "Captain Slow");
		assertEquals(worm1.getName(), "Captain Slow");
	}
	
	@Test(expected = ModelException.class)
	public void testRenameIllegal() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		facade.rename(worm1, "James & Jeremy");
	}
	
	@Test(expected = ModelException.class)
	public void testExhaustedMove() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		facade.turn(worm1, Math.PI/4.0);
		facade.jump(worm1, dT);
		facade.move(worm1);
	}
	
	@Test(expected = ModelException.class)
	public void testExhaustedJump() {
		Worm worm1 = facade.createWorm(null, 0, 0, 0, 1, "James May", null);
		facade.turn(worm1, Math.PI/4.0);
		facade.jump(worm1, dT);
		facade.jump(worm1, dT);
	}

}
