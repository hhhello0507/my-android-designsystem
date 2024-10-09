package com.hhhello0507.mydesignsystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.hhhello0507.mydesignsystem.R
import com.hhhello0507.mydesignsystem.extension.ButtonState
import com.hhhello0507.mydesignsystem.foundation.MyTheme
import com.hhhello0507.mydesignsystem.foundation.iconography.MyIcon
import com.hhhello0507.mydesignsystem.foundation.util.MyPreviews


@Composable
fun MyLikeButton(
    modifier: Modifier = Modifier,
    like: Int,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val color =
        if (enabled) MyTheme.colorScheme.likeSecondary
        else MyTheme.colorScheme.buttonPrimaryDisabled
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )
    val animColor by animateColorAsState(
        targetValue = if (buttonState == ButtonState.Idle) {
            color
        } else {
            color
        },
        label = "",
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minHeight = 20.dp, minWidth = 1.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(buttonState) {
                awaitPointerEventScope {
                    buttonState = if (buttonState == ButtonState.Hold) {
                        waitForUpOrCancellation()
                        ButtonState.Idle
                    } else {
                        awaitFirstDown(false)
                        ButtonState.Hold
                    }
                }
            },
        colors = ButtonDefaults.buttonColors(
            containerColor = animColor,
            contentColor = MyTheme.colorScheme.likePrimary,
            disabledContainerColor = animColor,
            disabledContentColor = MyTheme.colorScheme.textDisabled,
        ),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
        interactionSource = interactionSource,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textColor = if (enabled) {
                MyTheme.colorScheme.likePrimary
            } else {
                MyTheme.colorScheme.textDisabled
            }
            MyIcon(
                id = R.drawable.ic_heart,
                color = textColor
            )
            Text(
                text = like.toString(),
                style = MyTheme.typography.bodyMedium,
                color = textColor
            )
        }
    }
}

@Composable
@MyPreviews
private fun Preview() {
    MyTheme {
        Column(
            modifier = Modifier
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MyLikeButton(like = 0) {

            }
            MyLikeButton(like = 311, enabled = false) {

            }
        }
    }
}