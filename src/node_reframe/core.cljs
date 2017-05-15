(ns node-reframe.core
  (:require [cljs.nodejs :as nodejs]
            [re-frame.core :as re-frame]))

(nodejs/enable-util-print!)

(defonce express (nodejs/require "express"))
(defonce http (nodejs/require "http"))

(defonce state (js-obj {}))

(defn create-app []
  (.log js/console "Create express app")
  (aset state "app"
        (doto (express)
          (.get "/" (fn [req res]
                      (re-frame/dispatch [:my-event req res]))))))

(defn -main []
  (let [port (some-> js/process
                     (aget "env" "PORT")
                     (js/parseInt))]
    (create-app)
    (doto (.createServer http #((aget state "app") %1 %2))
      (.listen (or port 3000)))))

(set! *main-cli-fn* -main)

(re-frame/reg-event-fx
 :my-event
 (fn [_ [_ _ res]]
   (-> res
       (.status 200)
       (.send #js {:hello "World event"}))
   {}))
