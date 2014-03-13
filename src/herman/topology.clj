(ns herman.topology
  "Topology

More info on the Clojure DSL here:

https://github.com/nathanmarz/storm/wiki/Clojure-DSL"
  (:require [herman
             [spouts :refer [article-spout]]
             [bolts :refer [stormy-bolt herman-bolt]]]
            [backtype.storm [clojure :refer [topology spout-spec bolt-spec]] [config :refer :all]])
  (:import [backtype.storm LocalCluster LocalDRPC]))

(defn article-ingest-topology []
  (topology
   {"article-spout" (spout-spec article-spout)}

   {"newspaper-bolt" (bolt-spec {"article-spout" ["type"]} herman-bolt :p 2)}))

(defn run! [& {debug "debug" workers "workers" :or {debug "true" workers "2"}}]
  (doto (LocalCluster.)
    (.submitTopology "stormy topology"
                     {TOPOLOGY-DEBUG (Boolean/parseBoolean debug)
                      TOPOLOGY-WORKERS (Integer/parseInt workers)}
                     (stormy-topology))))
