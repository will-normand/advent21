(ns advent21.day14
  (:require [clojure.string :as str]
            [clojure.test :as t]
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
         new-polymer '()]
    (if (nil? b)
      (reverse (conj new-polymer a))
      (if-let [c (get rules [a b])]
        (recur
          (conj rest b)
          (conj new-polymer a c))
        (recur
          (conj rest b)
          (conj new-polymer a))))))

(defn pairwise-step
  {:test (fn [] (do (t/is (= {}
                             (pairwise-step ['x 'y] {} 10)))
                    (t/is (= {'z 1}
                             (pairwise-step ['x 'y] {['x 'y] 'z} 1)))
                    (t/is (= {'z 1}
                             (pairwise-step ['x 'y] {['x 'y] 'z} 10)))))}
  [pair rules steps]
  (let [pairwise-step0
        (memoize
          (fn [pairwise-step0 pair step]
            (let [[a b] pair]
              (if (= step steps)
                {}
                (if-let [c (get rules [a b])]
                  (merge-with +
                              {c 1}
                              (pairwise-step0 pairwise-step0 [a c] (inc step))
                              (pairwise-step0 pairwise-step0 [c b] (inc step)))
                  {})))))
        pairwise-step0 (partial pairwise-step0 pairwise-step0)]
    (pairwise-step0 pair 0)))

(defn pairwise-steps [polymer rules steps]
  (let [pairs (partition 2 1 polymer)
        polymer-freqs (frequencies polymer)]
    (->> (conj (map #(pairwise-step % rules steps) pairs) polymer-freqs)
         (reduce (partial merge-with +)))))

(defn most-least-common [freqs]
  (let [sorted-freqs (sort-by val freqs)]
    [(second (last sorted-freqs)) (second (first sorted-freqs))]))

(defn do-steps [polymer rules steps]
  (reduce (fn [polymer _] #_(time) (step polymer rules)) polymer (repeat steps 1)))

(defn calc-diff [filename steps]
  (let [input (parse-input filename)
        polymer (do-steps (:template input) (:rules input) steps)
        [most least] (most-least-common (frequencies polymer))]
    (- most least)))

(defn calc-diff-depth [filename steps]
  (let [input (parse-input filename)
        freqs (pairwise-steps (:template input) (:rules input) steps)
        [most least] (most-least-common freqs)]
    (- most least)))

(defn part1 [filename] (calc-diff filename 10))
(defn part1-depth [filename] (calc-diff-depth filename 10))

(defn part2 [filename] (calc-diff-depth filename 40))
