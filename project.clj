(defproject node-reframe "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :clean-targets ^{:protect false} ["build" "node_modules" "target"]
  :dependencies
  [[org.clojure/clojure "1.8.0"]
   [org.clojure/clojurescript "1.9.521"]
   [reagent "0.6.1"]
   [re-frame "0.9.3"]]
  :hooks [leiningen.cljsbuild]
  :plugins
  [[lein-cljsbuild "1.1.5"]
   [lein-figwheel "0.5.10"]
   [lein-npm "0.6.2"]
   [lein-pprint "1.1.2"]]
  :source-paths ["src"]
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  ;; Tools
  :cljsbuild
  {:builds
   {:app {:source-paths ["src"]
          :compiler {:main "node-reframe.core"
                     :optimizations :none
                     :output-to "build/app.js"
                     :output-dir "build"
                     :pretty-print true
                     :target :nodejs}}}}
  :figwheel
  {}
  :npm
  {:dependencies [[express "^4.15.2"]]
   :root "build"}
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  ;; Profiles
  :profiles
  {:dev
   {:dependencies [[figwheel-sidecar "0.5.10"]
                   [com.cemerick/piggieback "0.2.1"]]
    :cljsbuild
    {:builds
     {:app {:figwheel {:on-jsload "node-reframe.core/create-app"}}}}
    :npm
    {:dependencies [[source-map-support "^0.4.15"]
                    [ws "^2.3.1"]]}}
   :production
   {}
   :repl [:dev]})
