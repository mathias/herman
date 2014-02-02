(defproject herman "0.1.0-SNAPSHOT"
  :description "Pallet Hadoop Cluster Recipes & Cascalog queries against that"
  :url "https://github.com/mathias/herman"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"sonatype" "http://oss.sonatype.org/content/repositories/releases/"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.cloudhoist/pallet "0.7.5"]
                 [com.palletops/pallet-vmfest "0.4.0-alpha.1"]
                 [org.cloudhoist/pallet-hadoop "0.3.3"]
                 [ch.qos.logback/logback-classic "1.0.9"]
                 [org.slf4j/jcl-over-slf4j "1.7.3"]])
