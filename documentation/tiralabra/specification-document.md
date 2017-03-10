
# Specification

### Topic
In this practice work I'm going to implement an AI/bot for playing Pentago. Pentago is a two player game, of which I made an Java implementation as a Java practise work earlier. (Javalabra, [Documentation](../javalabra/topic-definition.md))

### Code
I'm going to use the same repository for this course, as I used a year ago. For that reason the repository is already full of code. The new classes and logic will be representing th
e work I did for this course.

### Algorithms And Data Structures\*
The algorithm I use will be negamax pruned with alpha-beta pruning, and some heuristics. It would be too slow (i.e. impossible) to calculate the whole game beforehand to know the absolute best move, so I have to use some kind of heuristics to assign points for every situation.

For alpha-beta pruning I'm going to need at least some kind of stack, maybe also some other data structures. These I will implement later on the course.

\* These may change during the course

### The Problem
The problem is the good old player vs. computer. Can a computer play Pentago in a way, in which it will offer any challenge against the human playing with it?

The algorithm I chose for the work is the most widely used in this kind of situations, so I chose it without even thinking others.