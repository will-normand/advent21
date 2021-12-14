(ns advent21.day14
  (:require [clojure.string :as str]
            [advent21.utils.input :as i]))

(defn parse-input [filename]
  (let [rows (i/load-input filename)
        [template _ & rules-str] rows
        rules (map (fn [row] (let [[pair element] (str/split row #" -> ")]
                               [[(first pair) (second pair)] (first element)]))
                   rules-str)]
    {:template (seq template)
     :rules    (into {} rules)}))

(defn step [polymer rules]
  (loop [[a b & rest] polymer
         new-polymer []]
    (if (nil? b)
      (conj new-polymer a)
      (if-let [c (get rules [a b])]
        (recur (conj rest b) (conj new-polymer a c))
        (recur (conj rest b) (conj new-polymer a))))))

(defn most-least-common [polymer] (let [freq (frequencies polymer)
                                        sorted-freqs (sort-by val freq)]
                                    [(second (last sorted-freqs)) (second (first sorted-freqs))]))

(defn do-steps [polymer rules steps]
  (reduce (fn [polymer _] (time (step polymer rules))) polymer (repeat steps 1)))

(defn calc-diff [filename steps]
  (let [input (parse-input filename)
        polymer (do-steps (:template input) (:rules input) steps)
        [most least] (most-least-common polymer)]
    (- most least)))

(defn part1 [filename] (calc-diff filename 10))

(defn part2 [filename])
