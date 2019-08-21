(ns nedap.components.pedestal.interceptors.component-injector
  (:require
   [clojure.spec.alpha :as spec]
   [com.grzm.component.pedestal :refer [use-component]]
   [nedap.speced.def :as speced]))

(speced/defn component-injector
  "An interceptor that exposes components in the request."
  [^{::speced/spec (spec/coll-of qualified-keyword?)} components]
  {:enter (fn [{:keys [request] :as context}]
            (reduce (fn [v component]
                      (assoc-in v [:request component] (use-component request component)))
                    context
                    components))
   :name  ::context-injector})
