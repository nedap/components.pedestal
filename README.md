# nedap.components.pedestal

This library delivers:

* A [Pedestal](https://github.com/pedestal/pedestal) server as a component
  * Featuring refined integration with the Reloaded workflow
    * Otherwise Pedestal<->Reloaded integration can be hacky or incomplete.
  * Including logging dependencies, so that apps don't repeat the same dep declarations.
* Pedestal service configuration
* A [dependency injection mechanism](https://github.com/grzm/component.pedestal)
* A spec-coercion interceptor
  * Every app tends to need this; hence the bundling here. 

## Rationale

* Reduce per-project boilerplate.
* The server can be delicate to configure, so changes to it should be centralized, so that everyone benefits from future improvements.
* Provide a mechanism for injecting Component dependencies into the request context.
  * `grzm/component.pedestal` provides that, and this library provides something DRYer on top of it.

## Installation

```clojure
[com.nedap.staffing-solutions/components.pedestal "1.1.0-alpha7"]
```

## Documentation

The best way to see how the three bundled components play together is creating a web app via our [lein-template](https://github.com/nedap/lein-template).

As per usual, this library features specs, docstrings and various tests that can serve as examples.

## License

Copyright © Nedap
