package org.ncibi.task.executor.chipenrich;

import java.util.List;
import org.ncibi.chipenrich.ChipEnrichInputArguments;
import org.ncibi.chipenrich.*;
import org.ncibi.chipenrich.ChipEnrich;
import org.ncibi.lrpath.config.RServerConfiguration;
import org.ncibi.mqueue.task.TaskArgRetriever;
import org.ncibi.task.TaskWriter;
import org.ncibi.task.executor.AbstractTaskExecutor;

public class ChipEnrichTaskExecutor extends AbstractTaskExecutor<ChipEnrichInputArguments, List<ChipEnrichResults>>
{

    private final String rServeAddress = RServerConfiguration.rserverAddress();
    private final int rServePort = RServerConfiguration.rserverPort();

    public ChipEnrichTaskExecutor(TaskArgRetriever<ChipEnrichInputArguments> argRetriever,
            TaskWriter<List<ChipEnrichResults>, ChipEnrichInputArguments> resultWriter)
    {
        super(argRetriever, resultWriter);
    }

    @Override
    protected List executeWithArgs(ChipEnrichInputArguments args)
    {
        final ChipEnrichRServer rserver = new ChipEnrichRServer(rServeAddress, rServePort);
        final ChipEnrich chipenrichpath = new ChipEnrich(rserver);
        List results = chipenrichpath.runAnalysis(args);
        rserver.close();
        return results;
    }
}
