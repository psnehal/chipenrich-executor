package org.ncibi.chipenrich;

import org.junit.Test;
import org.ncibi.lrpath.config.RServerConfiguration;

public class ChipEnrichRServerTest
{
    @Test
    public void testRServer() throws Exception
    {
    	ChipEnrichRServer rserver = new ChipEnrichRServer(RServerConfiguration.rserverAddress(),
                    RServerConfiguration.rserverPort());
    	System.out.println(RServerConfiguration.rserverAddress());
    	ChipEnrichInputArguments data = new ChipEnrichInputArguments();
    	data.setSgSetList(new String[] { "biocarta_pathway" });
    	//127.0.0.1
        rserver.assignRVariable("check", "to test if rserver is ruuning");
        rserver.assignRVariable("genesetdata", data.getSgSetList());
        
        String r = rserver.evalRCommand("genesetdata").asString();
        System.out.println(r);
    }
}
