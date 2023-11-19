package com.jlahougue.dndcharactersheet.ui.elements.weapons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.UNIT_SYSTEM_METRIC
import com.jlahougue.dndcharactersheet.ui.theme.DnDCharacterSheetTheme

@Composable
fun Weapons(
    weapons: List<WeaponView>,
    unitSystem: String,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
    ) {
        items(weapons.size) { index ->
            Weapon(weapons[index], unitSystem)
        }
    }
}

@Composable
fun Weapon(
    weapon: WeaponView,
    unitSystem: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(32.dp)
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF4F2E0F)
            ),
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier
                .padding(1.dp)
                .size(30.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.die),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        Text(
            text = weapon.count.toString(),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(1.dp)
                .background(Color.White)
                .height(30.dp)
                .width(30.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = weapon.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(1.dp)
                .background(Color.White)
                .height(30.dp)
                .width(160.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = if(weapon.modifier >= 0) "+ ${weapon.modifier}"
            else "- ${weapon.modifier}",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(1.dp)
                .background(Color.White)
                .height(30.dp)
                .width(50.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = weapon.damage,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(1.dp)
                .background(Color.White)
                .height(30.dp)
                .width(200.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = weapon.getRangeString(unitSystem),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(1.dp)
                .background(Color.White)
                .height(30.dp)
                .width(160.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun WeaponsPreview() {
    DnDCharacterSheetTheme {
        Weapons(
            weapons = getWeaponsPreviewData(),
            unitSystem = UNIT_SYSTEM_METRIC,
            modifier = Modifier
        )
    }
}

fun getWeaponsPreviewData() = listOf(
    WeaponView(
        "Dagger",
        1,
        2,
        3,
        "1d4 slashing",
        20,
        60,
        120
    ),
    WeaponView(
        "Quarterstaff",
        1,
        1,
        3,
        "1d6 bludgeoning",
        20
    )
)