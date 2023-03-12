[//]: # (TODO: add student id and direct link to github readme page)

# ECE654 Assignment 2

I implemented a static Parity analysis tool for the Java programming language on top of the [Dataflow Project of the Checker Framework](https://checkerframework.org/manual/checker-framework-dataflow-manual.pdf). There are 4 essential components to define a dataflow analysis with monotone framework:
* A lattice ($L$, $\sqsubseteq$)
* An abstraction function $\alpha$
* A transfer function $f$
* An initial dataflow analysis assumptions, $\sigma_0$


## Lattice
```mermaid
graph BT;
    Bottom-->Even;
    Bottom-->Odd;
    Even-->Top;
    Odd-->Top;
```

## Abstract Functions

### Integer Addition and Subtraction
|        | $\bot$ | Even   | Odd    | $\top$ |
|--------|--------|--------|--------|--------|
| $\bot$ | $\bot$ | $\bot$ | $\bot$ | $\bot$ |
| Even   | $\bot$ ||||
| Odd    | $\bot$ ||||
| $\top$ | $\bot$ |||

# References
[Claire Le Goues Jonathan Aldrich and Rohan Padhye. Program analysis. 2022.](https://cmu-program-analysis.github.io/2023/resources/program-analysis.pdf)

[Anders Møller and Michael I Schwartzbach. Static program analysis. 2022.](https://cs.au.dk/~amoeller/spa/spa.pdf)