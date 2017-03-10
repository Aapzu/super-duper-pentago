
# Some specifications

## Serialization String of the game state
Serialization String is 42 digits long base 3 number in String format. First 36 characters represent the Board from left to right, top to bottom.
The digits from 37th to 42nd:

| # | Meaning |
| - | - |
|37.| X of last Marble |
|38.| Y of last Marble |
|39.| X of last rotated Tile |
|40.| Y of last rotated Tile |
|41.| Direction of last rotated tile (0/1/2)\*  |
|42.| Whose turn it is (0/1)  |
|43.| Is the game started? (0/1) |
\* 0 = not rotated (initial state), 1 = clockwise, 2 = counter-clockwise


## Heuristics used
### Marbles In Row In A Tile
| No. in a row  | Direction | Points    |
| -             | -         | -         |
| 1             | -         | 0         | 
| 2             | D         | 10        |
| 2             | V/H       | 20       	|
| 3             | D         | 100       |
| 3             | V/H       | 200       |
D = Diagonal, V = Vertical, H = Horizontal


### PossibleLines
| No. in a row\*| Points when mine	| Points when opponents  |
| -             | -             |  -             |
| 0             | 0         	| 0	    	|
| 1             | 10         	| -10  		| 
| 2             | 100         	| -100      	|
| 3             | 1000       	| -1000      	|
| 4             | 10000      	| -10000      	|
| 5             | Infinity      | -Infinity     |
\* Of a single possible line