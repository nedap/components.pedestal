;; Please don't bump the library version by hand - use ci.release-workflow instead.
(defproject com.nedap.staffing-solutions/components.pedestal "1.1.0"
  ;; Please keep the dependencies sorted a-z.
  :dependencies [[ch.qos.logback/logback-classic "1.2.3"
                  :exclusions [org.slf4j/slf4j-api]]
                 [com.grzm/component.pedestal "0.1.7"
                  :exclusions [ring/ring-codec]]
                 [com.nedap.staffing-solutions/utils.modular "2.1.0"]
                 [com.nedap.staffing-solutions/speced.def "2.0.0"
                  :exclusions [org.clojure/spec.alpha]]
                 [com.stuartsierra/component "0.4.0"]
                 [io.pedestal/pedestal.jetty "0.5.7"]
                 [io.pedestal/pedestal.service "0.5.7"]
                 [medley "1.2.0"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.analyzer.jvm "0.7.3" #_"Transitive"]
                 [org.slf4j/jcl-over-slf4j "1.7.26"]
                 [org.slf4j/jul-to-slf4j "1.7.26"]
                 [org.slf4j/log4j-over-slf4j "1.7.26"]]

  :exclusions [org.clojure/clojurescript]

  :description "A Pedestal server offered as a Clojure Component."

  :url "https://github.com/nedap/components.pedestal"

  :min-lein-version "2.0.0"

  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :signing {:gpg-key "releases-staffingsolutions@nedap.com"}

  :repositories {"releases" {:url      "https://nedap.jfrog.io/nedap/staffing-solutions/"
                             :username :env/artifactory_user
                             :password :env/artifactory_pass}}

  :repository-auth {#"https://nedap.jfrog\.io/nedap/staffing-solutions/"
                    {:username :env/artifactory_user
                     :password :env/artifactory_pass}}

  :deploy-repositories {"clojars" {:url      "https://clojars.org/repo"
                                   :username :env/clojars_user
                                   :password :env/clojars_pass}}
  :target-path "target/%s"

  :test-paths ["src" "test"]

  :monkeypatch-clojure-test false

  :plugins [[lein-pprint "1.1.2"]]

  ;; A variety of common dependencies are bundled with `nedap/lein-template`.
  ;; They are divided into two categories:
  ;; * Dependencies that are possible or likely to be needed in all kind of production projects
  ;;   * The point is that when you realise you needed them, they are already in your classpath, avoiding interrupting your flow
  ;;   * After realising this, please move the dependency up to the top level.
  ;; * Genuinely dev-only dependencies allowing 'basic science'
  ;;   * e.g. criterium, deep-diff, clj-java-decompiler

  ;; NOTE: deps marked with #_"transitive" are there to satisfy the `:pedantic?` option.
  :profiles {:dev      {:dependencies [[cider/cider-nrepl "0.16.0" #_"formatting-stack needs it"]
                                       [com.clojure-goes-fast/clj-java-decompiler "0.2.1"]
                                       [com.nedap.staffing-solutions/utils.spec.predicates "1.1.0"]
                                       [com.taoensso/timbre "4.10.0"]
                                       [criterium "0.4.5"]
                                       [formatting-stack "2.0.1-alpha2"]
                                       [lambdaisland/deep-diff "0.0-29"]
                                       [medley "1.2.0"]
                                       [org.clojure/core.async "0.7.559"]
                                       [org.clojure/math.combinatorics "0.1.1"]
                                       [org.clojure/test.check "0.10.0-alpha3"]
                                       [org.clojure/tools.namespace "0.3.1"]
                                       [refactor-nrepl "2.4.0" #_"formatting-stack needs it"]]
                        :jvm-opts     ["-Dclojure.compiler.disable-locals-clearing=true"]
                        :plugins      [[lein-cloverage "1.1.1"]]
                        :source-paths ["dev"]
                        :repl-options {:init-ns dev}}

             :check    {:global-vars {*unchecked-math* :warn-on-boxed
                                      ;; avoid warnings that cannot affect production:
                                      *assert*         false}}

             :test     {:dependencies [[com.nedap.staffing-solutions/utils.test "1.6.2"]]
                        :jvm-opts     ["-Dclojure.core.async.go-checking=true"
                                       "-Duser.language=en-US"]}

             :provided {:dependencies [[com.google.guava/guava "25.1-jre" #_"not a real depenency - satisfies NVD"]]}

             :nvd      {:plugins      [[lein-nvd "1.4.0"]]
                        :nvd          {:suppression-file "nvd_suppressions.xml"}
                        ;; These are lein-nvd transitive dependencies, copied verbatim, which Lein could otherwise alter.
                        :dependencies [[com.esotericsoftware/minlog "1.3"]
                                       [com.github.spullara.mustache.java/compiler "0.8.17"]
                                       [com.google.code.gson/gson "2.8.5"]
                                       [com.h2database/h2 "1.4.196"]
                                       [com.h3xstream.retirejs/retirejs-core "3.0.1"]
                                       [joda-time "2.10" #_"For clj-time"]
                                       [org.apache.commons/commons-compress "1.19"]
                                       [org.json/json "20140107"]
                                       [org.owasp/dependency-check-core "5.3.2"]]}

             :ci       {:pedantic?    :abort
                        :jvm-opts     ["-Dclojure.main.report=stderr"]
                        :global-vars  {*assert* true} ;; `ci.release-workflow` relies on runtime assertions
                        :dependencies [[com.nedap.staffing-solutions/ci.release-workflow "1.6.0"]]}})
