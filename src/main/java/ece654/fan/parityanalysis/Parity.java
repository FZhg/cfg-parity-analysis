package ece654.fan.parityanalysis;

import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.analysis.AbstractValue;
import org.checkerframework.javacutil.BugInCF;

import java.util.Objects;

public class Parity implements AbstractValue<Parity> {
  
  public static final Parity top = new Parity(ABSTRACT_VALUE.TOP);
  public static final Parity bottom = new Parity(ABSTRACT_VALUE.BOTTOM);
  public static final Parity even = new Parity(ABSTRACT_VALUE.EVEN);
  public static final Parity odd = new Parity(ABSTRACT_VALUE.ODD);


  // TODO: add more precise abstract operations by using integer lattice
  /**
   * Abstract Operations
   */
  public static final BinaryOp<Parity> add = Parity::addSubtract;
  public static final BinaryOp<Parity> subtract = Parity::addSubtract;

  public static Parity addSubtract(Parity first, Parity second) {
    if (second.isBottom() || first.isBottom()) {
      return bottom;
    }
    if ((first.isEven() && second.isOdd()) || (first.isOdd() && second.isEven())) {
      return odd;
    }
    if((first.isOdd() && second.isOdd()) && (first.isEven() && second.isEven())){
      return even;
    }
    return  top;
  }

  public static final BinaryOp<Parity> multiply = (Parity first, Parity second) ->{
    if (second.isBottom() || first.isBottom()) {
      return bottom;
    }
    if (first.isEven()  ||  second.isEven()) {
      return even;
    }
    if(first.isOdd() && second.isOdd()){
      return odd;
    }
    return  top;
  };

  /**
   * The elements in the Parity Lattice
   *          TOP
   *         /   \
   *       EVEN  ODD
   *          \  /
   *          BOTTOM
   */
  public enum ABSTRACT_VALUE {
    BOTTOM,
    EVEN,
    ODD,
    TOP;

    public static ABSTRACT_VALUE getAbstractValue(Integer concreteValue){return concreteValue % 2 == 0? EVEN : ODD; }

  }

  /** What kind of abstract value is this? */
  protected final ABSTRACT_VALUE abstractValue;




  /** Create a constant for {@code type}. */
  public Parity(ABSTRACT_VALUE abstractValue) {
    this.abstractValue = abstractValue;
  }

  /** Create a constant for {@code value}. */
  public Parity(Integer concreteValue) {
    this.abstractValue = ABSTRACT_VALUE.getAbstractValue(concreteValue);
  }

  /**
   * Returns whether the parity is TOP.
   *
   * @return whether the parity is TOP
   */
  public boolean isTop() {
    return abstractValue == ABSTRACT_VALUE.TOP;
  }

  /**
   * Returns whether the parity is BOTTOM.
   *
   * @return whether the parity is BOTTOM
   */
  public boolean isBottom() {
    return abstractValue == ABSTRACT_VALUE.BOTTOM;
  }

  /**
   * Returns whether the parity is EVEN.
   *
   * @return whether the parity is EVEN
   */
  @EnsuresNonNullIf(result = true, expression = "abstractValue")
  public boolean isEven() {
    return abstractValue == ABSTRACT_VALUE.EVEN;
  }


  /**
   * Returns whether the parity is ODD.
   *
   * @return whether the parity is ODD
   */
  @EnsuresNonNullIf(result = true, expression = "abstractValue")
  public boolean isOdd() {
    return abstractValue == ABSTRACT_VALUE.ODD;
  }

  /**
   * Returns the abstractValue.
   *
   * @return the abstractValue
   */

  public ABSTRACT_VALUE getAbstractValue(){
    return abstractValue;
  }

  public Parity copy() {
    return new Parity(abstractValue);
  }

  @Override
  public Parity leastUpperBound(Parity other) {
    if (other.isBottom()) {
      return this.copy();
    }
    if (this.isBottom()) {
      return other.copy();
    }
    if (other.isTop() || this.isTop()) {
      return top;
    }
    if (this.equals(other)) {
      return this.copy(); // when both branches gives the same parity for a variable
    }
    return  top;
  }









  @Override
  public boolean equals(@Nullable Object obj) {
    if (!(obj instanceof Parity)) {
      return false;
    }
    Parity other = (Parity) obj;
    return abstractValue == other.abstractValue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(abstractValue);
  }

  @Override
  public String toString() {
    switch (abstractValue) {
      case TOP:
        return "T";
      case BOTTOM:
        return "-";
      case EVEN:
        return "even";
      case ODD:
        return "odd";
      default:
        throw new BugInCF("Unexpected type: " + abstractValue);
    }
  }
}
