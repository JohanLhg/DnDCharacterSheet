package com.jlahougue.dndcharactersheet.ui.elements.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jlahougue.dndcharactersheet.ui.elements.FramedBox
import com.jlahougue.dndcharactersheet.ui.theme.DDCharacterSheetTheme

@Composable
fun Skills() {
    FramedBox(title = "Skills") {
        Column(
            modifier = Modifier.padding(
                start = 5.dp,
                end = 5.dp,
                bottom = 5.dp
            )
        ) {
            SkillRow(name = "Acrobatics", ability = "Dex")
            SkillRow(name = "Animal Handling", ability = "Wis")
            SkillRow(name = "Arcana", ability = "Int")
            SkillRow(name = "Athletics", ability = "Str")
            SkillRow(name = "Deception", ability = "Cha")
            SkillRow(name = "History", ability = "Int")
            SkillRow(name = "Insight", ability = "Wis")
            SkillRow(name = "Intimidation", ability = "Cha")
            SkillRow(name = "Investigation", ability = "Int")
            SkillRow(name = "Medicine", ability = "Wis")
            SkillRow(name = "Nature", ability = "Int")
            SkillRow(name = "Perception", ability = "Wis")
            SkillRow(name = "Performance", ability = "Cha")
            SkillRow(name = "Persuasion", ability = "Cha")
            SkillRow(name = "Religion", ability = "Int")
            SkillRow(name = "Sleight of Hand", ability = "Dex")
            SkillRow(name = "Stealth", ability = "Dex")
            SkillRow(name = "Survival", ability = "Wis")
        }
    }
}

@Composable
fun SkillRow(name: String, ability: String, modifier: Int = 0) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "+$modifier",
            modifier = Modifier
                .width(30.dp)
                .padding(5.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 14.sp)
        )
        Text(
            text = "$name ($ability)",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp),
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
fun SkillsPreview() {
    DDCharacterSheetTheme {
        Skills()
    }
}