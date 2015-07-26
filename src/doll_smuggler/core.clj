;; Code written for Atomic Object programming challenge 
;; Written by Nathan Ward
;; Original commit date: 2015/7/24
;; Special thanks to Rosetta Code and Matt Rosema, whose code helped to move this project forward
(ns doll-smuggler.core
  (:require [cheshire.core :refer :all] 
            [clojure.tools.cli :refer [parse-opts]]
    )
  (use [clojure.string :only [join]])
  (use clojure.walk)
  (use clojure.java.io)
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-f" "--file FILE" "Input File"
    :default "./input_files/dolls_input_0.json"
    ;:parse-fn #(Integer/parseInt %)

    :validate [#(.exists (as-file %1)) "Invalid File Path"]]]
   ; ;; A non-idempotent option
   ; ["-v" nil "Verbosity level"
   ;  :id :verbosity
   ;  :default 0
   ;  :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ; ;; A boolean option defaulting to nil
   ; ["-h" "--help"]]
   )

;;parse json into clojure data structures
;; example file: "./input_files/dolls_input_0.json"
; (def product_run 
;   (parse-stream 
;     (clojure.java.io/reader (get options :file))))

(defstruct doll :name :weight :value)
 
(defn itemize [item-data]
  (vec (map #(apply struct doll %) (partition 3 item-data))))


(defn product_run [file]
  (parse-stream 
    (clojure.java.io/reader file)))

;; define the vector of dolls based on the json data
(defn get_dolls [file]
    (keywordize-keys (get (product_run file) "dolls")))

;; initial capacity of the handbag is equivalent to the max_weight json parameter
(defn get_capacity [file]
  (get (product_run file) "max_weight"))




;; knapsack calculation code adopted from http://rosettacode.org/wiki/Knapsack_problem/0-1#Clojure

(declare kc) ;forward decl for memoization function
 
(defn knapsack_calc [i w dolls]
  (cond
    (< i 0) [0 []]
    (= w 0) [0 []]
    :else
    (let [{wi :weight vi :value} (get dolls i)]
      (if (> wi w)
        (kc (dec i) w dolls)
        (let [[vn sn :as no]  (kc (dec i) w dolls)
              [vy sy :as yes] (kc (dec i) (- w wi) dolls)]
          (if (> (+ vy vi) vn)
            [(+ vy vi) (conj sy i)]
            no))))))
 
(def kc (memoize knapsack_calc))

(defn -main
  [& args]
  (let [{:keys [options arguments]} (parse-opts args cli-options)]
    
    (let [dolls (get_dolls (get options :file)) capacity (get_capacity (get options :file))]

      (let [[value indexes] (knapsack_calc (-> dolls count dec) capacity dolls)
          names (map (comp :name dolls) indexes) values (map (comp :value dolls) indexes) weights (map (comp :weight dolls) indexes)]
        
        ; (println "dolls to pack:" names)
        ; (println "values of dolls (respectively):" (join ", " values))
        ; (println "weights of dolls (repsectively):" (join ", " weights))
        ; (println "total value:" value)
        ; (println "total weight:" (reduce + (map (comp :weight dolls) indexes)))

        (def dolls_out (itemize (flatten (map vector names values weights))))

        (println (generate-string { "total_weight" (reduce + (map (comp :weight dolls) indexes))
          "total_value" value
          "dolls" dolls_out}))
      )
    )
  )
)
