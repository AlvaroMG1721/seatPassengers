Seat Passengers
===============

How to run
-----------

The program can be executed with the jar file, using the scala command:

```
scala seatPassengers.jar pathToFile
```

Using the java command:

```
java -jar seatPassengers.jar pathToFile
```

Or using sbt:

```
sbt "run pathToFile"
```

How to build
------------

The project was built using the Scala Build Tool (SBT):

* [SBT](https://www.scala-sbt.org/)

To install the dependencies and run the tests, place yourself in the project folder and execute:

```
sbt test
```

The program was written using the Scala programming language (v. 2.11.8):

* [Scala](https://www.scala-lang.org/)


Assumptions
-----------

The following assumptions have been made when designing the solution:

1)	The input file will contain, at least, one line with the plane dimensions.
2)	Passengers in a group are considered happy if they are seated together in the same row and next to a window, in case they have window preference.
3)	If the plane is oversubscribed, the priority is to fit as many groups as possible inside the plane, without splitting the groups, regardless of window preference.
4)	The number of passengers in a group can exceed the size of the rows of the plane. In that case, the group can be splitted into subgroups, which would be considered independent groups (a passenger will be considered happy if it is seated together with members of its subgroup).
5)	There can be more than one passenger with window preference in a group.


Solution description
--------------------
We implemented an algorithm that takes the following steps:

1) Parse the input file, line by line, obtaining the plane dimensions and a series of Groups, that contain the Passengers.
2) Find the best combination of Groups to maximize the number of Passengers that will board the plane in case it is oversubscribed, without splitting the groups. These are the groups that move forward, the number of passengers left outside is printed on standard output.
3) Split the groups that have a size greater than the length of the rows in the plane into new groups. These new groups will be treated as if they were unrelated.
4) Find the best seats for each group, one by one, ordered with descending size. The passengers in each group will be arranged so that the passenger with window preference is at the right side or at the left side and the group will be inserted in a seats matrix from the left or from the right, trying to optimize window preference satisfaction. The seats matrix is of type Int, and has the dimensions of the plane. Groups are inserted replacing zeroes (the value associated with an empty seat) with the ids of the passengers they contain. In the process, unsatisfied clients are counted.
5) Prints the results with the format requested.

The complexity of this algorithm is O(N*M), where N is the number of input passengers and M the number of rows in the plane. 


Alternative solutions
---------------------
An alternative solution, which would provide the best combination every time, would be based on dynamic programming. We could develop a scoring algorithm, find all the possible combinations inside the plane that are acceptable and score them using the developed method, keeping the best as the final solution.

This solution would require more computing power, but could be easily done in a manner that suits parallelization.


What is missing
---------------
  - Tests: there are tests missing for many of the methods used in the application and the 'Group' class lacks and appropriate compare method.
  - The method that splits oversized groups should take into account window preference.  
  - Currently, groups are processed individually. To improve satisfaction, we could take into account the rest of the groups, make the best combinations and then place them.
  - There are some constants hardcoded.
  - Refactoring and optimization of the methods in package 'algorithm'
  