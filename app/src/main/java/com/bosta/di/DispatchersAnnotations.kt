package com.bosta.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: DispatcherAnnotations)

enum class DispatcherAnnotations {
    Default,
    IO,
    Main,
    Unconfined
}
