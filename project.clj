(defproject advent21 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2021"
  :url "https://github.com/will-normand/advent21"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [net.mikera/core.matrix "0.62.0"]]
  :repl-options {:init-ns advent21.core}
  :test-paths ["test" "src"])
