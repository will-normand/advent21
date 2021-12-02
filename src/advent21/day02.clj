(ns advent21.day02
  (:require [clojure.string :as str]))

(defn parse-row [row]
  (let [[direction amount] (str/split row #" ")
        direction-sym (cond
                        (= direction "forward") :forward
                        (= direction "up") :up
                        (= direction "down") :down
                        :else (str "ERROR got: " direction))]
    [direction-sym (Integer/parseInt amount)]))

(defn load-input [filename]
  (map parse-row (str/split-lines (slurp filename))))

(defn along [moves]
  (reduce + (map second (filter (fn [[direction _]] (= :forward direction)) moves))))

(defn depth [moves]
  (->> moves
       (map (fn [[direction amount]]
              (cond (= direction :down) amount
                    (= direction :up) (* -1 amount)
                    :else 0)))
       (reduce +)))

(defn part1 [filename]
  (let [moves (load-input filename)]
    (* (along moves) (depth moves))))

;; Part 2
(defn aiming [moves]
  (loop [ms moves
         depth 0
         hor-pos 0
         aim 0]
    (if (empty? ms)
      (* depth hor-pos)
      (let [[direction amount] (first ms)]
        (cond
          (= direction :down) (recur (rest ms) depth hor-pos (+ aim amount))
          (= direction :up) (recur (rest ms) depth hor-pos (- aim amount))
          (= direction :forward) (recur (rest ms)
                                        (+ depth (* aim amount))
                                        (+ hor-pos amount)
                                        aim))))))

(defn part2 [filename]
  (aiming (load-input filename)))
