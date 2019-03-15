(ns nedap.components.pedestal.service.component
  (:require
   [com.stuartsierra.component :as component]
   [io.pedestal.http :as server]
   [io.pedestal.http.body-params :as body-params]
   [io.pedestal.http.route :as route]
   [medley.core :refer [deep-merge]]
   [nedap.components.pedestal.router.kws :as router]
   [nedap.components.pedestal.service.kws :as service]
   [nedap.utils.modular.api :refer [implement]]
   [nedap.utils.spec.api :refer [check!]]))

(def prod-map
  {::server/resource-path     "/public"
   ::server/type              :jetty
   ::server/port              8080
   ::server/container-options {:h2c? true
                               :h2?  false
                               :ssl? false}})

(def dev-map
  {::server/join?           false
   ::server/allowed-origins {:creds true :allowed-origins (constantly true)}
   ::server/secure-headers  {:content-security-policy-settings {:object-src "'none'"}}})

(defn start [{{::router/keys [routes]} ::router/component
              ::service/keys           [defaults-kind pedestal-options expand-routes?]
              :as                      this}]
  {:pre [(check! ::service/initialized-component this)]}
  (let [dev? (= :dev defaults-kind)]
    (cond-> prod-map
      (not expand-routes?) (assoc ::server/routes routes)
      expand-routes?       (assoc ::server/routes #(route/expand-routes routes))
      dev?                 (merge dev-map)
      true                 (deep-merge pedestal-options)
      true                 (server/default-interceptors)
      dev?                 (server/dev-interceptors))))

(defn new [opts]
  {:pre [(check! ::service/uninitialized-component opts)]}
  (implement opts
    component/start start
    component/stop  identity))
