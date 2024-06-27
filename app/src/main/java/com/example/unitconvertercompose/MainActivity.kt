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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconvertercompose.ui.theme.UnitConverterComposeTheme

class MainActivity : ComponentActivity() {
    companion object
    {
        val converterMatrix = mapOf(
            "Centimeters" to mapOf(
                "Centimeters" to 1.0,
                "Meters" to 0.01,
                "Kilometers" to 0.00001
            ),
            "Meters" to mapOf(
                "Centimeters" to 100.0,
                "Meters" to 1.0,
                "Kilometers" to 0.001
            ),
            "Kilometers" to mapOf(
                "Centimeters" to 100000.0,
                "Meters" to 1000.0,
                "Kilometers" to 1.0
            )
        )
    }
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
    var valueToConvert by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val from = remember { mutableStateOf("") }
    val to = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Unit Converter", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = valueToConvert,
            onValueChange = { valueToConvert = it },
            placeholder = {
                Text(text = "Enter value to convert")
            })
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            MetricsDropdownButton("From", from)
            Spacer(modifier = Modifier.width(16.dp))
            MetricsDropdownButton("To", to)


        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            valueToConvert.toDoubleOrNull()?.let {
                val conversionFactor = MainActivity.converterMatrix[from.value]?.get(to.value)
                if (conversionFactor != null) {
                    result = (it * conversionFactor).toString()
                } else {
                    Toast.makeText(context, "Conversion not supported", Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "Convert")
        }
        Text(text = "Converted Value: $result ${to.value}")
    }
}

@Composable
fun MetricsDropdownButton(text: String, selectedValue: MutableState<String>) {
    val isDropdownExpanded = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            isDropdownExpanded.value = !isDropdownExpanded.value
        }) {
            Text(text)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Drop down icon"
            )
        }
        Text(text = selectedValue.value)
        MetricsDropdownMenu(isDropdownExpanded, selectedValue)
    }
}

@Composable
fun MetricsDropdownMenu(
    isDropdownExpanded: MutableState<Boolean>,
    selectedValue: MutableState<String>,
    textList: List<String> = listOf(
        "Centimeters", "Meters", "Kilometers",
    )
) {
    DropdownMenu(expanded = isDropdownExpanded.value, onDismissRequest = {
        isDropdownExpanded.value = false
    }) {
        textList.map {
            DropdownMenuItem(text = { Text(it) }, onClick = {
                selectedValue.value = it
                isDropdownExpanded.value = false
            })
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