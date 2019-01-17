package com.azutka.shopify_challenge.interfaces;

import java.util.List;

public interface OnTaskCompletedListener<E> {

    void onTaskCompleted(List<E> models);

}