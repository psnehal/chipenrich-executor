package org.ncibi.lrpath;

import org.junit.Test;
import org.ncibi.lrpath.config.RServerConfiguration;

public class LRPathRServerTest
{
    @Test
    public void testRServer() throws Exception
    {
        LRPathRServer rserver = new LRPathRServer("127.0.0.1",6311
                   );
       // System.out.println(RServerConfiguration.rserverAddress());
        rserver.assignRVariable("check", "to test if rserver is ruuning");
        String r = rserver.evalRCommand("check").asString();
        System.out.println(r);
    }
}
