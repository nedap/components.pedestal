(ns nedap.components.pedestal.service.component
  (:require
   [com.stuartsierra.component :as component]
   [io.pedestal.http :as pedestal.http]
   [io.pedestal.http.route :as route]
   [medley.core :refer [deep-merge]]
   [nedap.components.pedestal.router.kws :as router]
   [nedap.components.pedestal.service.kws :as service]
   [nedap.speced.def :as speced]
   [nedap.utils.modular.api :refer [implement]]))

(def prod-map
  {::pedestal.http/join?             false
   ::pedestal.http/resource-path     "/public"
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
        ;; routes may be passed directly (via `::router/component`) or indirectly (via `pedestal-options`). Handle that:
        routes (when routes
                 (if expand-routes?
                   #(route/expand-routes routes)
                   routes))
        config (cond-> prod-map
                 routes           (assoc ::pedestal.http/routes routes)
                 dev?             (merge dev-map)
                 pedestal-options (deep-merge pedestal-options)
                 true             (pedestal.http/default-interceptors)
                 dev?             (pedestal.http/dev-interceptors))]
    (merge this config)))

(defn stop [this]
  {})

(speced/defn new [^::service/uninitialized-component opts]
  (implement opts
    component/start start
    component/stop  stop))
