package com.example.dfmtest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dfmtest.ui.theme.DFMTestTheme
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus.INSTALLED

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DFMTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val request = SplitInstallRequest
            .newBuilder()
            .addModule("dynamicfeature")
            .build()
        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener { sessionId ->
                splitInstallManager.registerListener { state ->
                    if (state.sessionId() == sessionId && state.status() == INSTALLED) {
                        val view = Class.forName("com.example.dynamicfeature.CustomView")
                            .getConstructor(Context::class.java)
                            .newInstance(this) as FrameLayout
                        val child = view.getChildAt(0)
                        Log.d("MainActivity", "View: $view. Child: $child")
                        setContentView(
                            view
                        )
                    }
                }
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DFMTestTheme {
        Greeting("Android")
    }
}
