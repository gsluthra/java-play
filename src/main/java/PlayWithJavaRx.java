import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;

public class PlayWithJavaRx {
    public static void main(String args[]){
        function3();
    }

    private static void function1() {
        Flowable.range(1,1000)
                .flatMap(v ->
                 Flowable.just(v)
                         .subscribeOn(Schedulers.computation())
                         .map(w -> w * w)
                ).blockingSubscribe(System.out::println);
    }

    private static void function2() {
        Flowable.range(1,1000)
                .observeOn(Schedulers.computation())
                .map( x -> x * x)
                .blockingSubscribe(System.out::println);
    }

    private static void function3() {
        Flowable<String> flowable = Flowable.just("a","b","c");
        flowable.subscribe(x -> System.out.println("Got "+ x));

        Single<String> single = Single.just("Blah");
        single.subscribe( x -> System.out.println("gotten a single: "+ x));

    }
}
