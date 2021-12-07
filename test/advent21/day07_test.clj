(ns advent21.day07-test
  (:require [clojure.test :refer :all]
            [advent21.day07 :as day]))

(def test-file "day07test")

(deftest part1 (is (= (day/part1 test-file) 37)))
(deftest part2 (is (= (day/part2 test-file) 168)))
