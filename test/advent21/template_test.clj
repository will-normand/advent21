(ns advent21.template-test
  (:require [clojure.test :refer :all]
            [advent21.template :as day]))

(def test-file "resources/dayXXtest")

(deftest part1 (is (= (day/part1 test-file) nil))
               )
(deftest part2 (is (= (day/part2 test-file) nil)))
