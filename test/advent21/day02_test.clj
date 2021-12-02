(ns advent21.day02-test
  (:require [clojure.test :refer :all]
            [advent21.day02 :as day]))

(def expected-input [[:forward 5]
                     [:down 5]
                     [:forward 8]
                     [:up 3]
                     [:down 8]
                     [:forward 2]])

(deftest load-input
  (is (= (day/load-input "resources/day02test") expected-input)))

(deftest along
  (is (= (day/along expected-input) 15)))

(deftest depth
  (is (= (day/depth expected-input) 10)))

(deftest part1
  (is (= (day/part1 "resources/day02test") 150)))

(deftest aiming
  (is (= (day/aiming expected-input) 900)))
