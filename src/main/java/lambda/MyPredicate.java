package lambda;

/**
 * Created by XJX on 2017/4/15.
 */
@FunctionalInterface
public interface MyPredicate<T> {
    boolean filter(T t);
}
