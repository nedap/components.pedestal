(ns unit.nedap.components.pedestal.service.component
  (:require
   [clojure.test :refer :all]
   [io.pedestal.http :as pedestal.http]
   [nedap.components.pedestal.router.kws :as router]
   [nedap.components.pedestal.service.component :as sut]
   [nedap.components.pedestal.service.kws :as service]))

(deftest start
  (testing "validity"
    (are [desc input expected] (testing desc
                                 (try
                                   (with-out-str
                                     (-> input sut/new sut/start))
                                   expected
                                   (catch Exception e
                                     (false? expected))))

      "Non filled"
      {}                                                        false

      "No pedestal options"
      {::service/defaults-kind  :dev
       ::service/expand-routes? false
       ::router/component       {::router/routes #{}}},         false

      "With pedestal options"
      {::service/defaults-kind    :dev
       ::service/expand-routes?   false
       ::router/component         {::router/routes #{}}
       ::service/pedestal-options {::pedestal.http/port 8080}}, true))

  (testing "pedestal options are observed"
    (testing "flat options are assoc'ed"
      (are [desc input expected] (testing desc
                                   (= expected
                                      (-> input sut/new sut/start ::pedestal.http/port)))

        "Default"
        {::service/defaults-kind    :dev
         ::service/expand-routes?   false
         ::router/component         {::router/routes #{}}
         ::service/pedestal-options {}},                          8080

        "With pedestal options"
        {::service/defaults-kind    :dev
         ::service/expand-routes?   false
         ::router/component         {::router/routes #{}}
         ::service/pedestal-options {::pedestal.http/port 8081}}, 8081))

    (testing "Nested options are deep merged"
      (are [desc input expected] (testing desc
                                   (= expected
                                      (-> input sut/new sut/start ::pedestal.http/container-options)))

        "Defaults"
        {::service/defaults-kind    :dev
         ::service/expand-routes?   false
         ::router/component         {::router/routes #{}}
         ::service/pedestal-options {}},                                               {:h2c? true
                                                                                        :h2?  false
                                                                                        :ssl? false}

        "With pedestal options"
        {::service/defaults-kind    :dev
         ::service/expand-routes?   false
         ::router/component         {::router/routes #{}}
         ::service/pedestal-options {::pedestal.http/container-options {:h2c? :OMG
                                                                        :ssl? :BAR}}}, {:h2c? :OMG
                                                                                        :h2?  false
                                                                                        :ssl? :BAR}))))
