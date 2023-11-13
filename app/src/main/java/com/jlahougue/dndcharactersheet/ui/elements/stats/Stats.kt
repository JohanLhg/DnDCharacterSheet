package com.jlahougue.dndcharactersheet.ui.elements.stats

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jlahougue.dndcharactersheet.ui.elements.FramedBox
import com.jlahougue.dndcharactersheet.ui.theme.DnDCharacterSheetTheme

@Composable
fun Stats() {
    FramedBox(
        title = "Stats",
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 5.dp,
                    end = 5.dp,
                    bottom = 5.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                Text(
                    text = "Init.",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                )
                Spacer(modifier = Modifier.width(0.dp).weight(1f))
                Text(
                    text = "+2",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(50.dp)
                        .padding(horizontal = 5.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                Text(
                    text = "A.C.",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                )
                Spacer(modifier = Modifier.width(0.dp).weight(1f))
                BasicTextField(
                    value = "0",
                    onValueChange = {},
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .width(50.dp)
                        .padding(horizontal = 5.dp)
                        .border(
                            1.dp,
                            Color.Gray,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(horizontal = 5.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                Text(
                    text = "Speed",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                )
                Spacer(modifier = Modifier.width(0.dp).weight(1f))
                BasicTextField(
                    value = "0",
                    onValueChange = {},
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .width(50.dp)
                        .padding(horizontal = 5.dp)
                        .border(
                            1.dp,
                            Color.Gray,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(horizontal = 5.dp)
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
fun StatsPreview() {
    DnDCharacterSheetTheme {
        Stats()
    }
}