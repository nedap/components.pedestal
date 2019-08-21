(ns nedap.components.pedestal.interceptors.spec-coercer
  (:require
   [clojure.spec.alpha :as spec]
   [io.pedestal.interceptor.chain :as interceptor.chain]
   [nedap.speced.def :as speced]
   [nedap.utils.spec.api :as utils.spec]))

(spec/def ::params-key #{:params :route-params :query-params :form-params :multipart-params})

(speced/defn param-spec-interceptor
  "Coerces params according to a spec. If invalid, aborts the interceptor-chain with 422, explaining the issue."
  [^some? spec, ^::params-key params-key]
  {:name  ::param-spec-interceptor
   :enter (fn [context]
            (let [result (utils.spec/coerce-map-indicating-invalidity spec (get-in context [:request params-key] {}))]
              (if (contains? result ::utils.spec/invalid?)
                (-> context
                    (assoc :response {:status 422
                                      :body   {:explanation (spec/explain-str spec result)}})
                    (assoc-in [:response :headers "Content-Type"] "text/html;charset=UTF-8")
                    interceptor.chain/terminate)
                (assoc-in context [:request params-key] result))))})
