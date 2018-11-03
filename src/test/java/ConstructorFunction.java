/**
 * Author: zhshuo
 * Time : 2018/10/19 13:27 星期五
 **/
@FunctionalInterface
public interface ConstructorFunction<T,F,G,H> {

    H get(T t,F f,G g);

    default T deMe(T t){
        return t;
    }

    static void deMeSta(){

    }

}
