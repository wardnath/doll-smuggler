(ns doll-smuggler.core
  (:require [cheshire.core :refer :all])
  (:gen-class))

(def product_run 
  (parse-stream 
    (clojure.java.io/reader "./input_files/dolls_input_0.json")))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
