package com.example.funpark.util;

/**
 * Interface pour les tâches asynchrones.
 */
public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
