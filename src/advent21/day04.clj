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

(def card (t/table-of 5 5 false))

(defn bingo-cards
  "Bingo cards are a [number matrix, found matrix] pair."
  [boards] (map #(vector % card) boards))

(defn bingo [current-cards]
  (some (fn [[num-board found-board]]
          (if (or (t/complete-row? found-board true) (t/complete-column? found-board true))
            [num-board found-board]
            nil))
        current-cards))

(defn bingo-score [[num-board found-board] number]
  (let [unmarked-idxs (t/find-cell found-board false)]
    (* number (reduce + (map (fn [[x y]] (t/get-cell num-board x y)) unmarked-idxs)))))

(defn draw
  "Handle the draw of one number.  Mark it off on each board, and check for a full card."
  [cards number]
  (let [new-cards (map (fn [[num-board found-board]]
                         (let [cells-to-update (t/find-cell num-board number)]
                           (if (empty? cells-to-update)
                             [num-board found-board]
                             (let [[x y] (first cells-to-update)] ; Boards have no duplicate numbers
                               [num-board (t/replace-cell found-board x y true)]))))
                       cards)]
    (if-let [bingo-board (bingo new-cards)]
      (bingo-score bingo-board number)
      new-cards)))

(defn play [cards numbers]
  (loop [c cards
         ns numbers]
    (let [new-cards (draw c (first ns))]
      (if (int? new-cards) new-cards (recur new-cards (rest ns))))))

(defn part1 [filename]
  (let [[numbers raw-boards] (load-input filename)
        cards (bingo-cards (parse-boards raw-boards))]
    (play cards numbers)))

(defn part2 [filename])
