package com.mattsplat.tfa_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mattsplat.tfa_compose.ui.theme.TfacomposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TfacomposeTheme {
                Navigation()
            }
        }
    }
}



