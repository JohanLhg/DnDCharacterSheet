package com.jlahougue.dndcharactersheet.ui.elements.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.ui.elements.FramedBox
import com.jlahougue.dndcharactersheet.ui.theme.DDCharacterSheetTheme

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
            Row(
                modifier = Modifier.padding(start = 25.dp)
            ) {
                Text(
                    text = "Mod.",
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp)
                )
                Text(
                    text = "S.T.",
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp)
                )
            }
            AbilityRow("Strength", R.drawable.strength, 0, 0)
            AbilityRow("Dexterity", R.drawable.dexterity, 0, 0)
            AbilityRow("Constitution", R.drawable.constitution, 0, 0)
            AbilityRow("Intelligence", R.drawable.intelligence, 0, 0)
            AbilityRow("Wisdom", R.drawable.wisdom, 0, 0)
            AbilityRow("Charisma", R.drawable.charisma, 0, 0)
        }
    }
}

@Composable
fun AbilityRow(name: String, imageId: Int, mod: Int = 0, st: Int = 0) {
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
            style = TextStyle(fontSize = 14.sp)
        )
        Text(
            text = "+$st",
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun AbilitiesPreview() {
    DDCharacterSheetTheme {
        Abilities()
    }
}