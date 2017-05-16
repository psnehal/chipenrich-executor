package org.ncibi.main;

import org.ncibi.commons.ipc.ThreadUtil;
import org.ncibi.db.EntityManagers;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.PersistenceUnit;
import org.ncibi.db.ws.Task;
import org.ncibi.log.Logger;
import org.ncibi.mqueue.MessageQueue;
import org.ncibi.mqueue.MessageQueueConfiguration;
import org.ncibi.mqueue.QueueOperationNotAllowedException;
import org.ncibi.mqueue.poll.NSecondsPoller;
import org.ncibi.mqueue.poll.Poller;
import org.ncibi.mqueue.task.PersistentTaskDequeuer;
import org.ncibi.task.TaskStatus;
import org.ncibi.task.executor.TaskRunner;
import org.ncibi.task.factory.TaskRunnerFactory;

final class Executor
{
    private final TaskRunner taskRunner;
    private final PersistentTaskDequeuer taskDequeuer;
    
    public Executor(PersistenceSession persistence, MessageQueue queue)
    {
        taskRunner = TaskRunnerFactory.newTaskRunner(persistence);
        String check;
        if(taskRunner == null){
        	check = "yes;";}
        	else{
        		check = "no";}
        Logger.log.logMessage("Is taskrunner null?: " + check);
        taskDequeuer = createTaskDequeuer(persistence, queue);
    }
    
    private PersistentTaskDequeuer createTaskDequeuer(PersistenceSession persistence, MessageQueue queue)
    {
        final Poller poller = new NSecondsPoller(queue, MessageQueueConfiguration.pollingWait());
        final PersistentTaskDequeuer taskDequeuer = new PersistentTaskDequeuer(poller, persistence);
        return taskDequeuer;
    }
    
    public void retrieveAndExecuteTasks()
    {
        Logger.log.logMessage("Processing task requests, in 'retrieveAndExecuteTasks'...");
        while (true)
        {
        	try
        	{
        		Logger.log.logMessage("Call for retrieve and run next task");
        		retrieveAndRunNextTask();
        		
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        }
    }
     
    private void retrieveAndRunNextTask()
    {
    	Logger.log.logMessage("Inside 'retrieveAndRunNextTask'");
        Task task = retrieveNextTask();
        Logger.log.logMessage("After 'retrieveNextTask'; status is "+ task.getStatus());
        
        Logger.log.logMessage("Going towards taskStatus");
        TaskStatus taskStatus = runTaskHandlingExceptions(task);
        Logger.log.logMessage("After 'runTaskHandlerExceptions', TaskStatus is: "+ taskStatus);
        taskDequeuer.markTaskAs(task, taskStatus);
        Logger.log.logMessage("Return from retrieveAndRunNextTask: with task-status="+ task.getStatus() + " for task-id=" + task.getId());
    }
      
    private Task retrieveNextTask()
    {
        Task task = null;
        while (task == null)
        {
        	Logger.log.logMessage("Inside RetrieveNextTask");
            task = retrieveNextTaskHandlingQueueExceptions();
            Logger.log.logMessage("Task type is"+task.getTaskType());
        }
        
        return task;
    }

    private Task retrieveNextTaskHandlingQueueExceptions()
    {
        try
        {
        	 Logger.log.logMessage("inside retrieveNextTaskHandlingQueueExceptions");
            return taskDequeuer.dequeue();
        }
        catch (QueueOperationNotAllowedException e)
        {
            Logger.log.logMessage("Queue not currently processing requests.");
            ThreadUtil.waitSeconds(60);
            return null;
        }
    }
    
    private TaskStatus runTaskHandlingExceptions(Task task)
    {
    	TaskStatus status = TaskStatus.ERRORED;
    	
    	try
    	{
    		status = taskRunner.runTask(task);
    		if( status.equals(TaskStatus.DONE)){
    		 PersistenceSession persistence = new PersistenceUnit(EntityManagers
    	                .newEntityManagerFromProject("task"));
    	        ChipEnrichDbConnection db = new ChipEnrichDbConnection();
    	        String uuid = task.getUuid();
    	        String email = db.getEmail(uuid);
    			JavaMailer mail = new JavaMailer();
    			String sender = "chipenrich@umich.edu";
    			// String recipient = "snehalbpatil87@gmail.com";
    			String subject = "ChIP-Enrich Analysis Results";
    			String emailMessage = "Your ChIP-Enrich analysis is done. Please check your results here:  + http://chip-enrich.med.umich.edu/chipEnrichResult.jsp?name="+db.getOutname(uuid);
    			mail.sendMail(sender, email, subject, emailMessage);
    			Logger.log.logMessage("From task done id loop and name of the utr");}
    	}
    	catch (Exception e)
    	{
    		Logger.log.logMessage("Task " + task.getUuid() + " failed with exception: " + e);
    	}
    	
    	return status;
    }
}
