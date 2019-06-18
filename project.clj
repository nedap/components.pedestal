(defproject com.nedap.staffing-solutions/components.pedestal "0.4.0"
  ;; Please keep the dependencies sorted a-z.
  :dependencies [[ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [com.grzm/component.pedestal "0.1.7"]
                 [com.nedap.staffing-solutions/utils.modular "0.3.0"]
                 [com.nedap.staffing-solutions/utils.spec "0.8.2"]
                 [com.stuartsierra/component "0.4.0"]
                 [io.pedestal/pedestal.jetty "0.5.5"]
                 [io.pedestal/pedestal.service "0.5.5"]
                 [medley "1.1.0"]
                 [org.clojure/clojure "1.10.1"]
                 [org.slf4j/jcl-over-slf4j "1.7.26"]
                 [org.slf4j/jul-to-slf4j "1.7.26"]
                 [org.slf4j/log4j-over-slf4j "1.7.26"]]

  :description "Pedestal server as a Clojure Component"

  :url "https://github.com/nedap/components.pedestal"

  :min-lein-version "2.0.0"

  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :signing {:gpg-key "releases-staffingsolutions@nedap.com"}

  :repositories {"releases" {:url      "https://nedap.jfrog.io/nedap/staffing-solutions/"
                             :username :env/artifactory_user
                             :password :env/artifactory_pass}}

  :deploy-repositories [["releases" {:url "https://nedap.jfrog.io/nedap/staffing-solutions/"}]]

  :repository-auth {#"https://nedap.jfrog\.io/nedap/staffing-solutions/"
                    {:username :env/artifactory_user
                     :password :env/artifactory_pass}}

  :target-path "target/%s"

  :monkeypatch-clojure-test false

  :repl-options {:init-ns dev}


  :profiles {:dev {:dependencies [[cider/cider-nrepl "0.16.0" #_"formatting-stack needs it"]
                                  [com.clojure-goes-fast/clj-java-decompiler "0.2.1"]
                                  [com.taoensso/timbre "4.10.0"]
                                  [criterium "0.4.4"]
                                  [formatting-stack "0.18.4"]
                                  [lambdaisland/deep-diff "0.0-29"]
                                  [org.clojure/test.check "0.10.0-alpha3"]
                                  [org.clojure/tools.namespace "0.3.0-alpha4"]
                                  [org.clojure/tools.reader "1.1.1" #_"formatting-stack needs it"]]
                   :plugins      [[lein-cloverage "1.0.13"]]
                   :source-paths ["dev" "test"]}})
