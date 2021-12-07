(ns advent21.day06
  (:require [clojure.string :as str]
            [advent21.utils.input :as i]))

(defn load-input ([filename] (str/split-lines (slurp filename))))
(defn parse-input [filename] (->> (-> (load-input filename)
                                      (first)
                                      (str/split #","))
                                  (map i/parse-int)))

(defn fish-counts [starting-shoal] (frequencies starting-shoal))

(defn shoal-day [fish-map]
  (let [eggs (get fish-map 0 0)
        without-zeros (dissoc fish-map 0)
        aged (into {} (map (fn [[k v]] [(dec k) v]) without-zeros))]
    (-> aged
        (assoc 6 (+ eggs (get aged 6 0)))
        (assoc 8 eggs))))

(defn run-simulation [generations starting-shoal]
  (loop [x 0
         fishes (fish-counts starting-shoal)]
    (if (= x generations)
      (reduce + (vals fishes))
      (recur (inc x) (shoal-day fishes)))))

(defn part1 [filename]
  (run-simulation 80 (parse-input filename)))

(defn part2 [filename]
  (run-simulation 256 (parse-input filename)))
