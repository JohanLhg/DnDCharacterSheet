package com.jlahougue.dndcharactersheet.ui.elements.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.ui.elements.FramedBox
import com.jlahougue.dndcharactersheet.ui.theme.DnDCharacterSheetTheme

@Composable
fun Abilities(
    abilities: List<AbilityView>,
    modifier: Modifier = Modifier
) {
    FramedBox(title = "Abilities", modifier = modifier) {
        Column {
            Row {
                Spacer(modifier = Modifier.width(0.dp).weight(1f))
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
            for (ability in abilities)
                AbilityRow(ability)
        }
    }
}

@Composable
fun AbilityRow(ability: AbilityView) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = ability.name.substring(0, 3).uppercase(),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.width(0.dp).weight(1f)
        )
        Text(
            text = if (ability.modifier >= 0) "+${ability.modifier}"
            else ability.modifier.toString(),
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = if (ability.savingThrow >= 0) "+${ability.savingThrow}"
            else ability.savingThrow.toString(),
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
        Abilities(
            abilities = getAbilitiesPreviewData(),
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
        )
    }
}

fun getAbilitiesPreviewData() = listOf(
    AbilityView(1, AbilityRepository.STRENGTH, 10, 0, 0, false),
    AbilityView(1, AbilityRepository.DEXTERITY, 14, 2, 2, false),
    AbilityView(1, AbilityRepository.CONSTITUTION, 12, 1, 1, false),
    AbilityView(1, AbilityRepository.INTELLIGENCE, 22, 6, 9, true),
    AbilityView(1, AbilityRepository.WISDOM, 15, 2, 5, true),
    AbilityView(1, AbilityRepository.CHARISMA, 16, 3, 3, false),
)