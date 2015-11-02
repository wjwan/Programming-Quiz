package amazonOpt;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OptimizationProcessTest {
	private OptimizationProcess optimizationIns;
	@Before
	public void setUp() throws Exception {
		optimizationIns = new OptimizationProcess();
	}
	
	@Test
	public void testGetOptimizeInsertion() throws Exception {
		optimizationIns.setAllSchedules("Files/schedule.txt");
		Insertion content = new Insertion(1,8,50);
		PotentialInsertion result = optimizationIns.getOptimizeInsertion(content);
		System.out.format("Insertion: %d (%d,%d) %d",result.space.areaID,result.space.begin,result.space.end,result.space.depth);
	}

}
