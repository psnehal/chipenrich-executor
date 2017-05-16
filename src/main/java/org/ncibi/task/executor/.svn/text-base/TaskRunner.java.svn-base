package org.ncibi.task.executor;

import java.util.EnumMap;


import org.ncibi.db.ws.TaskType;
import org.ncibi.db.ws.Task;
import org.ncibi.log.Logger;

import org.ncibi.task.TaskExecutor;
import org.ncibi.task.TaskStatus;

public class TaskRunner
{
    private final EnumMap<TaskType, TaskExecutor> taskExecutors;

    public TaskRunner(EnumMap<TaskType, TaskExecutor> taskExecutors)
    {
        this.taskExecutors = taskExecutors;
    }

    public TaskStatus runTask(Task task)
    {
    	
        TaskExecutor executor = taskExecutors.get(task.getTaskType());
        
        Logger.log.logMessage("Inside run task"+ task.getTaskType());
        Logger.log.logStart(task);
        Logger.log.logMessage("Is executor null? : " + ((executor == null)?"yes":"no"));
        try
        {
        	Logger.log.logMessage("inside runTask " + task.getTaskType());
            executor.run(task);
        }
        catch (Exception e)
        {
        
            e.printStackTrace();
            Logger.log.logError(task, e);
            return TaskStatus.ERRORED;
        }
        Logger.log.logFinish(task);
        
        return TaskStatus.DONE;
    }
}