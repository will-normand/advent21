(ns advent21.day08-test
  (:require [clojure.test :refer :all]
            [advent21.day08 :as day]))

(def test-file "day08test")

(deftest part1 (is (= (day/part1 test-file) 26)))
(deftest part2 (is (= (day/part2 test-file) 61229)))
