(ns advent21.day04
  (:require [clojure.string :as str]
            [advent21.utils.table :as t]))

(defn get-numbers [number-row] (map #(Integer/parseInt %) (str/split number-row #",")))

(defn load-input [filename]
  (let [raw-lines (str/split-lines (slurp filename))
        numbers (get-numbers (first raw-lines))
        raw-boards (drop 2 raw-lines)]
    [numbers raw-boards]))

(defn parse-boards [raw-boards]
  (let [boards-strings (filter #(not= (count %) 1) (partition-by #(= % "") raw-boards))
        parse-row (fn [row-string] (vec (map #(Integer/parseInt %) (str/split (str/trim row-string) #" +"))))]
    (map (fn [board-strings] (vec (map parse-row board-strings))) boards-strings)))

(def initial-card (t/table-of 5 5 false))

(defn bingo-cards
  "Bingo cards are a [number matrix, found matrix] pair."
  [boards] (map #(vector % initial-card) boards))

(defn bingo [current-cards]
  (filter (fn [[num-board found-board]]
            (if (or (t/complete-row? found-board true) (t/complete-column? found-board true))
              [num-board found-board]
              nil))
          current-cards))

(defn bingo-score [[num-board found-board] number]
  (let [unmarked-idxs (t/find-cell found-board false)]
    (* number (reduce + (map (fn [[x y]] (t/get-cell num-board x y)) unmarked-idxs)))))

(defn draw
  "Handle the draw of one number.  Mark it off on each board, and check for a full card."
  [cards number part2]
  (let [new-cards (map (fn [[num-board found-board]]
                         (let [cells-to-update (t/find-cell num-board number)]
                           (if (empty? cells-to-update)
                             [num-board found-board]
                             (let [[x y] (first cells-to-update)] ; Boards have no duplicate numbers
                               [num-board (t/replace-cell found-board x y true)]))))
                       cards)]

    (let [bingo-boards (bingo new-cards)]
      (if (not-empty bingo-boards)
        (if (nil? part2)
          ; Part 1 - just return the score
          (bingo-score (first bingo-boards) number)
          ; Part 2 - remove the board, or calculate score if it's the only one left
          (if (= (count new-cards) 1)
            (bingo-score (first bingo-boards) number)
            (filter #(not (contains? (set bingo-boards) %)) new-cards)))
        new-cards))))

(defn play [cards numbers part2]
  (loop [c cards
         ns numbers]
    (let [new-cards (draw c (first ns) part2)]
      (if (= 1 1) (if (int? new-cards)
                    new-cards
                    (recur new-cards (rest ns)))
                  (if (= (count new-cards) 1)
                    (println new-cards)
                    (recur new-cards (rest ns)))
                  ))))

(defn part1 [filename]
  (let [[numbers raw-boards] (load-input filename)
        cards (bingo-cards (parse-boards raw-boards))]
    (play cards numbers nil)))

(defn part2 [filename]
  (let [[numbers raw-boards] (load-input filename)
        cards (bingo-cards (parse-boards raw-boards))]
    (play cards numbers :part2)))
