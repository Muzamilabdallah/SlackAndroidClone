package dev.baseio.slackclone.uionboarding.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.baseio.slackclone.commonui.theme.SlackCloneSurface
import dev.baseio.slackclone.commonui.theme.SlackCloneTheme
import dev.baseio.slackclone.commonui.theme.SlackCloneColorProvider
import dev.baseio.slackclone.commonui.theme.SlackCloneTypography
import dev.baseio.slackclone.navigator.ComposeNavigator
import dev.baseio.slackclone.navigator.SlackRoute
import dev.baseio.slackclone.navigator.SlackScreen

@Composable
fun CommonInputUI(
  composeNavigator: ComposeNavigator,
  TopView: @Composable (modifier: Modifier) -> Unit,
  subtitleText: String
) {
  val scaffoldState = rememberScaffoldState()

  SlackCloneTheme {
    Scaffold(
      backgroundColor = SlackCloneColorProvider.colors.uiBackground,
      contentColor = SlackCloneColorProvider.colors.textSecondary,
      modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding(),
      scaffoldState = scaffoldState,
      snackbarHost = {
        scaffoldState.snackbarHostState
      }
    ) { innerPadding ->
      Box(modifier = Modifier.padding(innerPadding)) {
        SlackCloneSurface(
          color = SlackCloneColorProvider.colors.uiBackground,
          modifier = Modifier
        ) {
          ConstraintLayout(
            modifier = Modifier
              .padding(12.dp)
              .navigationBarsPadding()
              .imePadding()
              .fillMaxHeight()
              .fillMaxWidth()
          ) {
            // Create references for the composables to constrain
            val (inputView, subtitle, button) = createRefs()

            TopView(modifier = Modifier.constrainAs(inputView) {
              top.linkTo(parent.top)
              bottom.linkTo(button.top)
              start.linkTo(parent.start)
              end.linkTo(parent.end)
            })
            SubTitle(modifier = Modifier.constrainAs(subtitle) {
              top.linkTo(inputView.bottom)
            }, subtitleText)
            NextButton(modifier = Modifier.constrainAs(button) {
              bottom.linkTo(parent.bottom)
              start.linkTo(parent.start)
              end.linkTo(parent.end)
            }, composeNavigator)
          }
        }
      }

    }
  }
}

@Composable
fun NextButton(modifier: Modifier = Modifier, composeNavigator: ComposeNavigator) {
  Button(
    onClick = {
      composeNavigator.navigate(SlackScreen.Dashboard.name) {
        this.popUpTo(SlackRoute.OnBoarding.name) {
          this.inclusive = true
        }
      }
    },
    modifier
      .fillMaxWidth()
      .height(50.dp)
      .padding(top = 8.dp),
    colors = ButtonDefaults.buttonColors(
      backgroundColor = SlackCloneColorProvider.colors.buttonColor
    )
  ) {
    Text(
      text = "Next",
      style = SlackCloneTypography.subtitle2.copy(color = SlackCloneColorProvider.colors.buttonTextColor)
    )
  }
}

@Composable
private fun SubTitle(modifier: Modifier = Modifier, subtitleText: String) {
  Text(
    subtitleText,
    modifier = modifier
      .fillMaxWidth()
      .wrapContentWidth(align = Alignment.Start),
    style = SlackCloneTypography.subtitle2.copy(
      color = SlackCloneColorProvider.colors.textPrimary.copy(alpha = 0.8f),
      fontWeight = FontWeight.Normal,
      textAlign = TextAlign.Start
    )
  )
}

