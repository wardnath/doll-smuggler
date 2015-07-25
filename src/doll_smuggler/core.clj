(ns doll-smuggler.core
  (:require [cheshire.core :refer :all])
  (:gen-class))

;; get the name of a doll at a given index
(defn get_name [dolls index]
  (get ( get dolls index) "name"))

;; get the weight of a doll at a given index
(defn get_weight [dolls index]
  (get ( get dolls index) "weight"))

;; get the value of a doll at a given index
(defn get_value [dolls index]
  (get ( get dolls index) "value"))

;; generate vector of true values, this will be used to record the dolls that are taken
(defn gen_true_vec [_iterator]
  (def new_vec [])
  (if (_iterator < 0)
    new_vec
    (
      (conj new_vec true)
      (def _iterator (_iterator - 1)))))

;;parse json into clojure data structures
(def product_run 
  (parse-stream 
    (clojure.java.io/reader "./input_files/dolls_input_0.json")))

;; set the index of this method to the index of the last element of the dolls container
(def _index 
  (- 
    (count (get product_run "dolls"))
    1))

;; define the vector of dolls based on the json data
(def _dolls
  (get product_run "dolls"))

;; initial capacity of the handbag is equivalent to the max_weight json parameter
(def _capacity
  (get product_run "max_weight"))

;; define a vector of booleans for each doll
    ; (def _keep_vec
    ;   (gen_true_vec 
    ;     (count (get product_run "dolls"))
    ;     )
    ;   )

(declare kc)

(defn knapsack_calc [index_param dolls capacity keep_vec]
  (if (< index_param 0) 
    ;; if there are no more items return 0; stop recursing
    0 
    ;; else recursively search for better solution
    ( last
      ( list 
        (if (< capacity (get_weight dolls index_param))
          (def with_last_item -1) 
          ;; else 
          (def with_last_item
            (+ 
              (get_value dolls index_param) 
              (kc 
                (- index_param 1)
                dolls
                (- capacity (get_weight dolls index_param))
                keep_vec )
              )
            )
          )

        (def without_last_item
          (kc
            (- index_param 1)
            dolls
            capacity
            []
            ; (assoc keep_vec index_param false)
            ))
        ;; return the better solution
        (max with_last_item without_last_item)
        )
      )
    )
  )

(def kc (memoize knapsack_calc))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ( 
    

    (println (knapsack_calc _index _dolls _capacity []))

    
    )
)
