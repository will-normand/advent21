(ns advent21.day10
  (:require [advent21.utils.input :as i]))

(def error-score {\) 3
                  \] 57
                  \} 1197
                  \> 25137})
(def complete-score {\) 1
                     \] 2
                     \} 3
                     \> 4})
(def brackets {\( \) \[ \] \{ \} \< \>})
(def opens (set (keys brackets)))

(defn check-brackets
  "Checks for matching brackets.  In case of an error, returns the invalid character.
  If incomplete, returns the necessary completion sequence."
  [line]
  (loop [l line
         stack (list)]
    (if-let [head (first l)]
      (if (contains? opens head)
        (recur (rest l) (cons head stack))
        (if (= (brackets (first stack)) head)
          (recur (rest l) (rest stack))
          head))
      stack)))

(defn complete [line] (map brackets (let [result (check-brackets line)] (when (seq? result) result))))
(defn score [current-score bracket] (+ (complete-score bracket) (* current-score 5)))
(defn score-completion [completion] (reduce score 0 completion))
(defn score-completions [rows] (->> (map complete rows)
                                    (filter not-empty)
                                    (map #(score-completion %))))
(defn middle-score [scores] (nth (sort scores) (/ (dec (count scores)) 2)))

(defn part1 [filename] (let [input (i/load-input filename)]
                         (reduce + (filter #(not (nil? %)) (map #(error-score (check-brackets %)) input)))))

(defn part2 [filename] (middle-score (score-completions (i/load-input filename))))
