package com.example.unitconvertercompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconvertercompose.ui.theme.UnitConverterComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}


@Composable
fun UnitConverter() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Unit Converter")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {})
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            MetricsDropdownButton("From")
            Spacer(modifier = Modifier.width(16.dp))
            MetricsDropdownButton("To")


        }
    }
}

@Composable
fun MetricsDropdownButton(text: String) {
    Box {
        val context = LocalContext.current
        Button(onClick = {
            Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show()
        }) {
            Text(text)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Drop down icon"
            )
        }
        MetricsDropdownMenu()

    }
}

@Composable
fun MetricsDropdownMenu(textList: List<String> = listOf("Centimeters",
    "Meters", "Kilometers", "Feet", "Milimeters")) {
    DropdownMenu(expanded = true, onDismissRequest = { /*TODO*/ }) {
        textList.map {
            DropdownMenuItem(text = { Text(it) }, onClick = { /*TODO*/ })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverterComposeTheme {
        UnitConverter()
    }
}