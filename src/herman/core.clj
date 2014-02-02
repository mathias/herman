(ns herman.core
  (:require [pallet.action.package :refer [package package-manager]]
            [pallet.core :refer [group-spec node-spec lift converge]]
            [pallet-hadoop.node :refer [lift-cluster cluster-spec node-group]]
            [pallet.crate.hadoop :as hadoop]
            [pallet.compute :refer [compute-service images]]
            [pallet.phase :refer [phase-fn]]
            [pallet.compute.vmfest :refer [add-image]]
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

(def vmfest (compute-service :vmfest))

(defn install-vm-image []
  (add-image vmfest "https://s3.amazonaws.com/vmfest-images/ubuntu-13.04-64bit.vdi.gz"))

(def example-cluster
  (cluster-spec :private
                {:jobtracker (pallet-hadoop.node/node-group [:jobtracker :namenode] 1 {:spec (vbox-node-spec :ubuntu-13.04-64bit 1024 1)})
                 :slaves     (pallet-hadoop.node/slave-group 1)}
                :base-machine-spec {:os-type-id "Ubuntu_64"
                                    :os-64-bit true}
                :base-props {:mapred-site {:mapred.task.timeout 300000
                                           :mapred.reduce.tasks 3
                                           :mapred.tasktracker.map.tasks.maximum 3
                                           :mapred.tasktracker.reduce.tasks.maximum 3
                                           :mapred.child.java.opts "-Xms1024m"}}))
(defn doit
  []
  (pallet-hadoop.node/boot-cluster  example-cluster :compute vmfest)
  (pallet-hadoop.node/start-cluster example-cluster :compute vmfest))

(defn vbox-node-spec
  [{:keys [image-id memory-size cpu-count]}]
  (node-spec :image {:image-id (or image-id *image-id*)}
             :hardware {:hardware-model
                        {:memory-size (to-int (or memory-size default-vm-ram))
                         :cpu-count (to-int (or cpu-count 1))
                         :network [{:attachment-type :host-only
                                    :host-only-interface "vboxnet0"}
                                   {:attachment-type :nat}]
                         :storage [{:name "IDE Controller"
                                    :bus :ide
                                    :devices [nil nil nil nil]}]
                         :boot-mount-point ["IDE Controller" 0]}}))
