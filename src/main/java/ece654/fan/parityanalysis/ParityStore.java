package ece654.fan.parityanalysis;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.analysis.Store;
import org.checkerframework.dataflow.cfg.node.IntegerLiteralNode;
import org.checkerframework.dataflow.cfg.node.LocalVariableNode;
import org.checkerframework.dataflow.cfg.node.Node;
import org.checkerframework.dataflow.cfg.visualize.CFGVisualizer;
import org.checkerframework.dataflow.expression.JavaExpression;


import java.util.LinkedHashMap;
import java.util.Map;

/** A store that records information about expression and parity mappings. */
public class ParityStore implements Store<ParityStore> {

  /** Information about variables gathered so far. */
  private final Map<Node, Parity> contents;

  /** Creates a new ConstantPropagationStore. */
  public ParityStore() {
    contents = new LinkedHashMap<>();
  }

  protected ParityStore(Map<Node, Parity> contents) {
    this.contents = contents;
  }

  public Parity getInformation(Node n) {
    if (contents.containsKey(n)) {
      return contents.get(n);
    }
    // for local variables and other expressions, assign them a bottom
    return Parity.bottom;
  }

  public void mergeInformation(Node n, Parity otherAbstractValue) {
    Parity abstractValue;
    if (contents.containsKey(n)) {
      abstractValue = otherAbstractValue.leastUpperBound(contents.get(n));
    } else {
      abstractValue = otherAbstractValue;
    }
    contents.put(n, abstractValue);
  }

  public void setInformation(Node n, Parity newAbstractValue) {
    contents.put(n, newAbstractValue);
  }

  @Override
  public ParityStore copy() {
    return new ParityStore(new LinkedHashMap<>(contents));
  }

  @Override
  public ParityStore leastUpperBound(ParityStore other) {
    Map<Node, Parity> newContents =
        new LinkedHashMap<>(contents.size() + other.contents.size());

    // go through all of the information of the other class
    for (Map.Entry<Node, Parity> e : other.contents.entrySet()) {
      Node n = e.getKey();
      Parity otherVal = e.getValue();
      if (contents.containsKey(n)) {
        // merge if both contain information about a variable
        newContents.put(n, otherVal.leastUpperBound(contents.get(n)));
      } else {
        // add new information
        newContents.put(n, otherVal);
      }
    }

    for (Map.Entry<Node, Parity> e : contents.entrySet()) {
      Node n = e.getKey();
      Parity thisVal = e.getValue();
      if (!other.contents.containsKey(n)) {
        // add new information
        newContents.put(n, thisVal);
      }
    }

    return new ParityStore(newContents);
  }

  @Override
  public ParityStore widenedUpperBound(ParityStore previous) {
    return leastUpperBound(previous);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (o == null) {
      return false;
    }
    if (!(o instanceof ParityStore)) {
      return false;
    }
    ParityStore other = (ParityStore) o;
    // go through all the information of the other object
    for (Map.Entry<Node, Parity> e : other.contents.entrySet()) {
      Node n = e.getKey();
      Parity otherVal = e.getValue();
      if (otherVal.isBottom()) {
        continue; // no information
      }
      if (contents.containsKey(n)) {
        if (!otherVal.equals(contents.get(n))) {
          return false;
        }
      } else {
        return false;
      }
    }
    // go through all the information of the object
    for (Map.Entry<Node, Parity> e : contents.entrySet()) {
      Node n = e.getKey();
      Parity thisVal = e.getValue();
      if (thisVal.isBottom()) {
        continue; // no information
      }
      if (other.contents.containsKey(n)) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    int s = 0;
    for (Map.Entry<Node, Parity> e : contents.entrySet()) {
      if (!e.getValue().isBottom()) {
        s += e.hashCode();
      }
    }
    return s;
  }

  @Override
  public String toString() {
    // Only output local variable information.
    // This output is very terse, so a CFG containing it fits well in the manual.
    Map<Node, Parity> contentsLocalVars =
        new LinkedHashMap<>(contents.size());
    for (Map.Entry<Node, Parity> e : contents.entrySet()) {
      if (e.getKey() instanceof LocalVariableNode) {
        contentsLocalVars.put(e.getKey(), e.getValue());
      }
    }
    return contentsLocalVars.toString();
  }

  @Override
  public boolean canAlias(JavaExpression a, JavaExpression b) {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * <p>{@code abstractValue} is {@code null} because {@link ParityStore} doesn't support
   * visualization.
   */
  @Override
  public String visualize(CFGVisualizer<?, ParityStore, ?> viz) {
    return viz.visualizeStoreKeyVal("parity", toString());
  }
}
