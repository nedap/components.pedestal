(ns nedap.components.pedestal.router.kws
  (:require
   [nedap.speced.def :as speced]
   [clojure.spec.alpha :as spec]))

(speced/def-with-doc ::routes
  "The routes configured for this application. A 'routes definition object' in any format Pedestal understands."
  some?)

(spec/def ::component (spec/keys :opt [::routes]))
