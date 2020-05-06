(ns nedap.components.pedestal.service.kws
  (:require
   [clojure.spec.alpha :as spec]
   [io.pedestal.http :as pedestal.http]
   [nedap.components.pedestal.router.kws :as router]
   [nedap.speced.def :as speced]))

(def dependencies [::router/component])

(speced/def-with-doc ::defaults-kind
  "The kind of defaults to be used. Note that this a more constrained notion than 'environment' (which can include staging)."
  #{:dev :production})

(speced/def-with-doc ::pedestal.http/join?
  "A true value blocks the calling thread until server ends.

Warning: Blocking the server thread (`true` value) can interfere with shutdown
hooks that would need access to the started Component System."
  boolean?)

(speced/def-with-doc ::pedestal-options
  "To be deep-merged with the component's defaults."
  (spec/keys :req []))

(speced/def-with-doc ::pedestal-production-options
  "Somewhat more strict spec for pedestal-options when running in production."
  (spec/merge
    ::pedestal-options
    (spec/keys :req [::pedestal.http/join?])))

(speced/def-with-doc ::expand-routes?
  "Should the routes be expanded with `io.pedestal.http.route/expand-routes`?

That may or may not be necessary, depending on the chosen routes syntax."
  boolean?)

(speced/def-with-doc ::uninitialized-component
  "This component, before Component injects dependencies to it"
  (spec/keys :req [::defaults-kind ::expand-routes?]))

(speced/def-with-doc ::initialized-component
  "This component, after Component injects dependencies to it"
  (spec/merge ::uninitialized-component
              (spec/keys :req [::router/component ::pedestal-options])))

(speced/def-with-doc ::component "The configuration data for a Pedestal server." ::initialized-component)
