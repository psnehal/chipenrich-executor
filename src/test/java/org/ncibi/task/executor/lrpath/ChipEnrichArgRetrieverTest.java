package org.ncibi.task.executor.lrpath;

import junit.framework.Assert;

import org.hibernate.Session;
import org.junit.Test;
import org.ncibi.db.EntityManagers;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.PersistenceUnit;
import org.ncibi.db.ws.Task;
import org.ncibi.db.ws.TaskType;
import org.ncibi.hibernate.SessionProcedure;
import org.ncibi.hibernate.Sessions;

import org.ncibi.chipenrich.ChipEnrichInputArguments;
import org.ncibi.mqueue.MessageQueue;
import org.ncibi.mqueue.persistent.AnyMessagePersistentMessageQueue;
import org.ncibi.mqueue.task.PersistentTaskQueuer2;
import org.ncibi.mqueue.task.TaskUtil;
import org.ncibi.task.executor.chipenrich.ChipEnrichArgRetriever;

public class ChipEnrichArgRetrieverTest
{

    private static final PersistenceSession persistence = new PersistenceUnit(
            EntityManagers.newEntityManagerFromProject("task"));
    private static final MessageQueue queue = new AnyMessagePersistentMessageQueue(persistence, "test");
    private static PersistentTaskQueuer2<ChipEnrichInputArguments> taskQueuer = new PersistentTaskQueuer2<ChipEnrichInputArguments>(
            queue, persistence);
    private static final ChipEnrichInputArguments chipenrichArgs = new ChipEnrichInputArguments();

    @Test
    public void testRetrieveAndDeleteArgsFromDatabase()
    {
        Task task = TaskUtil.newQueuedTask(TaskType.CHIPENRICH);
        queueLChipEnrichArgsToRetrieve(task);
        ChipEnrichArgRetriever argRetriever = new ChipEnrichArgRetriever(persistence);
        ChipEnrichInputArguments args = argRetriever.retrieveArgsForTask(task);
        Assert.assertEquals(args.getOutName(), "chip");
        argRetriever.deleteArgsForTask(task);
        deleteTask(task);
    }

    private void queueLChipEnrichArgsToRetrieve(Task t)
    {
    	chipenrichArgs.setOutName("chip");
        taskQueuer.queue(t, chipenrichArgs);
    }

    private void deleteTask(final Task task)
    {
        try
        {
            Sessions.withSession(persistence.session(), new SessionProcedure()
            {
                @Override
                public void apply(Session session)
                {
                    session.refresh(task);
                    session.delete(task);
                }
            });
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
    }
}
