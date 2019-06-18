# nedap.components.pedestal

A Component including [Pedestal](https://github.com/pedestal/pedestal) server, configuration
and a [dependency injection mechanism](https://github.com/grzm/component.pedestal).

## Rationale

* Reduce per-project boilerplate.
* The server can be delicate to configure, so changes to it should be centralized so everyone benefits from future improvements.
* Provide a mechanism for injecting Component dependencies into the request context.
  * `grzm/component.pedestal` provides that, and we build something DRYer on top of it.

## Installation

```clojure
[com.nedap.staffing-solutions/components.pedestal "0.4.0"]
```

## License

Copyright © Nedap

This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
