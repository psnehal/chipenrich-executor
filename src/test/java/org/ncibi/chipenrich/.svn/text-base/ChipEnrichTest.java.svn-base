package org.ncibi.chipenrich;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.ncibi.chipenrich.ChipEnrichRServer;
import org.ncibi.lrpath.LRPathResult;
import org.ncibi.lrpath.config.RServerConfiguration;
import org.rosuda.REngine.REXPMismatchException;
import org.ncibi.chipenrich.ChipEnrichInputArguments;

public class ChipEnrichTest
{
	@Test
	public void testLRPath() throws REXPMismatchException, IOException
	{
		
		
	
		ChipEnrichRServer rserver = new ChipEnrichRServer(RServerConfiguration.rserverAddress(),
                RServerConfiguration.rserverPort());
		ChipEnrich chipenrich = new ChipEnrich(rserver);
		ChipEnrichInputArguments data = new ChipEnrichInputArguments();
		
		data.setUploadFile("/usr/share/chipFileStore/Variable/march18test2/E2F4.txt.bed");
		data.setOutPath("/usr/share/chipFileStore/Variable/march18test2/");
		data.setOutName("march18test2");
		data.setEmail("snehal@med.umich.edu");
		data.setSgList("hg19");
		data.setLd("nearest_tss") ;
		data.setSgSetList(new String[] { "GOCC" });
		data.setIsMappable("T");
		data.setRc("user");
		//data.setRc("user");
		//data.
		data.setUploadMappaFile("/usr/share/trest.txt");
		data.setMethod("chipenrich");
		data.setQc("T");
		data.setFilter("2000");
		data.setPeakThr("1");
		
		 chipenrich.runAnalysis(data);
		
		
        
        
		
		
	}
}
