package com.jlahougue.dndcharactersheet.ui.elements.health

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
fun HealthBar() {
    FramedBox(title = "Health") {
        Row(
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    end = 5.dp,
                    bottom = 5.dp
                )
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier
                    .padding(5.dp)
                    .height(0.dp)
                    .weight(1f)
                ){
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF3E9400)
                        ),
                        contentPadding = PaddingValues(5.dp),
                        modifier = Modifier
                            .width(35.dp)
                            .fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.plus),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
                Box(modifier = Modifier
                    .padding(5.dp)
                    .height(0.dp)
                    .weight(1f)
                ){
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFBC0606)
                        ),
                        contentPadding = PaddingValues(5.dp),
                        modifier = Modifier
                            .width(35.dp)
                            .fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.minus),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    BasicTextField(
                        value = "0",
                        onValueChange = {},
                        textStyle = TextStyle(
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .padding(5.dp)
                            .requiredWidth(50.dp)
                            .weight(1f)
                            .border(
                                1.dp,
                                Color.Gray,
                                RoundedCornerShape(5.dp)
                            )
                    )
                    BasicTextField(
                        value = "0",
                        onValueChange = {},
                        textStyle = TextStyle(
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .width(IntrinsicSize.Min)
                            .padding(5.dp)
                            .requiredWidth(50.dp)
                            .weight(1f)
                            .border(
                                1.dp,
                                Color.Gray,
                                RoundedCornerShape(5.dp)
                            )
                    )
                }
                Image(
                    painter = painterResource(R.drawable.slash),
                    contentDescription = "Divider",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp)
                        .height(80.dp)
                        .width(80.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier
                    .padding(5.dp)
                    .height(0.dp)
                    .weight(1f)
                ){
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF3E9400)
                        ),
                        contentPadding = PaddingValues(5.dp),
                        modifier = Modifier
                            .width(35.dp)
                            .fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.plus),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
                Box(modifier = Modifier
                    .padding(5.dp)
                    .height(0.dp)
                    .weight(1f)
                ){
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFBC0606)
                        ),
                        contentPadding = PaddingValues(5.dp),
                        modifier = Modifier
                            .width(35.dp)
                            .fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.minus),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
fun HealthPreview() {
    DDCharacterSheetTheme {
        HealthBar()
    }
}