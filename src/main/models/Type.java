package main.models;

import java.util.function.Consumer;

public class Type<T1,T2>{
    final T1 v1;
    final T2 v2;

    public Type(T1 t1, T2 t2){
        v1 = t1;
        v2 = t2;
    }

    public static <T, T1 extends  T, T2 extends  T> void forEach(Type<T1,T2> tuple, Consumer<? super T> consumer){
        consumer.accept(tuple.v1);
        consumer.accept(tuple.v2);
    }


}
