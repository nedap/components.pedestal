(ns nedap.components.pedestal.server.component
  (:require
   [com.grzm.component.pedestal :as pedestal-component]
   [com.stuartsierra.component :as component]
   [io.pedestal.http :as server]
   [nedap.components.pedestal.service.kws :as service]
   [nedap.utils.modular.api :refer [implement]]
   [nedap.utils.speced :as speced]))

(speced/def-with-doc ::start-stop-predicate
  "A fn with the following signature: `(fn ^boolean? [^::service/component service])`.

Meaning: should `service` be started on `#'com.stuartsierra.component/start`? (Same for `stop`)

Normally true, except on `:test` env."
  ifn?)

(speced/defn test?
  [{:keys [^some? env]}]
  (#{:test} env))

(defn should-start-or-stop? [service]
  (not (test? service)))

(speced/defn start [{service                                      ::service/component
                     server                                       ::server
                     ^::start-stop-predicate start-stop-predicate ::start-stop-predicate
                     :as                                          this}]
  (if server
    this
    (let [will-start? (start-stop-predicate service)
          server (cond-> service
                   true        (pedestal-component/add-component-interceptor this)
                   true        server/create-server
                   will-start? server/start)]
      (assoc this ::server server))))

(speced/defn stop [{service                                      ::service/component
                    server                                       ::server
                    ^::start-stop-predicate start-stop-predicate ::start-stop-predicate
                    :as                                          this}]
  (when (and server
             (start-stop-predicate service))
    (server/stop server))
  (assoc this ::server nil))

(defn new [& {::keys [start-stop-predicate]
              :or    {start-stop-predicate should-start-or-stop?}
              :as    this}]
  (implement this
    component/start start
    component/stop  stop))
