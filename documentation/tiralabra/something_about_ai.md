
# Some specifications

## Serialization String of the game state
Serialization String is 42 digits long base 3 number in String format. First 36 characters represent the Board in following order:
In each tile: from left to right, from up to down.
And the tiles from top to bottom, left to right.
The digits from 37th to 42nd:

| # | Meaning |
| - | - |
|37.| X of last Marble |
|38.| Y of last Marble |
|39.| X of last rotated Tile |
|40.| Y of last rotated Tile |
|41.| Direction of last rotated tile (0/1/2)\*  |
|42.| Whose turn it is (0/1)  |
\* 0 = not rotated (initial state), 1 = clockwise, 2 = counter-clockwise


## Heuristics used
### Marbles In Row In A Tile
| No. in a row  | Direction | Points    |
| -             | -         | -         |
| 1             | -         | 0         | 
| 2             | D         | 1         |
| 2             | V/H       | 2         |
| 3             | D         | 3         |
| 3             | V/H       | 4         |
D = Diagonal, V = Vertical, H = Horizontal


### PossibleLines
| No. in a row\*| For player	| For opponent  |
| -             | -             |  -             |
| 0             | 0         	| 0	    	|
| 1             | 10         	| -10  		| 
| 2             | 100         	| -100      	|
| 3             | 1000       	| -1000      	|
| 4             | 10000      	| -10000      	|
| 5             | Infinity      | -Infinity     |
\* Of a possible line