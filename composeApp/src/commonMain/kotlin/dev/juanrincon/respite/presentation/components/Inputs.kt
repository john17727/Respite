package dev.juanrincon.respite.presentation.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun RespiteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    focusRequester: FocusRequester = FocusRequester()
) {
    BasicTextField(
        value = TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        ),
        onValueChange = { textFieldValue -> onValueChange(textFieldValue.text) },
        modifier = modifier.focusRequester(focusRequester),
        textStyle = textStyle,
    )
}