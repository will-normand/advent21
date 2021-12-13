(ns advent21.day13-test
  (:require [clojure.test :refer :all]
            [advent21.day13 :as day]))

(def test-file "day13test")

(deftest part1 (is (= (day/part1 test-file) 17)))
