Plane Seats Arranger
====================

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



Alternative solutions
---------------------
An alternative solution, which would provide the best combination every time, would be based on dynamic programming. We could develop a scoring algorithm, find all the possible combinations inside the plane that are acceptable and score them using the developed method, keeping the best as the final solution.

This solution would require more computing power, but could be easily done in a manner that suits parallelization.


What is missing
---------------
  - Tests: there are tests missing for many of the methods used in the application.
  - The method that splits oversized groups should take into account window preference.  
  - Currently, groups are processed individually. To improve satisfaction, we could take into account the rest of the groups, make the best combinations and then place them.
  - There are some consta
  - Refactoring and optimization of the classes in package 'algorithm'
  