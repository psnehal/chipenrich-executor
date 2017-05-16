package org.ncibi.task.executor.chipenrich;

import org.ncibi.chipenrich.ChipEnrichInputArguments;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.ws.Task;
import org.ncibi.log.Logger;

import org.ncibi.task.executor.AbstractTaskArgRetriever2;
import org.ncibi.ws.BeanXMLDecoder;

public class ChipEnrichArgRetriever extends AbstractTaskArgRetriever2<ChipEnrichInputArguments>
{
    public ChipEnrichArgRetriever(PersistenceSession persistence)
    {
        super(persistence);
    }
    
    protected String hqlQueryToRetrieveArgsForTask(Task task)
    {
    	 Logger.log.logMessage("inside hqlQueryToRetrieveArgsForTask in ChipEnrichErgsRetriever" +"from ws.ServiceArguments where uuid = '" + task.getUuid() + "'");
        return "from ws.ServiceArguments where uuid = '" + task.getUuid() + "'";
    }

    @Override
    protected ChipEnrichInputArguments createObjectFromXml(String xml)
    {
        BeanXMLDecoder decoder = new BeanXMLDecoder(xml);
        return decoder.fromXml();
    }
}

