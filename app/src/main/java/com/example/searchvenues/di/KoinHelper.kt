package com.example.searchvenues.di

import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.Module
import org.koin.dsl.module

private val useCaseModules = listOf(
    module {
//        factory {  }
    }
)

private val viewModules = listOf(
    module {
//        fragment {  }
    }
)

val koinModules: List<Module> = useCaseModules + viewModules