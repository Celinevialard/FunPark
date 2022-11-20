package com.example.funpark.util;

/**
 * Interface pour les taches asynchrone.
 */
public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
