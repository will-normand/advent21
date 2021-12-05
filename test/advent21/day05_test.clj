(ns advent21.day05-test
  (:require [clojure.test :refer :all]
            [advent21.day05 :as day]))

(def test-file "day05test")

(deftest part1 (is (= (day/part1 test-file) 5)))

(deftest diagon-alley
  (is (= (day/diagonal-vents [[1 1] [3 3]]) [[1 1] [2 2] [3 3]]))
  (is (= (day/diagonal-vents [[9 7] [7 9]]) [[9 7] [8 8] [7 9]]))
  (is (= (day/diagonal-vents [[1 3] [0 2]]) [[1 3] [0 2]])))

(deftest part2 (is (= (day/part2 test-file) 12)))
