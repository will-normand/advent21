(ns advent21.day03
  (:require [clojure.string :as str]
            [clojure.core.matrix :as mx]))

(defn load-input [filename]
  (map (fn [line] (map #(Integer/parseInt (str %)) line)) (str/split-lines (slurp filename))))
(defn input-matrix [input] (mx/array input))

(defn most-common [column]
  (let [size (count column)
        sum (reduce + column)]
    (if (>= sum (/ size 2)) 1 0)))

(defn gamma [matrix] (reduce str (map most-common (mx/columns matrix))))
(defn bitwise-not [bin-str] (str/join (map #(if (= (str %) "1") "0" "1") bin-str)))
(defn epsilon [gamma] (bitwise-not gamma))

(defn part1 [filename]
  (let [m (input-matrix (load-input filename))
        gamma (gamma m)
        epsilon (epsilon gamma)]
    (* (Integer/parseInt gamma 2) (Integer/parseInt epsilon 2))))

(defn filter-rows [m v column-idx] (mx/array (filter #(= (nth % column-idx) v) (mx/rows m))))

;; Part 2

(defn filter-matrix [matrix filter-fn]
  (loop
    [m matrix
     column-idx 0]
    (if (= (count m) 1)
      (Integer/parseInt (str/join (mx/get-row m 0)) 2)
      (let [most-common (filter-fn (mx/get-column m column-idx))
            new-mx (filter-rows m most-common column-idx)]
        (recur new-mx (+ column-idx 1))))))

(defn ogr [matrix] (filter-matrix matrix most-common))

(defn least-common [column]
  (let [size (count column)
        sum (reduce + column)]
    (if (< sum (/ size 2)) 1 0)))

(defn csr [matrix] (filter-matrix matrix least-common))

(defn part2 [filename]
  (let [m (input-matrix (load-input filename))
        ogr (ogr m)
        csr (csr m)]
    (* ogr csr)))
