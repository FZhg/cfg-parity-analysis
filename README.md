# ECE654 Assignment 2

I implemented a static Parity analysis tool for the Java programming language on top of the [Dataflow Module of the Checker Framework](https://checkerframework.org/manual/checker-framework-dataflow-manual.pdf). There are 4 essential components to define a dataflow analysis with monotone framework:
* a lattice ($L$)
* 