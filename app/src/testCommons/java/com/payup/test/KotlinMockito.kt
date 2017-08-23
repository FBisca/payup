package com.payup.test

import org.mockito.Mockito

fun <T> anyNonNull(): T {
    Mockito.any<T>()
    return uninitialized()
}

@Suppress("UNCHECKED_CAST")
private fun <T> uninitialized(): T = null as T
