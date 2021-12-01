(ns advent21.day01-test
  (:require [clojure.test :refer :all]
            [advent21.day01 :as day]))

(def test-file "resources/day01test")
(def depths [199
             200
             208
             210
             200
             207
             240
             269
             260
             263])
(def expected-increase 7)
(def sums [607
           618
           618
           617
           647
           716
           769
           792])
(def expected-sums-increase 5)

(deftest load-input-test
  (is (= (day/load-input test-file) depths)))

(deftest increase-count
  (is (= (day/increase-count depths) expected-increase)))

(deftest part1
  (is (= (day/part1 test-file) expected-increase)))

(deftest three-measurement-sums
  (is (= (day/three-measurement-sums depths) sums)))

(deftest part2
  (is (= (day/part2 test-file) expected-sums-increase)))
