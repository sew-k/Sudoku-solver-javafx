# Sudoku Solver

## Introduction

The main purpose of this application is to practice building and developing a Java application with view layer using JavaFX, 
including the implementation of the bubble sorting algorithm.

The core of the application was completed during *Java Developer* bootcamp in April 2023. 
The next step is to implement clean code best practices and a few Creational Design Patterns.

### Application features

- Solve Sudoku board with game difficulty set on: easy/ medium/ hard
- Automatically (CPU) solving any uploaded Sudoku board
- Checking if given Sudoku board is valid
- Managing the loading, saving, and custom setup of Sudoku boards

### Technologies used

- Java JDK 17
- JavaFX v17.0.2
- JUnit v5.9.2

## Getting started

- To test this Game, download it (clone GitHub repository), build, compile and run in favourite Java IDE

### Requirements

- Java JDK 17 installed
- IDE with Gradle build tool

### How to use

## Features to be implemented

- `menu -> Game -> New game` - Starting new game with a blank Sudoku oard
- `menu -> Game -> Load game` - Loading a previously saved game
- `menu -> Game -> Save game` - Saving the game - Sudoku Board with fixed elements and those filled by Player
- `menu -> Board -> New random board` - Generating a random board drawn by the CPU
- `menu -> Settings -> Difficulty settings -> medium` - Sudoku elements filled by the Player are all gray 
(not marked as valid or invalid)
- `menu -> Settings -> Difficulty settings` -> hard - Passage of time resulting in the disappearance of last filled values
- `menu -> Settings -> Board settings` - To consider if it is even necessary
- Improving view layer visuals and UX

## Troubleshooting