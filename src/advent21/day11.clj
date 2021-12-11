(ns advent21.day11
  (:require [advent21.utils.input :as i]
            [advent21.utils.table :as t]
            [clojure.set :as set]))

(defn load-grid [filename] (mapv (fn [row] (mapv #(i/parse-int (str %)) row)) (i/load-input filename)))

(defn inc-grid [grid] (mapv #(mapv inc %) grid))
(defn inc-adjacents [grid [x y]]
  (let [adjacents (t/adjacents grid [x y])]
    (reduce (fn [g [ax ay]]
              (t/replace-cell g ax ay (inc (t/get-cell g ax ay))))
            grid
            adjacents)))

(def do-flash? (partial < 9))

(defn flash [grid]
  (loop [g grid
         flashes #{}]
    (let [possible-next-flashes (t/find-cell-fn g do-flash?)
          next-flashes (set/difference (set possible-next-flashes) flashes)]
      (if-let [next-flash (first next-flashes)]
        (recur (inc-adjacents g next-flash) (conj flashes next-flash))
        [g (count flashes)]))))

(defn reset-grid [grid]
  (->> (t/find-cell-fn grid do-flash?)
       (reduce (fn [g [cx cy]] (t/replace-cell g cx cy 0)) grid)))

(defn step [grid]
  (let [incremented (inc-grid grid)
        [flashed-grid flashes] (flash incremented)
        reset (reset-grid flashed-grid)]
    [reset flashes]))

(defn part1 [filename]
  (let [grid (load-grid filename)]
    (loop [i 0
           g grid
           flashes 0]
      (if (= i 100)
        flashes
        (let [[new-grid new-flashes] (step g)]
          (recur (inc i) new-grid (+ flashes new-flashes)))))))

(defn part2 [filename]
  (let [grid (load-grid filename)
        grid-size (t/cell-count grid)]
    (loop [i 1
           g grid]
      (let [[new-grid new-flashes] (step g)]
        (if (= new-flashes grid-size)
          i
          (recur (inc i) new-grid))))))
