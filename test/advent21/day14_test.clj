(ns advent21.day14-test
  (:require [clojure.test :refer :all]
            [advent21.day14 :as day]))

(def test-file "day14test")

(deftest part1 (is (= (day/part1 test-file) 1588)))
(deftest part2 (is (= (day/part2 test-file) nil)))
