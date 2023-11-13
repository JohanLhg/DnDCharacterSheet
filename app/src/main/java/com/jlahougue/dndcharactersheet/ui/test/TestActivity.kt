package com.jlahougue.dndcharactersheet.ui.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.jlahougue.dndcharactersheet.ui.elements.health.DeathSaves
import com.jlahougue.dndcharactersheet.ui.elements.health.HealthBar
import com.jlahougue.dndcharactersheet.ui.elements.health.HealthDice
import com.jlahougue.dndcharactersheet.ui.elements.stats.Abilities
import com.jlahougue.dndcharactersheet.ui.elements.stats.Skills
import com.jlahougue.dndcharactersheet.ui.theme.DDCharacterSheetTheme

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DDCharacterSheetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Row {
                        Skills()
                        Abilities()
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun GreetingPreview() {
    DDCharacterSheetTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row {
                Skills()
                Column(
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    Abilities()
                }
                Column(
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    HealthBar()
                    HealthDice()
                    DeathSaves()
                }
            }
        }
    }
}