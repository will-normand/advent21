(ns advent21.utils.table)

(defn table-of
  "Creates a table of the specified dimensions, filled with the given value v."
  [x y v]
  (vec (repeat y (vec (repeat x v)))))

(defn get-cell [t x y] ((t y) x))

(defn find-cell
  "Returns the coordinates of the cells containing v, or an empty list if there are none."
  [t v]
  (for [row (map-indexed list t)
        cell (map-indexed list (second row))
        :when (= (second cell) v)]
    [(first cell) (first row)]))

(defn replace-cell
  "Returns a new table with the cell at the given coordinates replaced by v."
  [t x y v]
  (->>
    (assoc (t y) x v)
    (assoc t y)))

(defn transpose [t] (apply mapv vector t))

(defn complete-row?
  "Returns true if any row contains only v, otherwise nil."
  [t v]
  (some (fn [row] (every? #(= v %) row)) t))

(defn complete-column? [t v] (complete-row? (transpose t) v))
