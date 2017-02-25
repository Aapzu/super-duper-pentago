
# Performance
The elements in this project, which need to be considered performance-wise, are the heuristics and alpha-beta pruning. The heuristics are not the best possible yet, but I’ve done some tests for the pruning class.
The amount of nodes in each layer is roughly 270^i, where i is the depth in the tree. So one may guess the performance of the program decreases quite radically, when i is increased. However, here are the numbers with i values 2 and 3:

| i | # tests 	| average time 	|
| 2 | 10	| 2881ms	|
| 3 | 2 	| 50499ms	|

I’m sure the numbers can be decreased, I’ve now just bee fighting with a few bugs that prevented this whole test, so these are the numbers I got now.