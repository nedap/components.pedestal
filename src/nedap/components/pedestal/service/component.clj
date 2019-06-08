(ns nedap.components.pedestal.service.component
  (:require
   [com.stuartsierra.component :as component]
   [io.pedestal.http :as pedestal.http]
   [io.pedestal.http.route :as route]
   [medley.core :refer [deep-merge]]
   [nedap.components.pedestal.router.kws :as router]
   [nedap.components.pedestal.service.kws :as service]
   [nedap.utils.modular.api :refer [implement]]
   [nedap.utils.speced :as speced]))

(def prod-map
  {::pedestal.http/resource-path     "/public"
   ::pedestal.http/type              :jetty
   ::pedestal.http/port              8080
   ::pedestal.http/container-options {:h2c? true
                                      :h2?  false
                                      :ssl? false}})

(def dev-map
  {::pedestal.http/join?           false
   ::pedestal.http/allowed-origins {:creds true :allowed-origins (constantly true)}
   ::pedestal.http/secure-headers  {:content-security-policy-settings {:object-src "'none'"}}})

(speced/defn ^::service/component start [{{::router/keys [routes]} ::router/component
                                          ::service/keys           [defaults-kind pedestal-options expand-routes?]
                                          :as                      ^::service/initialized-component this}]
  (let [dev? (= :dev defaults-kind)
        config (cond-> prod-map
                 (not expand-routes?) (assoc ::pedestal.http/routes routes)
                 expand-routes?       (assoc ::pedestal.http/routes #(route/expand-routes routes))
                 dev?                 (merge dev-map)
                 pedestal-options     (deep-merge pedestal-options)
                 true                 (pedestal.http/default-interceptors)
                 dev?                 (pedestal.http/dev-interceptors))]
    (merge this config)))

(defn new [^::service/uninitialized-component opts]
  (implement opts
    component/start start
    component/stop  identity))
