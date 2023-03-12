# ECE654 Assignment 2

I implemented a static Parity analysis tool for the Java programming language on top of the [Dataflow Module of the Checker Framework](https://checkerframework.org/manual/checker-framework-dataflow-manual.pdf). There are 4 essential components to define a dataflow analysis with monotone framework:
* A lattice ($L$, $\sqsubseteq$)
* An abstraction function $\alpha$
* A transfer function $f$
* An initial dataflow analysis assumptions, $\sigma_0$


## Lattice
```mermaid
  graph TD;
      top<--even;
      top<--odd;
      even<--bottom;
      odd<--bottom;
```

# References
[Claire Le Goues Jonathan Aldrich and Rohan Padhye. Program analysis. 2022.](https://cmu-program-analysis.github.io/2023/resources/program-analysis.pdf)

[Anders Møller and Michael I Schwartzbach. Static program analysis. 2022.](https://cs.au.dk/~amoeller/spa/spa.pdf)