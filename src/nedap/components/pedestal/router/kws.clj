(ns nedap.components.pedestal.router.kws
  (:require
   [nedap.speced.def :as speced]))

(speced/def-with-doc ::routes "The routes configured for this application" some?)

(speced/def-with-doc ::component "This component" map?)
