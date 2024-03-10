package com.example.articlesapp;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class RxImmediateSchedulerRule implements TestRule {
    private Scheduler immediate = new Scheduler() {
        @Override
        public @NonNull Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
            return super.scheduleDirect(run, 0, unit);
        }

        @Override
        public Scheduler.Worker createWorker() {
           return new ExecutorScheduler.ExecutorWorker(new Executor() {
                @Override
                public void execute(Runnable command) {
                    command.run();
                }
            }, true, true);
           // new ExecutorScheduler.ExecutorWorker(new );
            //return Schedulers.from(command -> command.run());
        }
    };

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
                RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
                RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
                RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}

