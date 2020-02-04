(ns functional.nedap.components.pedestal.interceptors.spec-coercer
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.test :refer :all]
   [io.pedestal.http :as http]
   [io.pedestal.test :refer [response-for]]
   [medley.core :refer [deep-merge]]
   [nedap.components.pedestal.interceptors.spec-coercer :as sut]
   [ring.util.response]))

(spec/def ::age int?)

(spec/def ::api (spec/keys :req-un [::age]))

(def control
  {:name  ::control
   :enter (fn [context]
            (update context :response deep-merge (ring.util.response/response "Hello")))})

(def service-fn
  (-> {::http/routes #{["/control" :get control :route-name ::control]
                       ["/sut" :get [(sut/param-spec-interceptor ::api :params), control] :route-name ::sut]}}
      http/create-servlet
      ::http/service-fn))

(deftest status-and-body
  (are [endpoint expected-status expected-body] (testing endpoint
                                                  (let [{:keys [status body]} (response-for service-fn :get endpoint)]
                                                    (is (= expected-status
                                                           status))
                                                    (is (= expected-body
                                                           body)))
                                                  true)

    "/control"         200 "Hello"
    "/sut"             422 "{:explanation \"#:nedap.utils.spec.api{:invalid? true} - failed: (contains? % :age) spec: :functional.nedap.components.pedestal.interceptors.spec-coercer/api\\n\"}"

    "/control?age="    200 "Hello"
    "/sut?age="        422 "{:explanation \"\\\"\\\" - failed: int? in: [:age] at: [:age] spec: :functional.nedap.components.pedestal.interceptors.spec-coercer/age\\n\"}"

    "/control?age=42"  200 "Hello"
    "/sut?age=42"      200 "Hello"

    "/control?age=aaa" 200 "Hello"
    "/sut?age=aaa"     422 "{:explanation \"\\\"aaa\\\" - failed: int? in: [:age] at: [:age] spec: :functional.nedap.components.pedestal.interceptors.spec-coercer/age\\n\"}"))
