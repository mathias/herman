(defproject herman "0.1.0-SNAPSHOT"
  :description "Pallet Hadoop Cluster Recipes & Cascalog queries against that"
  :url "https://github.com/mathias/herman"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"sonatype" "http://oss.sonatype.org/content/repositories/releases/"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.palletops/pallet "0.8.0-RC.5"]
                 [pallet-hadoop "0.3.3-beta.2"]
                 [org.cloudhoist/pallet-jclouds "1.4.2"]
                 [org.jclouds/jclouds-all "1.4.2"]
                 [org.jclouds.driver/jclouds-jsch "1.4.2"]
                 [org.jclouds.driver/jclouds-slf4j "1.4.2"]
                 [ch.qos.logback/logback-classic "1.0.1"]
                 [ch.qos.logback/logback-core "1.0.1"]
                 [com.palletops/pallet-vmfest "0.3.0-beta.2"]])
