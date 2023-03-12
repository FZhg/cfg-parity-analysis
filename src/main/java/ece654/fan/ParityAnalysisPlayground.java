package ece654.fan;

import ece654.fan.parityanalysis.Parity;
import ece654.fan.parityanalysis.ParityStore;
import ece654.fan.parityanalysis.ParityTransfer;
import org.checkerframework.dataflow.analysis.ForwardAnalysis;
import org.checkerframework.dataflow.analysis.ForwardAnalysisImpl;
import org.checkerframework.dataflow.cfg.visualize.CFGVisualizeLauncher;


/** Run constant propagation for a specific file and create a PDF of the CFG. */
public class ParityAnalysisPlayground {

    /** Do not instantiate. */
    private ParityAnalysisPlayground() {
        throw new Error("do not instantiate");
    }

    /**
     * Run Parity Analysis for a specific file and create a PDF of the CFG.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {

        /* Configuration: change as appropriate */
        String inputFile = "testcase/Straightline/Straightline.java"; // input file name and path
        String outputDir = "testcase/Straightline/"; // output directory
        String method = "straightline"; // name of the method to analyze
        String clazz = "Straightline"; // name of the class to consider

        // run the analysis and create a PDF file
        ParityTransfer transfer = new ParityTransfer();
        ForwardAnalysis<Parity, ParityStore, ParityTransfer>
                forwardAnalysis = new ForwardAnalysisImpl<>(transfer);
        CFGVisualizeLauncher cfgVisualizeLauncher = new CFGVisualizeLauncher();
        cfgVisualizeLauncher.generateDOTofCFG(
                inputFile, outputDir, method, clazz, true, true, forwardAnalysis);
    }
}
