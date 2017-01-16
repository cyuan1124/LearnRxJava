package com.chefmic.java.rxjava.chapter2;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by cyuan on 1/15/17.
 */
public class ReactiveExtensions {

    public void subscribe() {
        Observable<Tweet> tweets = null;
        // Subscribe with onNext
        tweets.subscribe((Tweet tweet) -> System.out.println(tweet));

        // Subscribe with onNext and onError
        tweets.subscribe((Tweet tweet) -> {
                    System.out.println(tweet);
                },
                (Throwable t) -> {
                    t.printStackTrace();
                });

        // Subscribe with onNext, onError and onCompletion
        tweets.subscribe((Tweet tweet) -> {
                    System.out.println(tweet);
                },
                (Throwable t) -> {
                    t.printStackTrace();
                },
                () -> {
                    this.noMore();
                });

        // Use Java8 method references
        tweets.subscribe(
                System.out::println,
                Throwable::printStackTrace,
                this::noMore);
    }

    public void observer() {
        Observable<Tweet> tweets;
        Observer<Tweet> observer = new Observer<Tweet>() {

            @Override
            public void onNext(Tweet tweet) {
                System.out.println(tweet);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                noMore();
            }
        };
    }

    public void subscription() {
        Observable<Tweet> tweets = null;
        Subscription subscription = tweets.subscribe(System.out::println);
        subscription.unsubscribe();
        Subscriber<Tweet> subscriber = new Subscriber<Tweet>() {

            private final int CAPACITY = 5;
            int count;

            @Override
            public void onNext(Tweet tweet) {
                System.out.print(tweet);
                count++;
                if (count >= CAPACITY) {
                    unsubscribe();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                noMore();
            }
        };
        tweets.subscribe(subscriber);
    }

    public void createObservables() {
        Observable.just(1);

        // T[]
        Observable.from(new Integer[]{1, 2, 3, 4});

        // range(from, n)
        Observable.range(12, 2);

        // Complete immediately after subscription
        Observable.empty();

        Observable.never();

        Observable.error(new RuntimeException());
    }

    static <T> Observable<T> just(T x) {
        return Observable.create(subscriber -> {
            subscriber.onNext(x);
            subscriber.onCompleted();
        });
    }

    static <T> Observable<T> never() {
        return Observable.create(subscriber -> {});
    }

    static <T> Observable<T> empty() {
        return Observable.create(subscriber -> {
            subscriber.onCompleted();
        });
    }

    static Observable<Integer> range(int start, int n) {
        return Observable.create(subscriber -> {
           for (int i = 0; i < n; i++) {
               subscriber.onNext(start + i);
           }
        });
    }

    private void noMore() {
        System.out.println("No more tweets");
    }

}
