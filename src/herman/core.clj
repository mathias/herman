(ns herman.core
  (:require [pallet.actions :refer [exec package]]
            [pallet.api :refer [group-spec lift plan-fn converge group-spec]]
            [pallet.core.user :refer [make-user]]
            [pallet.compute :refer [instantiate-provider nodes]]))

(def hadoop-ip "10.10.10.10")

(def hadoop-provider
  (instantiate-provider
   "node-list"
   :node-list [["hadoop" "hadoop-master" hadoop-ip :ubuntu]]))

(defn install-vim []
  (lift
   (group-spec "hadoop-master"
               :phases {:configure (plan-fn (package "vim"))})
   :compute hadoop-provider
   :timeout-ms 10000))
