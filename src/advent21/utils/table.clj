(ns advent21.utils.table)

(defn table-of
  "Creates a table of the specified dimensions, filled with the given value v."
  [x y v]
  (vec (repeat y (vec (repeat x v)))))

(defn get-cell [t x y] (nth (nth t y) x))

(defn find-cell-fn
  "Returns the coordinates of the cells containing v, or an empty list if there are none."
  [t f]
  (for [row (map-indexed list t)
        cell (map-indexed list (second row))
        :when (f (second cell))]
    [(first cell) (first row)]))

(defn find-cell
  "Returns the coordinates of the cells containing v, or an empty list if there are none."
  [t v]
  (find-cell-fn t (partial = v)))

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

(defn adjacents [t [x y]]
  (let [y-size (count t)
        x-size (if (> y-size 0) (count (first t)) 0)]
    (->> (vector [(dec x) (dec y)]
                 [(dec x) y]
                 [(dec x) (inc y)]
                 [x (dec y)]
                 [x (inc y)]
                 [(inc x) (dec y)]
                 [(inc x) y]
                 [(inc x) (inc y)])
         (filter (fn [[x y]] (and (>= x 0) (>= y 0) (< x x-size) (< y y-size)))))))

(defn cell-count [t] (* (count t) (if (= (count t) 0) 0 (count (first t)))))
