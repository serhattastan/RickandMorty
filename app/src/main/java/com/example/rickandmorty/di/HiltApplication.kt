package com.example.rickandmorty.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * HiltApplication is the main application class where Dagger Hilt is initialized.
 * The @HiltAndroidApp annotation triggers Hilt's code generation, including the creation of the app's dependency container.
 * This class acts as the base class for maintaining global application state.
 *
 * By extending Application, this class ensures that Hilt can manage dependencies at the application level.
 */
@HiltAndroidApp
class HiltApplication : Application() {
    // No additional implementation is needed here, as the @HiltAndroidApp annotation
    // is sufficient to set up Dagger Hilt's dependency injection framework.
}
