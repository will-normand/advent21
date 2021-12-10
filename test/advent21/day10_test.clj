(ns advent21.day10-test
  (:require [clojure.test :refer :all]
            [advent21.day10 :as day]))

(def test-file "day10test")

(deftest part1 (is (= (day/part1 test-file) 26397)))
(deftest part2 (is (= (day/part2 test-file) 288957)))
