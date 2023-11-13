package com.jlahougue.dndcharactersheet.ui.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jlahougue.dndcharactersheet.ui.elements.health.DeathSaves
import com.jlahougue.dndcharactersheet.ui.elements.health.Health
import com.jlahougue.dndcharactersheet.ui.elements.stats.Abilities
import com.jlahougue.dndcharactersheet.ui.elements.stats.Skills
import com.jlahougue.dndcharactersheet.ui.elements.stats.Stats
import com.jlahougue.dndcharactersheet.ui.elements.weapons.Weapon
import com.jlahougue.dndcharactersheet.ui.theme.DnDCharacterSheetTheme

class ActionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DnDCharacterSheetTheme {
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
fun ActionActivityPreview() {
    DnDCharacterSheetTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max)
            ) {
                Row {
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Min)
                    ) {
                        Abilities()
                        Stats()
                    }
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Min)
                    ) {
                        Health()
                        DeathSaves()
                    }
                }
                Divider()
                Column(
                    modifier = Modifier
                        .background(color = Color(0xFF78461F))
                        .padding(1.dp)
                ) {
                    Weapon()
                    Weapon()
                    Weapon()
                }
            }
        }
    }
}