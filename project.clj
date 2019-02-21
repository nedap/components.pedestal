(defproject com.nedap.staffing-solutions/components.pedestal "0.1.1"
  :description "Pedestal server as a Clojure Component"

  :url "https://github.com/nedap/components.pedestal"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :repositories {"nedap-snapshots" {:url      "https://nedap.jfrog.io/nedap/staffing-solutions/"
                                    :username :env/artifactory_user
                                    :password :env/artifactory_pass}
                 "releases"        {:url      "https://nedap.jfrog.io/nedap/staffing-solutions/"
                                    :username :env/artifactory_user
                                    :password :env/artifactory_pass}}

  :deploy-repositories [["releases" {:url "https://nedap.jfrog.io/nedap/staffing-solutions/"
                                     :sign-releases false}]]

  :repository-auth {#"https://nedap.jfrog\.io/nedap/staffing-solutions/"
                    {:username :env/artifactory_user
                     :password :env/artifactory_pass}}

  :dependencies [[ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [com.grzm/component.pedestal "0.1.7"]
                 [com.nedap.staffing-solutions/utils.modular "0.1.1"]
                 [com.nedap.staffing-solutions/utils.spec "0.1.1"]
                 [com.stuartsierra/component "0.4.0"]
                 [io.pedestal/pedestal.jetty "0.5.5"]
                 [io.pedestal/pedestal.service "0.5.5"]
                 [org.clojure/clojure "1.10.0"]
                 [org.slf4j/jcl-over-slf4j "1.7.25"]
                 [org.slf4j/jul-to-slf4j "1.7.25"]
                 [org.slf4j/log4j-over-slf4j "1.7.25"]])
