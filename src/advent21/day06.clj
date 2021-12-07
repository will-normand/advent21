(ns advent21.day06
  (:require [clojure.string :as str]
            [advent21.utils.input :as i]))

(defn load-input ([filename] (str/split-lines (slurp filename))))
(defn parse-input [filename] (->> (-> (load-input filename)
                                      (first)
                                      (str/split #","))
                                  (map i/parse-int)))

(defn do-day [fish] (if (= fish 0) [6 8] [(dec fish)]))
(defn day [fishes] (mapcat do-day fishes))

(defn run-simulation [generations starting-shoal]
  (loop [x 0
         fishes starting-shoal]
    (if (= x generations)
      (count fishes)
      (recur (inc x) (day fishes)))))

(defn part1 [filename]
  (run-simulation 80 (parse-input filename)))

(defn part2 [filename]
  (run-simulation 256 (parse-input filename)))
