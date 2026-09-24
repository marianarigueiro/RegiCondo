 package com.example.regicondo

 import android.os.Bundle
 import androidx.activity.ComponentActivity
 import androidx.activity.compose.setContent
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.material3.Surface
 import androidx.compose.ui.Modifier
 import com.example.regicondo.ui.navigation.AppNavigation
 import com.example.regicondo.ui.theme.RegiCondoTheme

 class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContent {
             RegiCondoTheme {
                 Surface(modifier = Modifier.fillMaxSize()) {
                     AppNavigation()
                 }
             }
         }
     }
 }