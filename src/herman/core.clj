(ns herman.core
  (:require [pallet.action.package :refer [package package-manager]]
            [pallet.core :refer [group-spec lift converge]]
            [pallet-hadoop.node :refer [lift-cluster cluster-spec node-group]]
            [pallet.crate.hadoop :as hadoop]
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

(def hadoop-cluster
  (cluster-spec :public
                {:master (node-group [:jobtracker :namenode :slavenode])
                 :base-machine-spec {:os-family :ubuntu
                                     :os-version-matches "13.10"
                                     :os-64-bit true}
                 :base-props {:mapred-site {:mapred.task.timeout 300000
                                            :mapred.reduce.tasks 3
                                            :mapred.tasktracker.map.tasks.maximum 3
                                            :mapred.tasktracker.reduce.tasks.maximum 3
                                            :mapred.child.java.opts "-Xms1024m"}}}))

(defn configure-cluster []
  (lift
   (group-spec "hadoop-master"
               :phases {:configure (phase-fn (package-manager :update)
                                             (pallet.crate.java/java :openjdk)
                                             (hadoop/install  :cloudera))})
   :compute hadoop-provider))
