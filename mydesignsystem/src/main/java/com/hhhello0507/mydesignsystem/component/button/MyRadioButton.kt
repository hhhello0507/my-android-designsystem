package com.hhhello0507.mydesignsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.hhhello0507.mydesignsystem.R
import com.hhhello0507.mydesignsystem.extension.ButtonState
import com.hhhello0507.mydesignsystem.foundation.MyTheme
import com.hhhello0507.mydesignsystem.foundation.util.MyPreviews

@Composable
fun MyRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    @DrawableRes selectedIcon: Int = R.drawable.ic_radio,
    @DrawableRes unselectedIcon: Int = R.drawable.ic_radio_unselected,
    shape: Shape = RoundedCornerShape(12.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )

    val primary = if (isSelected) {
        MyTheme.colorScheme.radioButtonPrimary
    } else {
        MyTheme.colorScheme.radioButtonPrimaryDisabled
    }
    val secondary = if (isSelected) {
        MyTheme.colorScheme.radioButtonSecondary
    } else {
        MyTheme.colorScheme.radioButtonSecondaryDisabled
    }

    val colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = Color.Transparent,
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
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
            }
            .border(
                border = BorderStroke(width = 1.5.dp, color = primary),
                shape = shape
            ),
        colors = colors,
        shape = shape,
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
        interactionSource = interactionSource,
    ) {
//            if (isLoading) {
//                RiveAnimation(
//                    resId = R.raw.loading_dots,
//                    contentDescription = "loading gif",
//                    autoplay = true,
//                    animationName = type.animName,
//                )
//            } else {

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            com.hhhello0507.mydesignsystem.foundation.iconography.MyIcon(
                modifier = Modifier
                    .size(24.dp),
                id = if (isSelected) selectedIcon else unselectedIcon,
                color = primary
            )
            Text(
                text = text,
                style = MyTheme.typography.bodyRegular,
                color = secondary
            )
        }
//            }
    }
}

@Composable
@MyPreviews
private fun Preview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MyTheme.colorScheme.background)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MyRadioButton(text = "Server", isSelected = true) {

            }
            MyRadioButton(text = "Web", isSelected = false) {

            }
        }
    }
}