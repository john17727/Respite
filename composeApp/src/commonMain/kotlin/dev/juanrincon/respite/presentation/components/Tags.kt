package dev.juanrincon.respite.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CallToActionTag(
    callToActionText: String,
    onCallToActionClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColorVariant: Color = Color(0xFF1D4451),
    onBackgroundColor: Color = Color(0xFFfAEBC1),
    modifier: Modifier = Modifier
) {
    Surface(
        color = backgroundColor,
        shape = CutCornerShape(topStart = 48.dp, topEnd = 48.dp),
        shadowElevation = 8.dp,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TagCutout()
            Text(
                text = callToActionText.uppercase(),
                color = onBackgroundColor,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            TagButton(
                text = "Start Packing",
                onClick = onCallToActionClick,
                backgroundColor = backgroundColorVariant,
                textColor = Color(0xFFFF6D3D),
                modifier = Modifier.fillMaxWidth().height(64.dp)
            )
        }
    }
}

@Composable
fun InputTag(
    onSaveInputClick: (String) -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColorVariant: Color = Color(0xFF1D4451),
    onBackgroundColor: Color = Color(0xFFfAEBC1),
    focusRequester: FocusRequester = FocusRequester(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    var inputValue by remember { mutableStateOf("") }
    Surface(
        color = backgroundColor,
        shape = CutCornerShape(topStart = 48.dp, topEnd = 48.dp),
        shadowElevation = 8.dp,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TagCutout()
            RespiteTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                color = onBackgroundColor,
                textStyle = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                focusRequester = focusRequester,
            )
            TagButton(
                text = "Create Trip",
                onClick = { onSaveInputClick(inputValue) },
                backgroundColor = backgroundColorVariant,
                textColor = Color(0xFFFF6D3D),
                modifier = Modifier.fillMaxWidth().height(64.dp)
            )
        }
    }
}

@Composable
private fun TagCutout(
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = Color(0xFF1D4451)
) {
    Canvas(modifier = Modifier.size(48.dp), onDraw = {
        val rectWidth = size.width / 3
        drawRect(
            color = borderColor,
            size = Size(width = rectWidth, height = size.height / 2),
            topLeft = Offset(x = (size.width / 2) - (rectWidth / 2), y = 0f)
        )
        drawCircle(
            color = borderColor,
            radius = 80f,
            center = Offset(x = size.width / 2, y = size.height - (size.height / 5))
        )
        drawCircle(
            color = backgroundColor,
            radius = 40f,
            center = Offset(x = size.width / 2, y = size.height - (size.height / 5))
        )
        drawLine(
            color = backgroundColor,
            start = Offset(x = size.width / 2, y = 0f),
            end = Offset(x = size.width / 2, y = size.height - (size.height / 5)),
            strokeWidth = 2f
        )
    })
}