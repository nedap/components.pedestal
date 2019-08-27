(defproject com.nedap.staffing-solutions/components.pedestal "1.1.0-alpha2"
  ;; Please don't bump the library version by hand - use ci.release-workflow instead.
  ;; Please keep the dependencies sorted a-z.
  :dependencies [[ch.qos.logback/logback-classic "1.2.3"
                  :exclusions [org.slf4j/slf4j-api]]
                 [com.grzm/component.pedestal "0.1.7"
                  :exclusions [ring/ring-codec]]
                 [com.nedap.staffing-solutions/utils.modular "2.0.0"]
                 [com.nedap.staffing-solutions/speced.def "1.0.0"
                  :exclusions [org.clojure/spec.alpha]]
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


  ;; A variety of common dependencies are bundled with `nedap/lein-template`.
  ;; They are divided into two categories:
  ;; * Dependencies that are possible or likely to be needed in all kind of production projects
  ;;   * The point is that when you realise you needed them, they are already in your classpath, avoiding interrupting your flow
  ;;   * After realising this, please move the dependency up to the top level.
  ;; * Genuinely dev-only dependencies allowing 'basic science'
  ;;   * e.g. criterium, deep-diff, clj-java-decompiler

  ;; NOTE: deps marked with #_"transitive" are there to satisfy the `:pedantic?` option.
  :profiles {:dev {:dependencies [[cider/cider-nrepl "0.16.0" #_"formatting-stack needs it"]
                                  [com.clojure-goes-fast/clj-java-decompiler "0.2.1"]
                                  [com.taoensso/timbre "4.10.0"]
                                  [criterium "0.4.4"]
                                  [formatting-stack "1.0.0-alpha3"
                                   :exclusions [rewrite-clj]]
                                  [lambdaisland/deep-diff "0.0-29"]
                                  [medley "1.1.0"]
                                  [org.clojure/core.async "0.4.490"]
                                  [org.clojure/math.combinatorics "0.1.1"]
                                  [org.clojure/test.check "0.10.0-alpha3"]
                                  [org.clojure/tools.namespace "0.3.0-alpha4"]
                                  [org.clojure/tools.reader "1.1.1" #_"transitive"]
                                  [rewrite-clj "0.6.1" #_"transitive"]]
                   :plugins      [[lein-cloverage "1.0.13"]]
                   :source-paths ["dev" "test"]
                   :repl-options {:init-ns dev}}

             :ci  {:pedantic?    :abort
                   :jvm-opts     ["-Dclojure.main.report=stderr"]
                   :global-vars  {*assert* true} ;; `ci.release-workflow` relies on runtime assertions
                   :dependencies [[com.nedap.staffing-solutions/ci.release-workflow "1.3.0-alpha3"]]}})
