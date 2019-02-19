(ns nedap.components.pedestal.router.kws
  (:require
   [nedap.utils.speced :as speced]))

(speced/def-with-doc ::routes "The routes configured for this application" some?)

(speced/def-with-doc ::component "This component" map?)
