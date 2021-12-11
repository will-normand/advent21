(ns advent21.day11-test
  (:require [clojure.test :refer :all]
            [advent21.day11 :as day]))

(def test-file "day11test")
(def grid (day/load-grid test-file))

(deftest step (is (= (first (day/step grid)) (day/load-grid "day11test_gen1"))))
(deftest step-with-flashes
  (is (= (first (day/step (first (day/step grid)))) (day/load-grid "day11test_gen2"))))

(deftest part1 (is (= (day/part1 test-file) 1656)))
(deftest part2 (is (= (day/part2 test-file) 195)))
