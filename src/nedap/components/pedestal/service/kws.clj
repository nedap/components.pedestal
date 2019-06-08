(ns nedap.components.pedestal.service.kws
  (:require
   [clojure.spec.alpha :as spec]
   [nedap.components.pedestal.router.kws :as router]
   [nedap.utils.speced :as speced]))

(def dependencies [::router/component])

(speced/def-with-doc ::defaults-kind
  "The kind of defaults to be used. Note that this a more constrained notion than 'environment' (which can include staging)."
  #{:dev :production})

(speced/def-with-doc ::pedestal-options
  "To be deep-merged with the component's defaults."
  map?)

(speced/def-with-doc ::expand-routes?
  "Should the routes be expanded with `io.pedestal.http.route/expand-routes`?

That may or may not be necessary, depending on the chosen routes syntax"
  boolean?)

(speced/def-with-doc ::uninitialized-component
  "This component, before Component injects dependencies to it"
  (spec/keys :req [::defaults-kind ::expand-routes?]))

(speced/def-with-doc ::initialized-component
  "This component, after Component injects dependencies to it"
  (eval `(spec/merge ::uninitialized-component
                     (spec/keys :req ~(conj dependencies ::pedestal-options)))))

(speced/def-with-doc ::component "This component" ::initialized-component)
