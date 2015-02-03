Game
====
**NOTE:** Mac OS X currently unsupported due to a limitation of the JSFML library.

Team
----
Together we can build a great game. Here are some pointers to follow:
+ Never push to the master branch. Always create a new branch for new features. When you feel your work is done, create a pull request for the rest of the team to review.
+ Comment your code. Create Javadocs for all public classes and methods. Use block comments to explain large comments and use single line comments everywhere else throughout your code. If the team cannot understand your code, it is not going to be merged.
+ Make all classes, methods and fields private when possible. Limiting the visibility of your code is a best practice. Go from private to protected to public, only when neccessary.
+ Make all classes and fields final when possible. Do not worry about making anything else final. This is another best practice.
+ Write unit tests for every single public method. A few minutes of writing tests can save hours of debugging.
+ Commit often. Shorter commits allow for more flexibility and allow others to better follow the development process.
+ Be thourough, neat and concise. Prototype your feature, test it and then finally spend a good amount of time refactoring it before creating a pull request. Pretend that the code you write is like paint on a canvas. Create something you are proud of.
+ Do not be offended. If a fellow team member does not find your code adequete or finds a better solution to the problem, do not take offense. We must all help one another and we all have something to learn.

Architecture
------------
This game is created with the [IntelliJ IDEA](https://www.jetbrains.com/idea/). Several other technologies are also used including:
+ [Gradle](https://gradle.org/) is used as the build automation system.
+ [JSFML](http://jsfml.org/) is used for handling audio, graphics, input and window management.
+ [JUnit](http://junit.org/) is used for unit testing.

TODO: finish README

&copy; 2014-2015 TWHSCS
