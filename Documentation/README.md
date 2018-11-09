Algorithm Documentation
===============================================================================
Overview
-------------------------------------------------------------------------------

#### Table of contents:

- **`Board#findPlaceMonster(String name)`**
  - **Premises the algorithm relies on:**
    1. The grid row width.
    2. A list that stores the linear index of each empty spot in the grid.
  - **How a monster spot is derived from a linear grid index:**
    0. Gathered facts
    1. Finding the y-coordinates of the current monster spot
    2. Finding the x-coordinates of the current monster spot
- **`Board#launchAttack(int[] pos)`**
  - **Premises the algorithm relies on:**
    1. Monster's attacking grid position
  - **How monster's attack range is derived from its own location:**
    1. Find monster attack range locations
    2. Check if the spot to be attacked is a monster
    3. Use linear index formula for finding the victim details
    4. Increase monster score by 1 for every successful attack
  - **Complexity analysis**


`Board#findPlaceMonster(String name)`
-------------------------------------------------------------------------------

### Premises the algorithm relies on:

#### 1. The grid row width.

The grid row width is given when the grid is initialised as a  variable `X`. (@see `#Board(int Y, int X)`)

#### 2. A list that stores the linear index of each empty spot in the grid.

To make this more clear here is the grid with it's array index for a 5x5 grid:

```
0 1 2 3 4  ->  Array 0
0 1 2 3 4  ->  Array 1
0 1 2 3 4  ->  Array 2
0 1 2 3 4  ->  Array 3
0 1 2 3 4  ->  Array 4
```

And here is how and what the list we are talking about stores from the grid: (`x` = ignored)

```
x x x x x  ->  Array 0
x 0 1 2 x  ->  Array 1
x 3 4 5 x  ->  Array 2
x 6 7 8 x  ->  Array 3
x x x x x  ->  Array 4
```

So for a `5x5` grid, we store 9 spots only.

This done by storing the empty spots linear index in a doubly linked-list when constructing the initial board.

```java
for (int i = 1, ii = 0; i <= Y-2; i++) {
    for (int j = 1; j <= X-2; j++, ii++) {
        board[i][j] = EMPTY;

        /** Add the linear index of each empty spot. */
        emptySpotList.add(ii);
    }
}
```


### How a monster spot (grid coordinates) is derived from a linear grid index:

#### Gathered facts:

**1. We know the upper bound grid length of the empty spots.**

Since a grid can only have a hedge on the board edges only. The number of hedges per row will always be `2`. Thus, we can find the number of valid monster spots in a single row:

```java
/** Number of valid monster spots in a single row */
int rowLen = X-2;
```

**2. Each row (`X`) is represented by a column (`Y`) multiplier:**

Consider we have a `5*5` board. Our spots of interest (valid monster spots) will be `(Y-2)*(X-2)`; `3*3`:

```
x x x x x  ->  Array 0
x 0 1 2 x  ->  Array 1
x 3 4 5 x  ->  Array 2
x 6 7 8 x  ->  Array 3
x x x x x  ->  Array 4
```

Which means the following grid coordinates (`Y`,`X`) are indexed as follow:

```
i,j: 1,1 - index: 0
i,j: 1,2 - index: 1
i,j: 1,3 - index: 2
i,j: 2,1 - index: 3
i,j: 2,2 - index: 4
i,j: 2,3 - index: 5
i,j: 3,1 - index: 6
i,j: 3,2 - index: 7
i,j: 3,3 - index: 8
```

As a result, we can consider the column index (`Y`) to be a multiplier to the row index (`X`) to find the linear index of each spot. This works because we know as a fact that the upper bound grid length of the empty spots in a row is `X-2`.

So to find a linear index for `grid[i][j]` we multiple `i` with `X-2` and then add it to `j`:

```
i,j: 1,1 - index: 0 | [(i-1)*(X-2)]+(j-1): 0
i,j: 1,2 - index: 1 | [(i-1)*(X-2)]+(j-1): 1
i,j: 1,3 - index: 2 | [(i-1)*(X-2)]+(j-1): 2
i,j: 2,1 - index: 3 | [(i-1)*(X-2)]+(j-1): 3
i,j: 2,2 - index: 4 | [(i-1)*(X-2)]+(j-1): 4
i,j: 2,3 - index: 5 | [(i-1)*(X-2)]+(j-1): 5
i,j: 3,1 - index: 6 | [(i-1)*(X-2)]+(j-1): 6
i,j: 3,2 - index: 7 | [(i-1)*(X-2)]+(j-1): 7
i,j: 3,3 - index: 8 | [(i-1)*(X-2)]+(j-1): 8
```

**NOTE:** we are taking `1` from `i` and `j` because of Arrays and Linked-lists count indexes starting from `0`.

**Therefore, we end up with the following useful formula for our grid:**
`s = [(i-1)*(X-2)]+(j-1)`

Where `s` is the linear index of a spot in the grid.


#### 1. Finding the y-coordinates of the current monster spot

If we are given a spot `s` (a linear index in a grid), we can find the y-axis `y` by finding the upper bound linear index value of row `x` that lies within the to-be-found `y` and then divide it by `X-2` (the number of valid monsters spots in a single row).

**We can do this by:**
1. Finding the remainder of `s % (X-2)`
2. Subtract `(X-2)` from the step `1`.
3. Then add `s` to step `2` (This is the upper bound linear index value of row `x` within `y`)
4. Then divide step `3` by `(X-2)`

**Examples using our previous `5*5` grid:**
```
index: 0  |  (0 + (3-(0%3)) ) / 3  =  0
index: 1  |  (1 + (3-(1%3)) ) / 3  =  1
index: 2  |  (2 + (3-(2%3)) ) / 3  =  2
index: 3  |  (3 + (3-(3%3)) ) / 3  =  3
index: 4  |  (4 + (3-(4%3)) ) / 3  =  4
index: 5  |  (5 + (3-(5%3)) ) / 3  =  5
index: 6  |  (7 + (3-(7%3)) ) / 3  =  6
index: 7  |  (6 + (3-(6%3)) ) / 3  =  7
index: 8  |  (8 + (3-(8%3)) ) / 3  =  8
```

**So the formula for finding the y-coordinates is:**
`y = (s + ( (X-2) - (s%(X-2))) ) / (X-2)`


#### 2. Finding the x-coordinates of the current monster spot

Finding the spot's x-axis is easy after finding the y-axis.

we can use our linear index formula to make `j` (x-axis) the subject of the formula `s = [(i-1)*(X-2)]+(j-1)`:

- `j =  s - ((i-1)*(X-2)) + 1`

**Examples using our previous `5*5` grid:**
```
index: 0  |  0 - ((1-1)*3) + 1  =  1
index: 1  |  1 - ((1-1)*3) + 1  =  2
index: 2  |  2 - ((1-1)*3) + 1  =  3
index: 3  |  3 - ((2-1)*3) + 1  =  1
index: 4  |  4 - ((2-1)*3) + 1  =  2
index: 5  |  5 - ((2-1)*3) + 1  =  3
index: 6  |  6 - ((3-1)*3) + 1  =  1
index: 7  |  7 - ((3-1)*3) + 1  =  2
index: 8  |  8 - ((3-1)*3) + 1  =  3
```

**So the formula for finding the x-coordinates is:**
`x =  s - ((y-1)*(X-2)) + 1`



`Board#launchAttack(int[] pos)`
-------------------------------------------------------------------------------

### Premises the algorithm relies on:

#### 1. Monster's attacking grid position.

This is passed by as an argument to the method.

### How monster's attack range is derived from it's own location:

#### 1. Find monster attack range locations:

Since the monster attack range is `{ north, south, west, east }`. We can use the monster's grid position to get it's cardinal directions coordinates:

```java
/** monster attack range: { north, south, west, east } */
int[][] atkRange = new int[][] {
    {pos[0]-1,pos[1]}, {pos[0]+1,pos[1]}, {pos[0],pos[1]-1}, {pos[0],pos[1]+1}
};
```

Where `pos[0]` is the y-axis and `pos[1]` is the x-axis.


#### 2. Check if the spot to be attacked is a monster:

While looping through cardinal direction spot value. A check will be upheld to see if any monsters exist: (If it is not an `EDGE` or `EMPTY`, then it is a monster)

```java
/** Attempt to attack all cardinal directions within the monster's range */
for (int i = 0; i < atkRange.length; i++) {
    /** Current cardinal direction to attack */
    int[] atkDir = atkRange[i];

    /** Value of spot to be attacked */
    char atkSpot = board[atkDir[0]][atkDir[1]];
    /** Skip spot if it is an EDGE or EMPTY */
    if (atkSpot == EDGE || atkSpot == EMPTY) continue;

    /** ...rest of loop block... */
}
```

#### 3. Use linear index formula for finding the victim details:

Using the formula for finding the linear index of spot in grid (`s = [(i-1)*(X-2)]+(j-1)`) we can get the victim location from the Array of active monsters on board and add location back to the list of empty spots available:

```java
/** Using the formula for finding the linear index of spot in grid */
int casualtyLocation = ((atkDir[0]-1)*(X-2))+(atkDir[1]-1);

/** Remove dead monster from active monsters array */
monstArr[casualtyLocation] = null;

/** Add dead monster's location to empty spot list */
emptySpotList.add(casualtyLocation);
```

#### 4. Increase monster score by 1 for every successful attack.


### Complexity analysis:

The algorithm in short is `O(1)`.

This is mainly because I am using an array to access items without searching the array by using my formula, and tracking empty spots using a linked list by only inserting at the end of the list:

```java
/** Find casualty-to-be location using the linear index formula: */
int casualtyLocation = ((atkDir[0]-1)*(X-2))+(atkDir[1]-1);

/** Remove dead monster from active monsters array */
monstArr[casualtyLocation] = null;

/** Add dead monster's location to empty spot list */
emptySpotList.add(casualtyLocation);
```

- The access time for arrays is `O(1)` (array items are stored at contiguous locations).

- The Insertion time for a linked list is `O(1)` (when we know the position of where we need to insert. In our case, it's the end of the list.)

So no matter how big the grid is, the algorithm will always execute in the same time (or space) regardless of the size of the input data set. (`O(1)`)

_______________________________________________________________________________
