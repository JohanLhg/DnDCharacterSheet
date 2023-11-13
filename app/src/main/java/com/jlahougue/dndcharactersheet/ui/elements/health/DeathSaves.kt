package com.jlahougue.dndcharactersheet.ui.elements.health

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves
import com.jlahougue.dndcharactersheet.ui.elements.FramedBox
import com.jlahougue.dndcharactersheet.ui.theme.DDCharacterSheetTheme

@Composable
fun DeathSaves() {
    FramedBox(
        title = "Death Saves",
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            DeathSavesRow(name = "Successes", color = Color(0xFF3E9400))
            DeathSavesRow(name = "Failures", color = Color(0xFFBC0606))
        }
    }
}

@Composable
fun DeathSavesRow(name: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(5.dp),
            style = TextStyle(fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.width(0.dp).weight(1f))
        Checkbox(
            checked = false,
            colors = CheckboxDefaults.colors(
                uncheckedColor = color,
                checkedColor = color
            ),
            modifier = Modifier.size(40.dp),
            onCheckedChange = {}
        )
        Checkbox(
            checked = false,
            colors = CheckboxDefaults.colors(
                uncheckedColor = color,
                checkedColor = color
            ),
            modifier = Modifier.size(40.dp),
            onCheckedChange = {}
        )
        Checkbox(
            checked = false,
            colors = CheckboxDefaults.colors(
                uncheckedColor = color,
                checkedColor = color
            ),
            modifier = Modifier.size(40.dp),
            onCheckedChange = {}
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun DeathSavesPreview() {
    DDCharacterSheetTheme {
        FramedBox(title = "Death Saves") {
            Column {
                DeathSavesRow(name = "Successes", color = Color(0xFF3E9400))
                DeathSavesRow(name = "Failures", color = Color(0xFFBC0606))
            }
        }
    }
}