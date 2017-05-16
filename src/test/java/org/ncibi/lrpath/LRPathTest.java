package org.ncibi.lrpath;

import java.util.List;

import org.junit.Test;
import org.ncibi.lrpath.config.RServerConfiguration;

public class LRPathTest
{
	@Test
	public void testLRPath()
	{
		LRPathRServer rserver = new LRPathRServer(RServerConfiguration.rserverAddress(), RServerConfiguration.rserverPort());
		System.out.println(RServerConfiguration.rserverAddress()+ RServerConfiguration.rserverPort());
		LRPath lrpath = new LRPath(rserver);
		LRPathArguments data = new LRPathArguments();
		data.setGeneids(new int[] { 780, 5982, 3310 });
		data.setDirection(new double[] { 0.414580082, -0.176934427, 0.01006101 });
		data.setSigvals(new double[] { 0.004859222, 0.275428947, 0.940720196 });
		data.setSpecies("hsa");
		data.setMing(10);
		// data.setDatabase("GO");
		// data.setDatabaseExternal(true);
		// data.setDatabase("KEGG Pathway");
		System.out.println(data);
		List<LRPathResult> results = lrpath.runAnalysis(data);
		for (LRPathResult result : results)
		{
			System.out.println(result);
		}
	}
}
