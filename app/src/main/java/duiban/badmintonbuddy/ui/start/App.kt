package duiban.badmintonbuddy.ui.start

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {
}

class TestFire @Inject constructor(){
    fun doBajs(): String{
        return "bajs"
    }
}