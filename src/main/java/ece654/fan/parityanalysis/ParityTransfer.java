package ece654.fan.parityanalysis;

import java.util.List;

import org.checkerframework.dataflow.analysis.ForwardTransferFunction;
import org.checkerframework.dataflow.analysis.RegularTransferResult;
import org.checkerframework.dataflow.analysis.TransferInput;
import org.checkerframework.dataflow.analysis.TransferResult;
import org.checkerframework.dataflow.cfg.UnderlyingAST;
import org.checkerframework.dataflow.cfg.node.*;

public class ParityTransfer
        extends AbstractNodeVisitor<
        TransferResult<Parity, ParityStore>,
        TransferInput<Parity, ParityStore>>
        implements ForwardTransferFunction<Parity, ParityStore> {

  @Override
  public ParityStore initialStore(
          UnderlyingAST underlyingAST, List<LocalVariableNode> parameters) {
    ParityStore store = new ParityStore();
    return store;
  }

  @Override
  public TransferResult<Parity, ParityStore> visitLocalVariable(
          LocalVariableNode node, TransferInput<Parity, ParityStore> before) {
    ParityStore store = before.getRegularStore();
    Parity value = store.getInformation(node);
    return new RegularTransferResult<>(value, store);
  }

  @Override
  public TransferResult<Parity, ParityStore> visitNode(
          Node n, TransferInput<Parity, ParityStore> p) {
    return new RegularTransferResult<>(null, p.getRegularStore());
  }

  @Override
  public TransferResult<Parity, ParityStore> visitAssignment(
          AssignmentNode n, TransferInput<Parity, ParityStore> pi) {
    ParityStore p = pi.getRegularStore();
    Node target = n.getTarget();
    Parity info = null;
    if (target instanceof LocalVariableNode) {
      LocalVariableNode t = (LocalVariableNode) target;
      info = p.getInformation(n.getExpression());
      p.setInformation(t, info);
    }
    return new RegularTransferResult<>(info, p);
  }

  @Override
  public TransferResult<Parity, ParityStore> visitIntegerLiteral(
          IntegerLiteralNode n, TransferInput<Parity, ParityStore> pi) {
    ParityStore p = pi.getRegularStore();
    Parity c = new Parity(n.getValue());
    p.setInformation(n, c);
    return new RegularTransferResult<>(c, p);
  }


  // TODO: equalTo refinement

  /**
   * Operations
   */

  public TransferResult<Parity, ParityStore> arithmeticOperation(BinaryOperationNode n, TransferInput<Parity, ParityStore> inFlow, BinaryOp<Parity> abstractOperation){
    ParityStore outFlowStore = inFlow.getRegularStore();
    Parity first = outFlowStore.getInformation(n.getLeftOperand());
    Parity second = outFlowStore.getInformation(n.getRightOperand());
    Parity result = abstractOperation.op(first, second);
    outFlowStore.setInformation(n, result);
    return new RegularTransferResult<>(result, outFlowStore);
  }

  @Override
  public TransferResult<Parity, ParityStore> visitNumericalAddition(NumericalAdditionNode n, TransferInput<Parity, ParityStore> inFlow) {
    return arithmeticOperation(n, inFlow, Parity.add);
  }

  @Override
  public TransferResult<Parity, ParityStore> visitNumericalSubtraction(NumericalSubtractionNode n, TransferInput<Parity, ParityStore> inFlow) {
    return arithmeticOperation(n, inFlow, Parity.subtract);
  }

  @Override
  public TransferResult<Parity, ParityStore> visitNumericalMultiplication(NumericalMultiplicationNode n, TransferInput<Parity, ParityStore> inFlow) {
    return arithmeticOperation(n, inFlow, Parity.multiply);
  }

  /**
   * Plus or Minus doesn't change the parity.
   */
  @Override
  public TransferResult<Parity, ParityStore> visitNumericalMinus(NumericalMinusNode n, TransferInput<Parity, ParityStore> inFlow) {
    return visitNumericalPlusMinus(n, inFlow);
  }


  @Override
  public TransferResult<Parity, ParityStore> visitNumericalPlus(NumericalPlusNode n, TransferInput<Parity, ParityStore> inFlow) {
    return visitNumericalPlusMinus(n, inFlow);
  }


  private TransferResult<Parity, ParityStore> visitNumericalPlusMinus(UnaryOperationNode n, TransferInput<Parity, ParityStore> inFlow){
    ParityStore outFlowStore = inFlow.getRegularStore();
    Parity parity = outFlowStore.getInformation(n.getOperand());
    outFlowStore.setInformation(n, parity);
    return new RegularTransferResult<>(parity, outFlowStore);
  }


}
