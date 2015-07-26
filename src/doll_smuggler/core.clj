(ns doll-smuggler.core
  (:require [cheshire.core :refer :all])
  (:gen-class))
(use '[clojure.string :only [join]])
(use 'clojure.walk)

;;parse json into clojure data structures
(def product_run 
  (parse-stream 
    (clojure.java.io/reader "./input_files/dolls_input_0.json")))

;; define the vector of dolls based on the json data
(def dolls
    (keywordize-keys (get product_run "dolls")))

;; initial capacity of the handbag is equivalent to the max_weight json parameter
(def capacity
  (get product_run "max_weight"))


;; code adopted from http://rosettacode.org/wiki/Knapsack_problem/0-1#Clojure


(declare kc) ;forward decl for memoization function
 
(defn knapsack_calc [i w]
  (cond
    (< i 0) [0 []]
    (= w 0) [0 []]
    :else
    (let [{wi :weight vi :value} (get dolls i)]
      (if (> wi w)
        (kc (dec i) w)
        (let [[vn sn :as no]  (kc (dec i) w)
              [vy sy :as yes] (kc (dec i) (- w wi))]
          (if (> (+ vy vi) vn)
            [(+ vy vi) (conj sy i)]
            no))))))
 
(def kc (memoize knapsack_calc))

(defn -main
  [& args]
  

  (let [[value indexes] (knapsack_calc (-> dolls count dec) capacity)
      names (map (comp :name dolls) indexes)]
  (println "dolls to pack:" (join ", " names))
  (println "total value:" value)
  (println "total weight:" (reduce + (map (comp :weight dolls) indexes))))
  )
