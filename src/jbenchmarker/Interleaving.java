package jbenchmarker;

import java.util.ArrayList;
import java.util.List;
import jbenchmarker.core.MergeAlgorithm;
import jbenchmarker.core.ReplicaFactory;
import jbenchmarker.core.VectorClock;
import jbenchmarker.logoot.LogootFactory;
import jbenchmarker.ot.SOCT2Factory;
import jbenchmarker.rga.RGAFactory;
import jbenchmarker.sim.CausalDispatcher;
import jbenchmarker.trace.IncorrectTrace;
import jbenchmarker.trace.TraceOperation;
import jbenchmarker.woot.original.WootFactory;
import jbenchmarker.woot.wooth.WootHFactory;
import jbenchmarker.woot.wooto.WootOFactory;

public class Interleaving {
    static final List<TraceOperation> ops = new ArrayList<TraceOperation>();

    static VectorClock makeIns(VectorClock prev, int replica, int pos, String content) {
        VectorClock vc = (prev == null) ? new VectorClock() : new VectorClock(prev);
        vc.inc(replica);
        ops.add(TraceOperation.insert(replica, pos, content, vc));
        return vc;
    }

    static VectorClock makeIns(VectorClock prev, int replica, int pos, String... chars) {
        for (int i = 0; i < chars.length; i++) {
            prev = makeIns(prev, replica, pos + i, chars[i]);
        }
        return prev;
    }

    static VectorClock makeIns(VectorClock prev, int replica, int pos, String content, int repeat) {
        for (int i = 0; i < repeat; i++) {
            prev = makeIns(prev, replica, pos + i, content);
        }
        return prev;
    }

    static {
        VectorClock before = makeIns(null, 0, 0, "<", ">");
        makeIns(before, 0, 1, "-", 100);
        makeIns(before, 1, 1, "#", 100);
    }

    static boolean runTest(ReplicaFactory rf, int attempt) throws IncorrectTrace {
        CausalDispatcher cd = new CausalDispatcher(rf);
        cd.run(ops.iterator());
        MergeAlgorithm r0 = cd.getReplicas().get(0);
        String result = r0.getDoc().view();
        if (!result.matches("^<-*#*>$") && !result.matches("^<#*-*>$")) {
            System.out.println("Testing " + rf.getClass().getName());
            System.out.println("Attempt " + attempt + " had interleaving: " + result);
            System.out.println();
            return false;
        } else if (attempt == 100) {
            System.out.println("Testing " + rf.getClass().getName());
            System.out.println("Could not find interleaving in " + attempt + " attempts.");
            System.out.println();
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        int attempts = 0; while (runTest(new LogootFactory(), ++attempts));
        attempts = 0; while (runTest(new RGAFactory(), ++attempts));
        attempts = 0; while (runTest(new SOCT2Factory(), ++attempts));
        attempts = 0; while (runTest(new WootFactory(), ++attempts));
        attempts = 0; while (runTest(new WootHFactory(), ++attempts));
        attempts = 0; while (runTest(new WootOFactory(), ++attempts));
    }
}
