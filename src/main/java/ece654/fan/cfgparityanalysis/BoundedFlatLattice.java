package ece654.fan.cfgparityanalysis;

/***
 * A finite elements flat lattice.
 * @param <T> the enum type
 */
public class BoundedFlatLattice<T> {
    private final T top;
    private final T bottom;

    public BoundedFlatLattice(T top, T bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public boolean  isTop(T element){
        return element == top;
    }

    public boolean isBottom(T element){
        return element == bottom;
    }

    public T getLeastUpperBound(T left, T right){
        if (isTop(left) || isTop(right)){
            return top;
        } else if (isBottom(left)){
            return right;
        } else if (isBottom(right)){
            return left;
        } else {
            return top;
        }
    }
}