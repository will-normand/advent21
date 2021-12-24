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
  (println "polymer is a" (type polymer) "that's" (count polymer) "long")
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

(def inc-with-nil (fn [x] (if (nil? x) 1 (inc x))))

#_(defn pairwise-step [pair rules steps]
  (loop [[a b] pair
         freqs {a 1, b 1}
         step 0]
    (if (= step steps)
      freqs
      (if-let [c (get rules [a b])]
        (merge-with +
                    (recur [a c] (update freqs c inc-with-nil) (inc step))
                    (recur [c b] (update freqs c inc-with-nil) (inc step)))
        freqs))))

;(defn pairwise-step0 [pair rules step steps freqs]
;  (let [[a b] pair]
;    (if (= step steps)
;      freqs
;      (if-let [c (get rules [a b])]
;        (merge-with +
;                    (pairwise-step0 [a c] rules (inc step) steps (update freqs c inc-with-nil))
;                    (pairwise-step0 [c b] rules (inc step) steps (update freqs c inc-with-nil)))
;        freqs))))

(defn pairwise-step [pair rules steps]
  (letfn [(pairwise-step0 [pair step freqs]
            (let [[a b] pair]
              (if (= step steps)
                (do (println "Reached max depth " freqs)
                  freqs)
                (if-let [c (get rules [a b])]
                  (merge-with +
                              (pairwise-step0 [a c] (inc step) (update freqs c inc-with-nil))
                              (pairwise-step0 [c b] (inc step) (update freqs c inc-with-nil)))
                  (do (println "no more subs for " a b freqs) freqs)))))]
    (pairwise-step0 pair 0 {})))

(defn most-least-common [polymer] (let [freq (frequencies polymer)
                                        sorted-freqs (sort-by val freq)]
                                    [(second (last sorted-freqs)) (second (first sorted-freqs))]))

(defn do-steps [polymer rules steps]
  (reduce (fn [polymer _] #_(time) (step polymer rules)) polymer (repeat steps 1)))

(defn calc-diff [filename steps]
  (let [input (parse-input filename)
        polymer (do-steps (:template input) (:rules input) steps)
        [most least] (most-least-common polymer)]
    (- most least)))

(defn part1 [filename] (calc-diff filename 10))

(defn part2 [filename] (calc-diff filename 40))
