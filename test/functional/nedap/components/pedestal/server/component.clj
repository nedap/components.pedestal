(ns functional.nedap.components.pedestal.server.component
  (:require
   [clojure.test :refer :all]
   [com.stuartsierra.component :as component]
   [io.pedestal.http :as pedestal.http]
   [nedap.components.pedestal.router.kws :as router]
   [nedap.components.pedestal.server.component :as sut]
   [nedap.components.pedestal.service.component :as service.component]
   [nedap.components.pedestal.service.kws :as service]))

(deftest lifecycle
  (doseq [service-opts [{::service/defaults-kind :dev
                         :env                    :test}
                        {::service/defaults-kind :production
                         :env                    :production}]]
    (let [service (-> {::service/expand-routes?   false
                       ::router/component         {::router/routes #{}}
                       ::service/pedestal-options {::pedestal.http/port  8080
                                                   ::pedestal.http/join? false}}
                      (merge service-opts)
                      service.component/new
                      component/start)]
      (testing "The component can be started and stopped without errors"
        (are [input] (do
                       (-> input
                           (assoc ::service/component service)
                           (component/start)
                           (component/stop))
                       true)
          (sut/new)
          (sut/new {::sut/start-stop-predicate (constantly false)})))

      (testing "The `::sut/start-stop-predicate` option is honored"
        (let [proof (atom false)
              start-stop-predicate (fn [& _]
                                     (reset! proof true)
                                     false)]
          (-> (sut/new {::sut/start-stop-predicate start-stop-predicate})
              (assoc ::service/component service)
              (component/start)
              (component/stop))
          (is @proof))))))
