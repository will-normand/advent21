(ns advent21.day07
  (:require [advent21.utils.input :as i]))

(defn fuel [distance] (reduce + (range 1 (inc distance))))
(defn basic-fuel-to-move [x crabs] (reduce + (map (fn [crab] (Math/abs ^Integer (- x crab))) crabs)))
(defn more-fuel-to-move [x crabs] (reduce + (map (fn [crab] (fuel (Math/abs ^Integer (- x crab)))) crabs)))
(defn min-fuel [crabs fuel-fn] (apply min (map #(fuel-fn % crabs) (range (apply min crabs) (inc (apply max crabs))))))

(defn part1 [filename] (min-fuel (i/parse-int-row filename) basic-fuel-to-move))
(defn part2 [filename] (min-fuel (i/parse-int-row filename) more-fuel-to-move))
