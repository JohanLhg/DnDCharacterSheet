package com.jlahougue.dndcharactersheet.ui.elements.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.views.SkillView
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SkillRepository
import com.jlahougue.dndcharactersheet.ui.elements.FramedBox
import com.jlahougue.dndcharactersheet.ui.theme.DnDCharacterSheetTheme

@Composable
fun Skills(skills: List<SkillView>, modifier: Modifier = Modifier) {
    FramedBox(title = "Skills", modifier = modifier) {
        Column {
            for (skill in skills) {
                SkillRow(skill)
            }
        }
    }
}

@Composable
fun SkillRow(skill: SkillView, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 3.dp)
    ) {
        Text(
            text = if (skill.modifier >= 0) "+${skill.modifier}"
            else skill.modifier.toString(),
            modifier = Modifier
                .width(30.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = stringResource(
                id = R.string.skill_display,
                stringResource(SkillRepository.getNameId(skill.name)),
                stringResource(AbilityRepository.getModifierNameId(skill.modifierType))
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
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
fun SkillsPreview() {
    DnDCharacterSheetTheme {
        Skills(
            skills = getSkillsPreviewData(),
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Min)
        )
    }
}

fun getSkillsPreviewData() = listOf(
    SkillView(1, SkillRepository.ACROBATICS, AbilityRepository.DEXTERITY, 2, false),
    SkillView(1, SkillRepository.ANIMAL_HANDLING, AbilityRepository.WISDOM, 5, false),
    SkillView(1, SkillRepository.ARCANA, AbilityRepository.INTELLIGENCE, 6, false),
    SkillView(1, SkillRepository.ATHLETICS, AbilityRepository.STRENGTH, 0, false),
    SkillView(1, SkillRepository.DECEPTION, AbilityRepository.CHARISMA, 3, false),
    SkillView(1, SkillRepository.HISTORY, AbilityRepository.INTELLIGENCE, 6, false),
    SkillView(1, SkillRepository.INSIGHT, AbilityRepository.WISDOM, 5, false),
    SkillView(1, SkillRepository.INTIMIDATION, AbilityRepository.CHARISMA, 3, false),
    SkillView(1, SkillRepository.INVESTIGATION, AbilityRepository.INTELLIGENCE, 6, false),
    SkillView(1, SkillRepository.MEDICINE, AbilityRepository.WISDOM, 5, false),
    SkillView(1, SkillRepository.NATURE, AbilityRepository.INTELLIGENCE, 6, false),
    SkillView(1, SkillRepository.PERCEPTION, AbilityRepository.WISDOM, 5, false),
    SkillView(1, SkillRepository.PERFORMANCE, AbilityRepository.CHARISMA, 3, false),
    SkillView(1, SkillRepository.PERSUASION, AbilityRepository.CHARISMA, 3, false),
    SkillView(1, SkillRepository.RELIGION, AbilityRepository.INTELLIGENCE, 6, false),
    SkillView(1, SkillRepository.SLEIGHT_OF_HAND, AbilityRepository.DEXTERITY, 2, false),
    SkillView(1, SkillRepository.STEALTH, AbilityRepository.DEXTERITY, 2, false),
    SkillView(1, SkillRepository.SURVIVAL, AbilityRepository.WISDOM, 5, false),
)