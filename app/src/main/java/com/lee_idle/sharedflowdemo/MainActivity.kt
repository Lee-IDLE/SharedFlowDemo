package com.lee_idle.sharedflowdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.lee_idle.sharedflowdemo.ui.theme.SharedFlowDemoTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedFlowDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)){
                        ScreenSetup()
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: DemoViewModel = DemoViewModel()) {
    MainScreen(viewModel.sharedFlow)
}

@Composable
fun MainScreen(sharedFlow: SharedFlow<Int>) {
    val messages = remember { mutableStateListOf<Int>() }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        // Lifecycle.State.INITIALIZED
        // Lifecycle.State.CREATED
        // Lifecycle.State.STARTED
        // Lifecycle.State.RESUMED
        // Lifecycle.State.DESTROYED
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            sharedFlow.collect {
                println("Collecting $it")
                messages.add(it)
            }
        }
    }

    LazyColumn {
        items(messages) {
            Text(
                text = "Collected Value = $it",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SharedFlowDemoTheme {
        MainScreen(sharedFlow = DemoViewModel().sharedFlow)
    }
}