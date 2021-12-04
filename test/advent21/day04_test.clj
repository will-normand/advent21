(ns advent21.day04-test
  (:require [clojure.test :refer :all]
            [advent21.day04 :as day]))

(def test-file "resources/day04test")

(deftest part1 (is (= (day/part1 test-file) 4512)))

(deftest part2 (is (= (day/part2 test-file) nil)))
