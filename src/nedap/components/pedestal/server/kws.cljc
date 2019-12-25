(ns nedap.components.pedestal.server.kws
  (:require
   [clojure.spec.alpha :as spec]
   [nedap.components.pedestal.router.kws :as router]
   [nedap.components.pedestal.service.kws :as service]
   [nedap.speced.def :as speced]))

(def dependencies [::router/component ;; this dependency is important: it guarantees that `(reset)` will reload routes
                   ::service/component])

(speced/def-with-doc ::component "This component" (spec/keys :req [::service/component]))
