(ns herman.spouts
  "Spouts.

More info on the Clojure DSL here:

https://github.com/nathanmarz/storm/wiki/Clojure-DSL"
  (:require [backtype.storm [clojure :refer [defspout spout emit-spout!]]]))

(defspout article-spout ["article"]
  [conf context collector]
  (let [articles-to-ingest [:some :list]]
    (spout
     (nextTuple [])))
  )

(defspout type-spout ["type"]
  [conf context collector]
  (let [stormys [:regular :bizarro]]
    (spout
     (nextTuple []
       (Thread/sleep 1000)
       (emit-spout! collector [(rand-nth stormys)]))
     (ack [id]
        ;; You only need to define this method for reliable spouts
        ;; (such as one that reads off of a queue like Kestrel)
        ;; This is an unreliable spout, so it does nothing here
        ))))
