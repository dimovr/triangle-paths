# ASSUMPTIONS

## Program structure

The program should be able to:
    - `load and parse different input data files`, so that it can be used outside of the scope of this project and tested arbitrarily.
    - `have predefined set of "strategies"`, so that they can be evaluated on different files.
       NOTE: `strategy` will be an implementation of the traversal algorithm.
        Initially Depth-first Search (`DFS`) will be considered. An placeholder `Optimized` will be defined and worked on after the initial skeleton is implemented. 
    - `perform different calculations based on an argument`, so that we can abstract away the traversal of the triangles from the computing function.
       NOTE: we will define `min` and `max` operations just as illustrative example

The program should be testable for any provided set of parameters.

Initially DFS search will be implemented as it will be conceptually simplest for manual runs until tests are written.
Also to showcase abstracting away traversal mechanisms from evaluation criteria. Should be replaced by more optimized graph algorithm.

