(ns advent21.day12-test
  (:require [clojure.test :refer :all]
            [advent21.day12 :as day]))

(def test-file "day12test")

(deftest part1 (is (= (day/part1 test-file) 10)))
(deftest part2 (is (= (day/part2 test-file) 36)))
