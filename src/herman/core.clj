(ns herman.core
  (:require [pallet.action.package :refer [package]]
            [pallet.core :refer [group-spec lift converge]]
            [pallet.compute :refer [compute-service]]
            [pallet.phase :refer [phase-fn]]
            [pallet.compute.node-list :refer [node-list-service make-node]]))

(def hadoop-ip "10.10.10.10")

(def hadoop-provider
  (compute-service
   "node-list"
   :node-list [["hadoop" "hadoop-master" hadoop-ip :ubuntu]]))

(defn install-vim []
  (lift
   (group-spec "hadoop-master"
               :phases {:configure (phase-fn (package "vim"))})
   :compute hadoop-provider))
