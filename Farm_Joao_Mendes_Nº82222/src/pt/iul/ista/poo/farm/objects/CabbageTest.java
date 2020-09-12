package pt.iul.ista.poo.farm.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pt.iul.ista.poo.utils.Point2D;

public class CabbageTest {
	
	Cabbage c1, c2;

	@Before
	public void setUp() throws Exception {
		c1=new Cabbage(new Point2D(0,0));
	}

	@Test
	public void testMaduro() {
		for(int i=0;i!=10;i++){
			c1.update();
		}
		assertEquals(c1.getEstado()," maduro");
	}
	@Test
	public void testPodre(){
		for(int i=0;i!=30;i++){
			c1.update();
		}
		assertEquals(c1.getEstado()," podre");
	}
	
	@Test
	public void testInteraction(){
		c1.interact();
		assertEquals(c1.getCiclos(),1);
	}

}
