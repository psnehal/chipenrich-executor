package org.ncibi.chipenrich;

import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;
import org.ncibi.db.EntityManagers;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.PersistenceUnit;
import org.ncibi.db.ws.ChipEnrichUrlLinkName;
import org.ncibi.db.ws.TaskType;
import org.ncibi.db.ws.Task;
import org.ncibi.hibernate.SessionProcedure;
import org.ncibi.hibernate.Sessions;
import org.ncibi.task.TaskStatus;
import org.ncibi.task.logger.PersistentTaskLogger;
import org.ncibi.task.logger.TaskLogger;

public class ChipEnrichLinkUrlTest
{
    @Test
    
    public void testLogging()
    {
        PersistenceSession persistence = new PersistenceUnit(EntityManagers
                    .newEntityManagerFromProject("task"));
        
        String uuid ="12345tyu";
        String name = "test3";
        
        final ChipEnrichUrlLinkName linkname = new ChipEnrichUrlLinkName();
        linkname.setUuid(uuid);
        linkname.setName(name);
        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                session.saveOrUpdate(linkname);
            }
        });
        
        
      
            final String hql = "from ws.ChipEnrichUrlLinkName where uuid = '" + uuid+ "'";
            ChipEnrichUrlLinkName  link = null;

            try
            {
                link = persistence.hqlQuery(hql).single();
            }
            catch (Exception e)
            {
                link = null;
            }
            System.out.println(link.getName()+link.getName());

            
       
        
       
        
        
    }

	

	
}
