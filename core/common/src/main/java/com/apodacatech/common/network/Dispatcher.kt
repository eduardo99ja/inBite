package com.apodacatech.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val niaDispatcher: InBiteDispatchers)

enum class InBiteDispatchers {
    Default,
    IO,
}
