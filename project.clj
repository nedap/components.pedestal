;; Please don't bump the library version by hand - use ci.release-workflow instead.
(defproject com.nedap.staffing-solutions/components.pedestal "2.0.1-alpha3"
  ;; Please keep the dependencies sorted a-z.
  :dependencies [[com.grzm/component.pedestal "0.1.7"]
                 [com.nedap.staffing-solutions/utils.modular "2.2.0"]
                 [com.nedap.staffing-solutions/speced.def "2.1.1"]
                 [com.stuartsierra/component "1.0.0"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [medley "1.3.0"]
                 [org.clojure/clojure "1.10.3"]]

  :managed-dependencies [[ring/ring-codec "1.1.1"]
                         [ring/ring-core "1.9.5" #_"nvd"]]

  :exclusions [org.clojure/clojurescript]

  :description "A Pedestal server offered as a Clojure Component."

  :url "https://github.com/nedap/components.pedestal"

  :min-lein-version "2.0.0"

  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :signing {:gpg-key "releases-staffingsolutions@nedap.com"}

  :repositories        {"github" {:url "https://maven.pkg.github.com/nedap/*"
                                  :username "github"
                                  :password :env/github_token}}

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

             :provided {:dependencies [[com.google.guava/guava "31.1-jre" #_"not a real depenency - satisfies NVD"]]}

             :nvd      {:plugins      [[lein-nvd "1.4.0"]]
                        :nvd          {:suppression-file "nvd_suppressions.xml"}}

             :ncrw       {:global-vars    {*assert* true} ;; `ci.release-workflow` relies on runtime assertions
                          :source-paths   ^:replace []
                          :test-paths     ^:replace []
                          :resource-paths ^:replace []
                          :plugins        ^:replace []
                          :dependencies   ^:replace [[com.nedap.staffing-solutions/ci.release-workflow "1.14.1"]]}

             :ci   {:pedantic?    :abort
                    :jvm-opts     ["-Dclojure.main.report=stderr"]}})
