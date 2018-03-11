# ChessMaster
<hr>
A simple chess game with GUI implementing OOP concepts in Java. Made by 
Ravishankar Joshi, as an independent project to learn various OOP, AI and 
unit-testing concepts.

# Playing the Game
<hr>
The game can be played by running the jar file available in releases folder. 
ChessMaster-1.0.jar has to be given executable permission to run it.
A new player is to be created when he/she plays the game for the first time.
(It's assumed that no two players have the same name.)

# Features
<hr>
<ol>
	<li> A greedy AI that currently implements minimax and alpha-beta pruning
	algorithms with depth 4 (Planning to change the AI to use an external 
	open-source chess engine) </li>
	<li> Many GUI events were handled using observer design pattern </li>
	<li> Unit-tests were written using JUnit 5 library </li>
	<li> GUI made using Swing library of Java </li>
	<li> Undo Move feature was implemented using a stack of moves </li>
	<li> All moves of the game and user statistics are stored into files </li>
	<li> All common complex moves like killing, pawn promotion, castling are 
	supported. </li>
</ol>

# Project Design
<hr>
There are 2 packages. 
The Piece package provides all dummy pieces, their icons and unit-tests for 
their functionality.
The chess package provides most of the functionality and unit-tests of the game. 
AI class plays against a human player in 1 player mode. Movement class handles movements 
of all pieces. Game and user classes retrieve and store respective statistics 
from files. The GUI functions are also handled. 

The project has been documented using javadocs inline with the code.

Currently known bugs are opened as issues on this repo. Please report any new 
bugs or issues that you face.

# Contributors
<hr>
<ul>
	<li> Ravishankar Joshi, for all the code and design of the project. </li>
	<li> Anish Bhobe for some design decisions. </li>
	<li> Lohit Marodia and a few others for play-testing the game and reporting bugs.</li>
</ul>
