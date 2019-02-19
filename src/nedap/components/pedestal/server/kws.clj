(ns nedap.components.pedestal.server.kws
  (:require
   [clojure.spec.alpha :as spec]
   [nedap.components.pedestal.service.kws :as service]
   [nedap.utils.speced :as speced]))

(def dependencies [::service/component])

(speced/def-with-doc ::component "This component" (eval `(spec/keys :req ~dependencies)))
