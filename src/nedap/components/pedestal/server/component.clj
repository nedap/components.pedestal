(ns nedap.components.pedestal.server.component
  (:require
   [com.grzm.component.pedestal :as pedestal-component]
   [com.stuartsierra.component :as component]
   [io.pedestal.http :as server]
   [nedap.components.pedestal.service.kws :as service]
   [nedap.utils.modular.api :refer [implement]]))

(defn test?
  [service-map]
  (-> service-map :env #{:test}))

(defn start [{service ::service/component
              ::keys [server]
              :as this}]
  (if server
    this
    (let [server (cond-> service
                   true                  (pedestal-component/add-component-interceptor this)
                   true                  server/create-server
                   (not (test? service)) server/start)]
      (assoc this ::server server))))

(defn stop [{service ::service/component
             ::keys [server]
             :as this}]
  (when (and server (not (test? service)))
    (server/stop server))
  (assoc this ::server nil))

(defn new []
  (implement {}
             component/start start
             component/stop stop))
