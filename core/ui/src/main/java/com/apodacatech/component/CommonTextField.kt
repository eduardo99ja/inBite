/*
 * Copyright (C) 2024 Eduardo Apodaca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apodacatech.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InBiteTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    color: Color = Color.Unspecified,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    helperText: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable() (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions : KeyboardActions = KeyboardActions.Default
) {
    Surface(modifier) {
        Column {
            OutlinedTextField(
                value = value,
                label = { Text(text = label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(label),
                onValueChange = onValueChange,
                leadingIcon = leadingIcon,
                textStyle = TextStyle(color, fontSize = fontSize),
                isError = isError,
                visualTransformation = visualTransformation,
                shape = RoundedCornerShape(8.dp),
                trailingIcon = trailingIcon,
                prefix = prefix,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
            )
            if (helperText.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = helperText, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun BasicInBiteTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    onTextChange: (String) -> Unit,
    maxLines: Int = Int.MAX_VALUE
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChange,
        textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onPrimary),
        maxLines = maxLines,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { inlineTextField ->
            AnimatedVisibility(visible = value.isBlank()) {
                Text(
                    text = label,
                    color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray,
                    style = textStyle
                )
            }
            inlineTextField()
        }
    )
}
