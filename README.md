# Triangle path
 This is a command line tool for evaluating a path through a `Triangle` based on a function. 
 `Triangle` is a Rooted binary directed acyclic graph of integers.
 

## Run
In order to execute it run the following command:
```dtd
triangle-path --i {inputFile} --f {function}
```
Example:
```dtd
triangle-path --i src/main/resources/data_small.txt --f Min
```

Arguments: 
    - `i` - input text file containing the triangle
    - `f` - evaluation function:
        - `Min` - finding the path with the minimal sum of the numbers
        - `Max` - finding the path with the minimal sum of the numbers
