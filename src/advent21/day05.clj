(ns advent21.day05
  (:require [clojure.string :as str]
            [advent21.utils.input :as i]))

(defn parse-row [row]
  (let [[end1 end2] (str/split row #" -> ")
        end1-coord (mapv i/parse-int (str/split end1 #","))
        end2-coord (mapv i/parse-int (str/split end2 #","))]
    [end1-coord end2-coord]))
(defn coords [rows] (map parse-row rows))

(defn hor-ver-vents [[[x1 y1] [x2 y2]]]
  (cond (= x1 x2) (map #(vector x1 %) (range (min y1 y2) (inc (max y1 y2))))
        (= y1 y2) (map #(vector % y1) (range (min x1 x2) (inc (max x1 x2))))
        :else []))

(defn diagonal? [[[x1 y1] [x2 y2]]] (and (not= x1 x2) (not= y1 y2)))
(defn diagonal-vents [[[x1 y1] [x2 y2]]]
  (if (diagonal? [[x1 y1] [x2 y2]])
    (map vector
         (if (< x1 x2) (range x1 (inc x2)) (reverse (range x2 (inc x1))))
         (if (< y1 y2) (range y1 (inc y2)) (reverse (range y2 (inc y1)))))
    []))

(defn hor-ver-map [coords] (mapcat hor-ver-vents coords))
(defn diagonal-map [coords] (mapcat diagonal-vents coords))

(defn dangerous-vents-count [positions]
  (->> positions
       (frequencies)
       (filter (fn [[_ vents]] (>= vents 2)))
       (count)))

(defn part1 [filename]
  (let [coords (coords (i/load-input filename))]
    (dangerous-vents-count (hor-ver-map coords))))

(defn part2 [filename]
  (let [coords (coords (i/load-input filename))]
    (dangerous-vents-count (concat (hor-ver-map coords) (diagonal-map coords)))))
