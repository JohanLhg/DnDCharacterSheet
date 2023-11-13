package com.jlahougue.dndcharactersheet.ui.elements.weapons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.jlahougue.dndcharactersheet.ui.theme.DnDCharacterSheetTheme

@Composable
fun Weapon() {
    Row(
        modifier = Modifier
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
            text = "2",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(1.dp)
                .background(
                    color = Color.White
                )
                .fillMaxHeight()
                .width(30.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = "Weapon Name",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(1.dp)
                .background(
                    color = Color.White
                )
                .fillMaxHeight()
                .width(160.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = "+3",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(1.dp)
                .background(
                    color = Color.White
                )
                .fillMaxHeight()
                .width(50.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = "2d6 Bludgeoning",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(1.dp)
                .background(
                    color = Color.White
                )
                .fillMaxHeight()
                .width(200.dp)
                .padding(3.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = "6-7 ft. (15-30 ft.)",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(1.dp)
                .background(
                    color = Color.White
                )
                .fillMaxHeight()
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
fun DeathSavesPreview() {
    DnDCharacterSheetTheme {
        Weapon()
    }
}