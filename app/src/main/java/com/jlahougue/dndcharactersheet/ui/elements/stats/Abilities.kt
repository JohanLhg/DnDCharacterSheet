package com.jlahougue.dndcharactersheet.ui.elements.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jlahougue.dndcharactersheet.ui.elements.FramedBox
import com.jlahougue.dndcharactersheet.ui.theme.DnDCharacterSheetTheme

@Composable
fun Abilities() {
    FramedBox(title = "Abilities") {
        Column(
            modifier = Modifier.padding(
                start = 5.dp,
                end = 5.dp,
                bottom = 5.dp
            )
        ) {
            Row {
                Spacer(modifier = Modifier
                    .width(0.dp)
                    .weight(1f))
                Text(
                    text = "Mod.",
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "S.T.",
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            AbilityRow("Strength")
            AbilityRow("Dexterity")
            AbilityRow("Constitution")
            AbilityRow("Intelligence")
            AbilityRow("Wisdom")
            AbilityRow("Charisma")
        }
    }
}

@Composable
fun AbilityRow(name: String, mod: Int = 0, st: Int = 0) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name.substring(0, 3).uppercase(),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier
            .width(0.dp)
            .weight(1f)
        )
        Text(
            text = "+$mod",
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "+$st",
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun AbilityRowAlt(name: String, imageId: Int, mod: Int = 0, st: Int = 0) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = name,
            modifier = Modifier
                .size(25.dp)
                .padding(2.dp)
        )
        Text(
            text = "+$mod",
            modifier = Modifier
                .width(50.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "+$st",
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun AbilitiesPreview() {
    DnDCharacterSheetTheme {
        Abilities()
    }
}