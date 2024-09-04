# Triangle path
 This is a command line tool for evaluating a path through a `Triangle` based on a function. 
 `Triangle` is a Rooted binary directed acyclic graph of integers.
 

## Run
In order to execute it run the following command:
```dtd
triangle-path --i {inputFile} --s {strategy} --f {function}
```
Example:
```dtd
triangle-path --i src/main/resources/data_small.txt --s DFS --f Min
```

Arguments: 
    - `i` - input text file containing the triangle
    - `s` - traversal strategy:
        - `DFS` - depth-first search
            NOTE: very inefficient for larger data structures due to 2^N complexity
        - `Optimized` - yet not implemented
    - `f` - evaluation function:
        - `Min` - finding the path with the minimal sum of the numbers
        - `Max` - finding the path with the minimal sum of the numbers


## References
https://www.researchgate.net/publication/324850741_Gait-based_human_age_estimation_using_age_group-dependent_manifold_learning_and_regression

