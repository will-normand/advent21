(ns advent21.day10
  (:require [clojure.string :as str]
            [advent21.utils.input :as i]))

(defn load-input ([filename] (str/split-lines (slurp filename))))

(def score {\) 3
            \] 57
            \} 1197
            \> 25137
            nil 0})

(def brackets { \( \) \[ \] \{ \} \< \>})

(def opens #{\( \[ \{ \<})

(defn parse-row [row] (seq row))

(defn first-error [line]
  (loop [l line
         stack (list)]
    (if-let [head (first l)]
      (if (contains? opens head)
        (recur (rest l) (cons head stack))
        (if (= (brackets (first stack)) head)
          (recur (rest l) (rest stack))
          head))
      nil)))

(defn part1 [filename] (let [input (parse-row (i/load-input filename))]
                         (reduce + (filter #(not (nil? %)) (map #(score (first-error %)) input)))))

(defn part2 [filename])
