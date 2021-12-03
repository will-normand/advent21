(ns advent21.day03-test
  (:require [clojure.test :refer :all]
            [advent21.day03 :as day]))

(def test-file "resources/day03test")
(def test-input (day/input-matrix (day/load-input test-file)))

(deftest part1
  (is (= (day/part1 test-file) 198)))

(deftest ogr (is (= (day/ogr test-input) 23)))
(deftest csr (is (= (day/csr test-input) 10)))

(deftest part2
  (is (= (day/part2 test-file) 230)))
