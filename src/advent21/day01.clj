(ns advent21.day01
  (:require [clojure.string :as str]))

(defn load-input [filename]
  (map #(Integer/parseInt %) (str/split-lines (slurp filename))))

(defn increase-count [depths]
  (let [changes (map vector (drop-last depths) (rest depths))]
    (count (filter (fn [[a b]] (< a b)) changes))))

(defn part1 [filename]
  (-> (load-input filename)
      (increase-count)))

(defn three-measurement-sums [depths]
  (let [depths1 (drop-last 2 depths)
        depths2 (drop 1 (drop-last 1 depths))
        depths3 (drop 2 depths)]
    (map + depths1 depths2 depths3)))

(defn part2 [filename]
  (-> (load-input filename)
      (three-measurement-sums)
      (increase-count)))
