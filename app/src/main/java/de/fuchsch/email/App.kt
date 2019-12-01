package de.fuchsch.email

import android.app.Application
import androidx.room.Room
import de.fuchsch.email.database.AppDatabase
import de.fuchsch.email.repository.AccountRepository
import de.fuchsch.email.repository.MailRepository
import de.fuchsch.email.viewmodel.AccountViewModel
import de.fuchsch.email.viewmodel.AccountsViewModel
import de.fuchsch.email.viewmodel.FolderViewModel
import de.fuchsch.email.viewmodel.MessageViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import javax.mail.Session

val appModule = module {

    single {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, "app-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().accountDao() }

    single { AccountRepository(get()) }

    factory { Session.getDefaultInstance(emptyMap<String, String>().toProperties()) }

    single { MailRepository(get()) }

    viewModel { AccountsViewModel(get()) }

    viewModel { AccountViewModel(get()) }

    viewModel { FolderViewModel(get()) }

    viewModel { MessageViewModel(get()) }

}

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }

}