# components.pedestal

This library delivers:

* A [Pedestal](https://github.com/pedestal/pedestal) server as a component
  * Featuring refined integration with the Reloaded workflow
    * Otherwise Pedestal<->Reloaded integration can be hacky or incomplete.
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
[com.nedap.staffing-solutions/components.pedestal "2.0.1-alpha2"]
```

## Documentation

The best way to see how the three bundled components play together is creating a web app via our [lein-template](https://github.com/nedap/lein-template).

As per usual, this library features specs, docstrings and various tests that can serve as examples.

## License

Copyright © Nedap

This program and the accompanying materials are made available under the terms of the [Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-2.0).
