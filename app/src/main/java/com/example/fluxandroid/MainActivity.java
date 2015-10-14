package com.example.fluxandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fluxandroid.action.TodoAction;
import com.example.fluxandroid.dispatcher.Dispatcher;
import com.example.fluxandroid.store.impl.TodoStore;
import com.example.fluxandroid.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

    private String TAG = "TodoStore";

    private TextView mTvHelloWorld;

    private TodoStore mTodoStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dispatcher.register(this);
        mTvHelloWorld = (TextView) findViewById(R.id.tv_hello_world);
        mTvHelloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "onClick");
                Dispatcher.dispatch(new TodoAction());
            }
        });
        test10();
    }

    private void test10() {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("efg");
        list.add("hij");
        list.add("klm");
        List<String> list2 = new ArrayList<>();
        list2.add("123");
        list2.add("456");
        list2.add("789");
        list2.add("101");
        Observable ob1 = Observable.from(list);
        Observable ob2 = Observable.from(list2);
        Observable ob3 = ob1.mergeWith(ob2);
//        Observable.from(list).subscribeOn(Schedulers.io())
        ob3
        .map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return s + "-hello";
            }
        })
        .subscribe(new Action1<String>() {
            private long lastTs = 0;

            @Override
            public void call(String s) {
                long ts = System.currentTimeMillis();
                if (lastTs == 0) {
                    lastTs = ts;
                }
                LogUtil.v("hjx", s + "-time: " + (ts - lastTs));
            }
        });
    }

    public void onEventMainThread(TodoAction action) {
        Log.v(TAG, "MainActivity receive an action");
    }

    private static List<String> sList = new ArrayList<>();
    static {
        sList.add("XXX");
        sList.add("YYY");
        sList.add("ZZZ");
    }

    private Observable<String> creat(final String str, final long sleep, final long start) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(str);
                Log.v(TAG, str + ", " + (System.currentTimeMillis() - start));
            }
        });
    }

    private void test9() {
        long start = System.currentTimeMillis();
        Observable.zip(
                creat("first", 900, start).subscribeOn(Schedulers.io()),
                creat("second", 800, start).subscribeOn(Schedulers.io()),
                creat("third", 700, start).subscribeOn(Schedulers.io()),
                new Func3<String, String, String, String>() {
                    @Override
                    public String call(String s, String s2, String s3) {
                        return s + "-" +
                                s2 + "-" +
                                s3;
                    }
                }
        ).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.v(TAG, s);
            }
        });

    }

    private void test8(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String out = "test8";
                Log.v(TAG, out + ", thread: " + Thread.currentThread().getName());
                subscriber.onNext(out);
            }
        })
//        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//        .observeOn(AndroidSchedulers.mainThread())
        .map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                String ret = s + "-map1";
                String thread = ", thread: " + Thread.currentThread().getName();
                Log.v("rx", ret + thread);
                return ret;
            }
        })
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
        .map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                String ret = s + "-map2";
                String thread = ", thread: " + Thread.currentThread().getName();
                Log.v("rx", ret + thread);
                return ret;
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                String ret = s + "-doOnNext";
                String thread = ", thread: " + Thread.currentThread().getName();
                Log.v("rx", ret + thread);
            }
        })
        .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                String ret = s + "-subscribe";
                String thread = ", thread: " + Thread.currentThread().getName();
                Log.v("rx", ret + thread);
            }
        });
    }

    private void test7() {
        Observable.just(sList)
            .flatMap(new Func1<List<String>, Observable<String>>() {
                @Override
                public Observable<String> call(List<String> strings) {
                    return Observable.from(strings);
                }
            }).flatMap(new Func1<String, Observable<String>>() {
                @Override
                public Observable<String> call(String s) {
                    return Observable.just(s);
                }
            }).filter(new Func1<String, Boolean>() {
                @Override
                public Boolean call(String s) {
                    return s.contains("ZZZ");
                }
            }).doOnNext(new Action1<String>() {
                @Override
                public void call(String s) {

                }
            }).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    Log.v("rx", "doubleflat - " + s);
                }
            });
    }

    private void test6() {
        Observable.just(sList)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                }).map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "-map";
                    }
                }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.v("rx", s + "-subscribe");
                    }
                });
    }

    private void test5() {
        Observable.just(sList)
            .map(new Func1<List<String>, Observable<String>>() {
                @Override
                public Observable<String> call(List<String> strings) {
                    return Observable.from(strings);
                }
            }).subscribe(new Action1<Observable<String>>() {
                @Override
                public void call(Observable<String> stringObservable) {
                    stringObservable.subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            Log.v("rx", "map - " + s);
                        }
                    });
                }
        });
    }

    private void test4() {
        Observable.just(sList)
            .flatMap(new Func1<List<String>, Observable<String>>() {
                @Override
                public Observable<String> call(List<String> strings) {
                    return Observable.from(strings);
                }
            }).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    Log.v("rx", "flatMap - " + s);
                }
        });
    }


    private void test1() {
        Observable.from(sList).subscribe(
            new Action1<String>() {
                @Override
                public void call(String s) {
                    Log.v("rx", s);
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
