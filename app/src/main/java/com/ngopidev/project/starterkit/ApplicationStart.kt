package com.ngopidev.project.starterkit

import android.app.Application
import com.ngopidev.project.starterkit.helper.APIServices
import com.ngopidev.project.starterkit.helper.RetrofitExec
import com.ngopidev.project.starterkit.repositories.AppsRepositories
import com.ngopidev.project.starterkit.viewmodels.LoginViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ApplicationStart : Application(), KodeinAware {
    override val kodein = Kodein.lazy{
        import(androidXModule(this@ApplicationStart))
        bind<APIServices>() with singleton { RetrofitExec.execRetrofit() }
        bind() from singleton { AppsRepositories(instance()) }
        bind() from singleton { LoginViewModelFactory(instance()) }
    }
}