package worms.model;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class FacadeTest {
	public abstract Facade createFacade();
	Facade instance = createFacade();

	@Test
	public void testCreation() {
		Worm worm1 = new Worm("James May", 0, 0, 0, 1);
		Worm worm2 = instance.createWorm(0, 0, 0, 1, "James May");
		assertEquals(worm1.getPosX(), worm2.getPosX(), 0.00001);
		assertEquals(worm1.getPosY(), worm2.getPosY(), 0.00001);
		assertEquals(worm1.getOrientation(), worm2.getOrientation(), 0.00001);
		assertEquals(worm1.getRadius(), worm2.getRadius(), 0.00001);
		assertEquals(worm1.getMass(), worm2.getMass(), 0.00001);
		assertEquals(worm1.getName(), worm2.getName());
	}
	
	@Test
	public void testGetters() {
		Worm worm1 = new Worm("James May", 0, 0, 0, 1);
		assertEquals(worm1.getPosX(), instance.getX(worm1), 0.00001);
		assertEquals(worm1.getPosY(), instance.getY(worm1), 0.00001);
		assertEquals(worm1.getOrientation(), instance.getOrientation(worm1), 0.00001);
		assertEquals(worm1.getRadius(), instance.getRadius(worm1), 0.00001);
		assertEquals(worm1.getMass(), instance.getMass(worm1), 0.00001);
		assertEquals(worm1.getName(), instance.getName(worm1));
		assertEquals(0.25, instance.getMinimalRadius(worm1), 0.00001);
		assertEquals(worm1.getActionPoints(), instance.getActionPoints(worm1));
		assertEquals(worm1.getMaxActionPoints(), instance.getMaxActionPoints(worm1));
	}
	
	@Test
	public void testMoveLegal() {
		Worm worm1 = new Worm("James May", 0, 0, 0, 1);
		instance.move(worm1, 2);
		assertEquals(worm1.getPosX(), 2.0, 0.00001);
		int targetAP = Worm.roundUp(worm1.getActionPoints() - Math.abs(Math.cos(0)) - Math.abs(4 * Math.sin(0)));
		assertEquals(targetAP, worm1.getActionPoints());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoveIllegalArg() {
		Worm worm1 = new Worm("James May", 0, 0, 0, 1);
		instance.move(worm1, -2);
	}
	
	@Test
	public void testTurnLegalCase() {
		Worm worm1 = new Worm("James May", 0, 0, 0, 1);
		instance.turn(worm1, Math.PI);
		assertEquals(Math.PI, worm1.getOrientation(), 0.00001);
		int targetAP = Worm.roundUp(worm1.getActionPoints() - (Math.abs(Math.PI) / (2 * Math.PI)) * 60);
		assertEquals(targetAP, worm1.getActionPoints());
	}
	
	@Test
	public void testJumpData() {
		Worm worm1 = new Worm("James May", 0, 0, 0, 1);
		instance.move(worm1, 1);
		instance.turn(worm1, Math.PI/4.0);
	}

}
