(ns doll-smuggler.core-test
  (:require [clojure.test :refer :all]
            [doll-smuggler.core :refer :all]))

(deftest a-test
  (testing "Sanity Check"
    (is (= 1 1))))

;;this data is directly from the problem specification
(def atomic_dolls 
  [
    { :name "luke"    :weight 9   :value 150} { :name "anthony"  :weight 13 :value 35 }  
    { :name "candice" :weight 153 :value 200} { :name "dorothy"  :weight 50 :value 160} 
    { :name "puppy"   :weight 15  :value 60 } { :name "thomas"   :weight 68 :value 45 }  
    { :name "randal"  :weight 27  :value 60 } { :name "april"    :weight 39 :value 40 } 
    { :name "nancy"   :weight 23  :value 30 } { :name "bonnie"   :weight 52 :value 10 }  
    { :name "marc"    :weight 11  :value 70 } { :name "kate"     :weight 32 :value 30 } 
    { :name "tbone"   :weight 24  :value 15 } { :name "tranny"   :weight 48 :value 10 }  
    { :name "uma"     :weight 73  :value 40 } { :name "grumpkin" :weight 42 :value 70 } 
    { :name "dusty"   :weight 43  :value 75 } { :name "grumpy"   :weight 22 :value 80 }  
    { :name "eddie"   :weight 7   :value 20 } { :name "tory"     :weight 18 :value 12 } 
    { :name "sally"   :weight 4   :value 50 } { :name "babe"     :weight 30 :value 10 }])


;;one giant doll that shouldn't fit in the purse
(def giant_doll 
  [
    { :name "godzilla"    :weight 90000   :value 150} ])


;;very light dolls, all of them should fit in the purse
(def light_dolls 
  [
    { :name "luke"    :weight 1   :value 100} { :name "anthony"  :weight 1 :value 200 }  
    { :name "candice" :weight 1 :value 300} { :name "dorothy"  :weight 1 :value 400}])


(deftest a-test
  (testing "Sanity Check"
    (is (= 1 1))))

(comment "This test uses sample data set from problem description:
          https://github.com/micahalles/doll-smuggler. Test refactored 
          using code from https://github.com/mrozema/doll-smuggler")
(deftest find-dolls-atomic
  (testing 
    "Test the knapsack algorithm against the data given by Atomic Object"
    (let [[value indexes] (knapsack_calc (-> atomic_dolls count dec) 400 atomic_dolls)]
      ;; test existence of dolls
      (is (= (set indexes) #{0 1 2 3 4 6 10 15 16 17 18 20}))
      ;; test outputed total value
      (is (= value 1030))
      ;; test the outputted total weight
      (is (= (reduce + (map (comp :weight atomic_dolls) indexes)) 396))
      )
    )
  )

(comment "Giant doll shouldn't be able to fit in the purse")
(deftest find-dolls-giant
  (testing 
    "Test the knapsack algorithm against the giant doll data"
    (let [[value indexes] (knapsack_calc (-> giant_doll count dec) 400 giant_doll)]
      ;; test existence of dolls
      (is (= (set indexes) #{}))
      ;; test outputed total value
      (is (= value 0))
      ;; test the outputted total weight
      (is (= (reduce + (map (comp :weight giant_doll) indexes)) 0))
      )
    )
  )

(comment "all dolls should fit in the purse")
(deftest find-dolls-light
  (testing 
    "Test the knapsack algorithm against the light dolls data"
    (let [[value indexes] (knapsack_calc (-> light_dolls count dec) 400 light_dolls)]
      ;; test existence of dolls
      (is (= (set indexes) #{0 1 2 3}))
      ;; test outputed total value
      (is (= value 1000))
      ;; test the outputted total weight
      (is (= (reduce + (map (comp :weight light_dolls) indexes)) 4))
      )
    )
  )


