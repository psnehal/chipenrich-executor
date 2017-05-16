package org.ncibi.chipenrich;

import java.io.IOException;

import org.ncibi.lrpath.config.RServerConfiguration;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RserveException;

public class RConnection
{
	

	public void testLRPath() throws REXPMismatchException, IOException
	{
		
		
		ChipEnrichRServer rserver = new ChipEnrichRServer(RServerConfiguration.rserverAddress(),
                RServerConfiguration.rserverPort());
		rserver.assignRVariable("check", "NULL");
		String r = rserver.evalRCommand("check").asString();
		rserver.voidEvalRCommand("library(chipenrich)");
		//voidEvalRCommand("library(chipenrich)");
		System.out.println(r);
		
		//ChipEnrichRServer rserver = new ChipEnrichRServer(RServerConfiguration.rserverAddress(), RServerConfiguration.rserverPort());
		ChipEnrich chipenrich = new ChipEnrich(rserver);
		ChipEnrichInputArguments data = new ChipEnrichInputArguments();
		
		data.setUploadFile("/usr/share/chipFileStore/Variable/march18test2/E2F4.txt.bed");
		data.setOutPath("/usr/share/chipFileStore/Variable/march18test2/");
		data.setOutName("march18test2");
		data.setEmail("snehal@med.umich.edu");
		data.setSgList("hg19");
		data.setLd("nearest_tss") ;
		data.setSgSetList(new String[] { "GOCC" });
		data.setIsMappable("F");
		//data.setRc("24");
		data.setMethod("chipenrich");
		data.setQc("T");
		
		
		rserver.assignRVariable("peaks", data.getUploadFile());
		rserver.assignRVariable("outname", data.getOutName());
		rserver.assignRVariable("outpath", data.getOutPath());
		rserver.assignRVariable("genomedata", data.getSgList());
	 	rserver.assignRVariable("genesetdata", data.getSgSetList());
	 	rserver.assignRVariable("ld", data.getLd());
	 	rserver.assignRVariable("methodata", data.getMethod());
	 	
	 	
	 	String command = "";
	 	/*
	 	
	 	command = "chipEnrichResults <- chipenrich( peaks, out_name = outname , out_path = outpath, genome ="
	 			+"genomedata , genesets = genesetdata , locusdef = ld , method = methodata, use_mappability = F," 
	 					+", qc_plots =T)";
	 	*/
	 	
		command = "ChipEnrichResults <- chipenrich( " +
		"peaks" +
		", out_name = outname "+ 
		", out_path = outpath" +
		", genome = genomedata "+
		", genesets = genesetdata " +
		", locusdef = ld "+ 
		", method = methodata"+
		", use_mappability = " + data.getIsMappable()+
		", read_length ="+ data.getRc()+
		", qc_plots =" + data.getQc()+
		")"; 
		
		System.out.println ("command is \n"+ command);
		
		rserver.voidEvalRCommand(command);
		
		RList list = rserver.evalRCommand("ChipEnrichResults").asList();
		System.out.println(list.size());
	
		
	}
		
		
        
        
		
		
	}

