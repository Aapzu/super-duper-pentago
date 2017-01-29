
# Heuristics

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
| 0             | 0         	| 0	    	|
| 1             | 10         	| -10  		| 
| 2             | 100         	| -100      	|
| 3             | 1000       	| -1000      	|
| 4             | 10000      	| -10000      	|
| 5             | Infinity      | -Infinity     |
\* Of a possible line