package lambda;

/**
 * Created by XJX on 2017/4/15.
 */
public interface ObjectPacking<T> {
    default boolean filter(T t, MyPredicate<T> myPredicate) {
        return myPredicate.filter(t);
    }
}
