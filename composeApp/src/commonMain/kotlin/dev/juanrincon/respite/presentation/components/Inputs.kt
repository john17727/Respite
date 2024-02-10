package dev.juanrincon.respite.presentation.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun RespiteTextField(
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    focusRequester: FocusRequester = FocusRequester(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BasicTextField(
        value = textFieldValue,
        onValueChange = onValueChange,
        modifier = modifier.focusRequester(focusRequester),
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        visualTransformation = VisualTransformation {
            TransformedText(AnnotatedString(it.text.uppercase()), OffsetMapping.Identity)
        }
    )
}