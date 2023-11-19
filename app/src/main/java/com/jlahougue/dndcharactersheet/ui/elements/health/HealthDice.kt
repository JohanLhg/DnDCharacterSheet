package com.jlahougue.dndcharactersheet.ui.elements.health

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jlahougue.dndcharactersheet.ui.elements.FramedBox
import com.jlahougue.dndcharactersheet.ui.theme.DnDCharacterSheetTheme

@Composable
fun HealthDice(modifier: Modifier = Modifier) {
    FramedBox(
        title = "Health Dice",
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    end = 5.dp,
                    bottom = 5.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Health dice :",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                val myData =
                    listOf(MyData(0, "d4"), MyData(1, "d6"), MyData(2, "d8"), MyData(2, "d10"))
                SpinnerSample(
                    list = myData,
                    preselected = myData.first(),
                    onSelectionChanged = {},
                    modifier = Modifier.width(IntrinsicSize.Max)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Health dice count :",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                Text(
                    text = "7",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun HealthDicePreview() {
    DnDCharacterSheetTheme {
        HealthDice(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        )
    }
}

@Composable
fun SpinnerSample(
    list: List<MyData>,
    preselected: MyData,
    onSelectionChanged: (myData: MyData) -> Unit,
    modifier: Modifier = Modifier
) {

    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value

    OutlinedCard(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clickable {
                expanded = !expanded
            }
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = selected.name,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(Icons.Outlined.ArrowDropDown, null, modifier = Modifier.padding(8.dp))

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry.name,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .align(Alignment.Start)
                            )
                        },
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun SpinnerSample_Preview() {
    DnDCharacterSheetTheme {
        val myData = listOf(MyData(0, "Apples"), MyData(1, "Bananas"), MyData(2, "Kiwis"))

        SpinnerSample(
            myData,
            preselected = myData.first(),
            onSelectionChanged = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

data class MyData (
    val id: Int,
    val name: String
)