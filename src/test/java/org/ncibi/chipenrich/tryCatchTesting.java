package org.ncibi.chipenrich;

import static org.junit.Assert.*;


import org.junit.Test;
import org.ncibi.lrpath.LRPathRServer;
import org.ncibi.lrpath.config.RServerConfiguration;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class tryCatchTesting {

	@Test
	public void test() throws REXPMismatchException, REngineException {
		RConnection connection;
		connection = new RConnection(RServerConfiguration.rserverAddress(),
                RServerConfiguration.rserverPort());
		//connection = new RConnection("127.0.0.1", 6311);
		//LRPathRServer rserver = new LRPathRServer(RServerConfiguration.rserverAddress(),
           //     RServerConfiguration.rserverPort());
		//connection.voidEval(command);
		ChipEnrichRServer rserver = new ChipEnrichRServer(RServerConfiguration.rserverAddress(),
                RServerConfiguration.rserverPort());
		rserver.assignRVariable("check", "To test if rserver is ruuning");
		String r = rserver.evalRCommand("check").asString();
		rserver.voidEvalRCommand("library(chipenrich)");
		//voidEvalRCommand("library(chipenrich)");
		System.out.println(r);
		
		//ChipEnrichRServer rserver = new ChipEnrichRServer(RServerConfiguration.rserverAddress(), RServerConfiguration.rserverPort());
		ChipEnrich chipenrich = new ChipEnrich(rserver);
		
			
	 	
	 	String path = "/usr/share/chipFileStore/jan2test2/E2A.txt";
	 	connection.assign("path", path);
	 	String command = "";
		command = "chipenrich(path,method='chipenrich',locusdef='nearest_tss', genesets=c('biocarta_pathway','panther_pathway'))" ;
		
		connection.voidEval("data(peaks_E2F4)");
		
		
		
		connection.voidEval("library(chipenrich)");
		REXP r1 = connection.parseAndEval("try("+command+",silent=TRUE)");
		if (r1.inherits("try-error")) System.err.println("Error: "+r1.asString());
		else { System.out.println("Hello");
			// success ... }
		}
		
	}
	
	
	

}
