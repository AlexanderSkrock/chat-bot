package dev.skrock.chatbot.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.*;

/**
 * https://stackoverflow.com/a/23302308
 */
@UtilityClass
public class FutureUtils {
    public<U> CompletableFuture<U> makeCompletable(Future<U> future) {
        if (future.isDone()) {
            return transformDoneFuture(future);
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                if (!future.isDone()) {
                    awaitFutureIsDoneInForkJoinPool(future);
                }
                return future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                // Normally, this should never happen inside ForkJoinPool
                Thread.currentThread().interrupt();
                // Add the following statement if the future doesn't have side effects
                // future.cancel(true);
                throw new RuntimeException(e);
            }
        });
    }

    private static <T> CompletableFuture<T> transformDoneFuture(Future<T> future) {
        CompletableFuture<T> cf = new CompletableFuture<>();
        T result;
        try {
            result = future.get();
        } catch (Throwable ex) {
            cf.completeExceptionally(ex);
            return cf;
        }
        cf.complete(result);
        return cf;
    }

    private void awaitFutureIsDoneInForkJoinPool(Future<?> future) throws InterruptedException {
        ForkJoinPool.managedBlock(new ForkJoinPool.ManagedBlocker() {

            @Override public boolean block() throws InterruptedException {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }

            @Override public boolean isReleasable() {
                return future.isDone();
            }
        });
    }
}