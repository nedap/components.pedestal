(ns functional.nedap.components.pedestal.interceptors.spec-coercer
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.test :refer :all]
   [io.pedestal.http :as http]
   [io.pedestal.http.route.definition :refer [defroutes]]
   [io.pedestal.interceptor :as interceptor]
   [io.pedestal.interceptor.chain :as interceptor.chain]
   [nedap.components.pedestal.interceptors.spec-coercer :as sut]
   [ring.util.response]))

(spec/def ::age int?)

(spec/def ::api (spec/keys :req-un [::age]))

(defn control [context]
  (assoc context :response (ring.util.response/response "Hello")))

(def sut (sut/param-spec-interceptor ::api :params))

(def default-interceptor-chain
  (->> (io.pedestal.http/default-interceptors
        {:env          :test
         ::http/routes #{["/" :get control :route-name ::control]}})
       (:io.pedestal.http/interceptors)))

(def interceptor-chain-without-sut (->> control
                                        interceptor/interceptor
                                        (conj default-interceptor-chain)))

(def interceptor-chain-with-sut (->> sut
                                     interceptor/interceptor
                                     (conj interceptor-chain-without-sut)))

(def new-request {::interceptor.chain/queue []
                  :request                  {:scheme         :http
                                             :request-method :get
                                             :protocol       "HTTP/1.1"
                                             :headers        {}
                                             :server-port    80
                                             :remote-addr    "0.0.0.0"
                                             :server-name    "localhost"
                                             :uri            "/"}})

(deftest works
  (are [chain input-params expected-status expected-coerced-params] (testing input-params
                                                                      (let [req (-> new-request
                                                                                    (assoc-in [:request :params] input-params))

                                                                            {{:keys [params]}
                                                                             :request

                                                                             {:keys [status]}
                                                                             :response}
                                                                            (interceptor.chain/execute req chain)]

                                                                        (is (= expected-status
                                                                               status))
                                                                        (is (= expected-coerced-params
                                                                               params)))
                                                                      true)

    interceptor-chain-without-sut {}           404 {}
    interceptor-chain-with-sut    {}           422 {}
    interceptor-chain-without-sut {:age ""}    404 {:age ""}
    interceptor-chain-with-sut    {:age ""}    422 {:age ""}
    interceptor-chain-without-sut {:age "42"}  404 {:age "42"}
    interceptor-chain-with-sut    {:age "42"}  404 {:age 42}
    interceptor-chain-without-sut {:age "aaa"} 404 {:age "aaa"}
    interceptor-chain-with-sut    {:age "aaa"} 422 {:age "aaa"}))
