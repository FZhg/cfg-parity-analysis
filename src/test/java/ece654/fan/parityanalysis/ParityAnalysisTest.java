package ece654.fan.parityanalysis;

import org.checkerframework.dataflow.analysis.ForwardAnalysis;
import org.checkerframework.dataflow.analysis.ForwardAnalysisImpl;
import org.checkerframework.dataflow.cfg.visualize.CFGVisualizeLauncher;
import org.checkerframework.dataflow.constantpropagation.Constant;
import org.checkerframework.dataflow.constantpropagation.ConstantPropagationStore;
import org.checkerframework.dataflow.constantpropagation.ConstantPropagationTransfer;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

class ParityAnalysisTest {

    @Test
    void straightlineTest() {
        generatePDFText(
                "testcase/Straightline/Straightline.java",
                "straightline",
                "Straightline",
                "testcase/Straightline/",
                "Out.txt"
        );
    }

    @Test
    void controlStructuresTest(){
        generatePDFText(
                "testcase/ControlStructure/ControlStructure.java",
                "controlStructure",
                "ControlStructure",
                "testcase/ControlStructure/",
                "Out.txt"
                );
    }

    private void generatePDFText(String inputFile, String method, String clazz, String outputDir, String outputFileName){
        String outputFile = outputDir + outputFileName;

        ParityTransfer transfer = new ParityTransfer();
        ForwardAnalysis<Parity, ParityStore, ParityTransfer>
                forwardAnalysis = new ForwardAnalysisImpl<>(transfer);
        CFGVisualizeLauncher cfgVisualizeLauncher = new CFGVisualizeLauncher();

        // generate dot file and pdf file
        cfgVisualizeLauncher.generateDOTofCFG(
                inputFile, outputDir, method, clazz, true, true, forwardAnalysis);


        Map<String, Object> res =
                cfgVisualizeLauncher.generateStringOfCFG(inputFile, method, clazz, true, forwardAnalysis);
        try (FileWriter out = new FileWriter(outputFile)) {
            out.write(res.get("stringGraph").toString());
            out.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}