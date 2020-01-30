(ns unit.nedap.components.pedestal.interceptors.spec-coercer
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.test :refer :all]
   [nedap.components.pedestal.interceptors.spec-coercer :as sut]))

(spec/def ::age int?)

(spec/def ::api (spec/keys :req-un [::age]))

(deftest param-coercion
  (are [input expected] (let [interceptor (-> (sut/param-spec-interceptor ::api :params)
                                              :enter)]
                          (testing input
                            (is (= expected
                                   (-> (interceptor {:request {:params input}})
                                       :request
                                       :params)))
                            true))
    {}          {}
    {:age "a"}  {:age "a"}
    {:age "42"} {:age 42}
    {:age 42}   {:age 42}))
