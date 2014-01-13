(defproject herman "0.1.0-SNAPSHOT"
  :description "Pallet Hadoop Cluster Recipes & Cascalog queries against that"
  :url "https://github.com/mathias/herman"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"sonatype" "http://oss.sonatype.org/content/repositories/releases/"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.palletops/pallet "0.8.0-RC.5"]
                 [com.palletops/pallet-vmfest "0.3.0-beta.2" :exclusions [org.clojure/tools.logging]]])
