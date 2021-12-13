(ns advent21.day13
  (:require [clojure.string :as str]
            [advent21.utils.input :as i]
            [advent21.utils.table :as t]))

(defn parse-input [filename]
  (let [rows (i/load-input filename)
        raw-dots (take-while not-empty rows)
        raw-folds (rest (drop-while not-empty rows))
        dots (map (fn [raw-dot] (mapv i/parse-int (str/split raw-dot #","))) raw-dots)
        folds (map (fn [raw-fold] (let [[dir dist] (str/split (str/replace raw-fold "fold along " "") #"=")]
                                    [(keyword dir) (i/parse-int dist)]))
                   raw-folds)]
    [dots folds]))

(defn fold [dots [fold-direction fold-dist]]
  (cond
    (= :x fold-direction)
    (set (map (fn [[x y]] (if (< x fold-dist) [x y] [(- fold-dist (- x fold-dist)) y])) dots))
    (= :y fold-direction)
    (set (map (fn [[x y]] (if (< y fold-dist) [x y] [x (- fold-dist (- y fold-dist))])) dots))))

(defn print-map [dots]
  (let [max-x (apply max (map first dots))
        max-y (apply max (map second dots))
        empty-table (t/table-of (inc max-x) (inc max-y) " ")]
    (t/print-table (reduce (fn [table [x y]] (t/replace-cell table x y "#")) empty-table dots))))

(defn part1 [filename] (let [[dots folds] (parse-input filename)] (count (fold dots (first folds)))))

(defn part2 [filename]
  (let [[dots folds] (parse-input filename)]
    (print-map (reduce (fn [ds fold-instruction] (fold ds fold-instruction)) dots folds))))
