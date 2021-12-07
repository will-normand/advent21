(ns advent21.day06-test
  (:require [clojure.test :refer :all]
            [advent21.day06 :as day]))

(def test-file "resources/day06test")

(deftest part1 (is (= (day/part1 test-file) 5934)))

(deftest part2 (is (= (day/part2 test-file) 26984457539)))
