// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0


package com.amazon.appstore.aadevs.ui.featureschecker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.utils.isScrolled

@Composable
fun FeaturesCheckerScreen(
    isAccelerometerSupported: () -> Boolean,
    isRunningOnWSA: () -> Boolean,
    isHardwareKeyboardConnected: () -> Boolean,
    isExpandedScreen: Boolean,
    onBack: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                if (!isExpandedScreen) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(id = R.string.features_checker),
                            style = MaterialTheme.typography.subtitle2,
                            color = LocalContentColor.current,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .weight(1.5f)
                        )
                    }
                }
            },
                navigationIcon = if (!isExpandedScreen) {
                    {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.cd_navigate_up),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                } else {
                    null
                },
                elevation = if (!lazyListState.isScrolled) 0.dp else 4.dp,
                backgroundColor = MaterialTheme.colors.surface)
        },
    ) { innerPadding ->
        FeaturesCheckerContent(
            isAccelerometerSupported,
            isRunningOnWSA,
            isHardwareKeyboardConnected,
            modifier = Modifier
                // innerPadding takes into account the top and bottom bar
                .padding(innerPadding),
        )
    }
}

@Composable
fun FeaturesCheckerContent(
    isAccelerometerSupported: () -> Boolean,
    isRunningOnWSA: () -> Boolean,
    isHardwareKeyboardConnected: () -> Boolean,
    modifier: Modifier
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Accelerometer Supported " + isAccelerometerSupported().toString())
        Text("Running on WSA " + isRunningOnWSA().toString())
        Text("Hardware Keyboard connected " + isHardwareKeyboardConnected().toString())
    }
}