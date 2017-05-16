package org.ncibi.chipenrich;

import static org.junit.Assert.*;


import org.junit.Test;
import org.ncibi.lrpath.LRPathRServer;
import org.ncibi.lrpath.config.RServerConfiguration;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RserverConnectioTest {

	@Test
	public void test() throws REXPMismatchException, REngineException {
		RConnection connection;
		connection = new RConnection("localhost",
                RServerConfiguration.rserverPort());
		//connection = new RConnection("127.0.0.1", 6311);
		//LRPathRServer rserver = new LRPathRServer(RServerConfiguration.rserverAddress(),
           //     RServerConfiguration.rserverPort());
		//connection.voidEval(command);
		int [] values = {31, 87, 93, 204, 261, 1908 };
		connection.assign("a", values);
		String filepath = "";
		connection.assign("filepath", "/home/snehal/ConceptMetabGG/conceptmetabVersion/v4/conceptmetab/3_qval.csv");
		String command  = "no_col <- max(count.fields(filepath,sep = \" "+","+ "\"))";
		System.out.println(command);
		connection.voidEval(command);
		int colNo = connection.eval("no_col").asInteger();
		System.out.println(colNo);
		String c2 = "data = read.table(filepath,sep=\"" +","+"\",fill=TRUE,col.names=1:no_col)";
		connection.voidEval(c2);
		connection.voidEval("enriched.concepts <- as.list(as.data.frame(t(data)))");
		connection.voidEval("membership = t(sapply(enriched.concepts, function(enriched.concepts){ as.numeric(a %in% enriched.concepts) }))");
		connection.voidEval("colnames(membership) = a");
		connection.voidEval("rownames(membership) = data[,1]");
		connection.voidEval("m_matrix <- data.matrix(membership)");
		connection.voidEval("pdf(file='/home/snehal/ConceptMetabGG/conceptmetabVersion/v4/conceptmetab/hist.pdf')");
		connection.voidEval("nba_heatmap <- heatmap(m_matrix, Rowv=NA, Colv=NA, col = cm.colors(256), scale='column')");
		connection.voidEval("dev.off()");
		
	
		
		
	
	
	}

}
