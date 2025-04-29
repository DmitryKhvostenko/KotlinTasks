package com.example.lessontictactoe

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lessontictactoe.ui.theme.LessonTicTacToeTheme
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var isPaused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Button(
            onClick = {
                isPaused = !isPaused
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("Пауза")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tic Tac Toe",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            GameBoard(isPaused)
        }
    }



    if (isPaused) {
        Box(
            modifier = Modifier
                .fillMaxSize()  // Тінь покриває весь екран
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { isPaused = false },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Button(
                onClick = {
                    isPaused = false
                }
            ) {
                Text("Продовжити")
            }
        }

    }
}

@Composable
fun GameBoard(isPaused: Boolean) {
    val dim = 3
    val field = remember { mutableStateListOf(*Array(dim * dim) { CellState.EMPTY }) }
    var currentPlayer by remember { mutableStateOf(Player.CROSS) }
    var gameState by remember { mutableStateOf(GameState.IN_PROGRESS) }
    var crossScore by remember { mutableStateOf(0) }
    var noughtScore by remember { mutableStateOf(0) }
    var timer by remember { mutableStateOf(10) }

    val crossColor = MaterialTheme.colorScheme.primary
    val noughtColor = MaterialTheme.colorScheme.secondary

    LaunchedEffect(gameState, isPaused) {
        if (gameState == GameState.IN_PROGRESS && !isPaused) {
            while (timer > 0 && !isPaused) {
                delay(1000)
                timer--
            }
            if (gameState == GameState.IN_PROGRESS && !isPaused) {
                currentPlayer = if (currentPlayer == Player.CROSS) Player.NOUGHT else Player.CROSS
                timer = 10
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text("Рахунок: ", modifier = Modifier.padding(end = 4.dp))
            Text("X - $crossScore", color = crossColor, modifier = Modifier.padding(end = 8.dp))
            Text("|", modifier = Modifier.padding(horizontal = 4.dp))
            Text("0 - $noughtScore", color = noughtColor)
        }

        Text("Час на хід: $timer сек.", modifier = Modifier.padding(bottom = 16.dp))

        for (row in 0 until dim) {
            Row {
                for (col in 0 until dim) {
                    val index = row * dim + col
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp)
                            .border(2.dp, MaterialTheme.colorScheme.tertiary)
                            .clickable {
                                if (field[index] == CellState.EMPTY && gameState == GameState.IN_PROGRESS) {
                                    field[index] = currentPlayer.mark
                                    gameState = checkGameState(field)
                                    if (gameState == GameState.IN_PROGRESS) {
                                        currentPlayer = if (currentPlayer == Player.CROSS) Player.NOUGHT else Player.CROSS
                                    } else {
                                        if (gameState == GameState.CROSS_WIN) crossScore++
                                        if (gameState == GameState.NOUGHT_WIN) noughtScore++
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        val (symbol, color) = when (field[index]) {
                            CellState.CROSS -> "X" to crossColor
                            CellState.NOUGHT -> "0" to noughtColor
                            CellState.EMPTY -> "" to MaterialTheme.colorScheme.onBackground
                        }
                        Text(
                            text = symbol,
                            style = MaterialTheme.typography.headlineMedium,
                            color = color
                        )
                    }
                }
            }
        }

        when (gameState) {
            GameState.CROSS_WIN -> Text(
                text = "Перемога: X",
                color = crossColor,
                modifier = Modifier.padding(top = 16.dp)
            )
            GameState.NOUGHT_WIN -> Text(
                text = "Перемога: 0",
                color = noughtColor,
                modifier = Modifier.padding(top = 16.dp)
            )
            GameState.DRAW -> Text("Нічия", modifier = Modifier.padding(top = 16.dp))
            else -> {}
        }

        androidx.compose.material3.Button(
            onClick = {
                for (i in field.indices) field[i] = CellState.EMPTY
                currentPlayer = if (currentPlayer == Player.CROSS) Player.NOUGHT else Player.CROSS
                gameState = GameState.IN_PROGRESS
                timer = 10
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(if (gameState == GameState.IN_PROGRESS) "Скинути гру" else "Зіграти знову")
        }
        androidx.compose.material3.Button(
            onClick = {
                crossScore = 0
                noughtScore = 0
                timer = 10
                for (i in field.indices) field[i] = CellState.EMPTY
                currentPlayer = Player.CROSS
                gameState = GameState.IN_PROGRESS
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Скинути рахунок")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview()
{
    LessonTicTacToeTheme {
        MainScreen()
    }
}